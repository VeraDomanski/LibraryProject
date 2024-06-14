package com.example.ok;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "company.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Departments (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "budget INTEGER NOT NULL)");

        db.execSQL("CREATE TABLE Salary (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "employee_name TEXT NOT NULL, " +
                "amount INTEGER NOT NULL, " +
                "bonus INTEGER NOT NULL)");

        db.execSQL("CREATE TABLE Employee (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "department_id INTEGER NOT NULL, " +
                "salary_id INTEGER NOT NULL, " +
                "FOREIGN KEY(department_id) REFERENCES Departments(id), " +
                "FOREIGN KEY(salary_id) REFERENCES Salary(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Employee");
        db.execSQL("DROP TABLE IF EXISTS Departments");
        db.execSQL("DROP TABLE IF EXISTS Salary");
        onCreate(db);
    }
}
