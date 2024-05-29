package com.example.medicalapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import java.util.Calendar;

public class ReminderFragment extends Fragment {

    private TimePicker timePicker;
    private Button setReminderButton, viewRemindersButton;
    private EditText medicineNameEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminder, container, false);
        timePicker = view.findViewById(R.id.timePicker);
        setReminderButton = view.findViewById(R.id.setReminderButton);
        medicineNameEditText = view.findViewById(R.id.medicineNameEditText);
        viewRemindersButton = view.findViewById(R.id.viewRemindersButton);
        setReminderButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setReminder();
            }
        });
        viewRemindersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new ReminderHistoryFragment());
                fragmentTransaction.addToBackStack(null); // Adaugă fragmentul în stiva de back pentru a permite revenirea la ReminderFragment
                fragmentTransaction.commit();
            }
        });


        return view;
    }

    private void setReminder() {
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
        String medicineName = medicineNameEditText.getText().toString();

        DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());
        databaseHelper.addReminder(medicineName, hour, minute);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);


        AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(requireContext(), ReminderBroadcastReceiver.class);


        intent.putExtra("medicine_name", medicineName);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);


        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        Toast.makeText(requireContext(), "Reminder set for " + hour + ":" + minute + " for " + medicineName, Toast.LENGTH_SHORT).show();
    }

}
