package com.example.medicalapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UserDatabase.db";
    private static final int DATABASE_VERSION = 2;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Query-ul pentru crearea tabelului Users
        String createUserTableQuery = "CREATE TABLE IF NOT EXISTS Users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT," +
                "password TEXT)";

        // Executarea query-ului pentru crearea tabelului Users
        db.execSQL(createUserTableQuery);

        // Query-ul pentru crearea tabelului Reminders
        String createRemindersTableQuery = "CREATE TABLE IF NOT EXISTS Reminders (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "medicine_name TEXT," +
                "hour INTEGER," +
                "minute INTEGER)";

        // Executarea query-ului pentru crearea tabelului Reminders
        db.execSQL(createRemindersTableQuery);

        // Query-ul pentru crearea tabelului Reviews
        String createReviewsTableQuery = "CREATE TABLE IF NOT EXISTS Reviews (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER," +
                "rating INTEGER," +
                "comment TEXT)";

        // Executarea query-ului pentru crearea tabelului Reviews
        db.execSQL(createReviewsTableQuery);
    }

    // upgrade la app
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aici poți implementa logica pentru upgrade-ul schemei bazei de date, dacă este necesar
        db.execSQL("DROP TABLE IF EXISTS Users");
        db.execSQL("DROP TABLE IF EXISTS Reminders");
        db.execSQL("DROP TABLE IF EXISTS Reviews");
        onCreate(db);
    }

    public void addReminder(String medicineName, int hour, int minute) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("medicine_name", medicineName);
        values.put("hour", hour);
        values.put("minute", minute);


        db.insert("Reminders", null, values);
        db.close();
    }

    public void deleteReminder(String medicineName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Reminders", "medicine_name = ?", new String[]{medicineName});
        db.close();
    }



    public long addReview(int rating, String comment) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("rating", rating);
        values.put("comment", comment);
        long result = db.insert("Reviews", null, values);
        db.close();
        return result;
    }

    public Cursor getAllReviews() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                "rating",
                "comment"
        };
        return db.query("Reviews", projection, null, null, null, null, null);
    }







}
