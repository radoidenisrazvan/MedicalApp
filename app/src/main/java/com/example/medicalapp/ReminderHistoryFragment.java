package com.example.medicalapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReminderHistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private ReminderAdapter reminderAdapter;
    private List<String> reminders;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminder_history, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        reminders = new ArrayList<>();
        loadRemindersFromDatabase();
        reminderAdapter = new ReminderAdapter(requireContext(), reminders);
        recyclerView.setAdapter(reminderAdapter);
        return view;
    }

    private void loadRemindersFromDatabase() {
        DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Reminders", null);

        if (cursor.moveToFirst()) {
            do {
                int hour = cursor.getInt(cursor.getColumnIndex("hour"));
                int minute = cursor.getInt(cursor.getColumnIndex("minute"));
                String medicineName = cursor.getString(cursor.getColumnIndex("medicine_name"));
                String reminder = "Time: " + hour + ":" + minute + ", Medicine: " + medicineName;
                reminders.add(reminder);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        if (reminders.isEmpty()) {
            Toast.makeText(requireContext(), "No reminders found", Toast.LENGTH_SHORT).show();
        }
    }
}
