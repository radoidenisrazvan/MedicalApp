package com.example.medicalapp;

import android.database.Cursor;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseManager {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public UserDatabaseManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }
    public long addUser(String username, String password) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        long result = db.insert("Users", null, values);
        db.close();
        return result;
    }

    public boolean authenticateUser(String username, String password) {
        db = dbHelper.getReadableDatabase();
        String[] columns = {"id"};
        String selection = "username=? AND password=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query("Users", columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }


}
