package com.example.ok;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public void addDepartment(String name, int budget) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("budget", budget);
        database.insert("Departments", null, values);
    }

    public void addSalary(String employeeName, int amount, int bonus) {
        ContentValues values = new ContentValues();
        values.put("employee_name", employeeName);
        values.put("amount", amount);
        values.put("bonus", bonus);
        database.insert("Salary", null, values);
    }

    public void addEmployee(String name, String departmentName, String salaryName) {
        int departmentId = getDepartmentIdByName(departmentName);
        int salaryId = getSalaryIdByName(salaryName);

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("department_id", departmentId);
        values.put("salary_id", salaryId);
        database.insert("Employee", null, values);
    }

    private int getDepartmentIdByName(String name) {
        Cursor cursor = database.query("Departments", new String[]{"id"},
                "name=?", new String[]{name}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            cursor.close();
            return id;
        }
        return -1;
    }

    private int getSalaryIdByName(String employeeName) {
        Cursor cursor = database.query("Salary", new String[]{"id"},
                "employee_name=?", new String[]{employeeName}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            cursor.close();
            return id;
        }
        return -1;
    }
    public int getSalaryTotalById(int id) {
        Cursor cursor = database.query("Salary", new String[]{"amount", "bonus"}, "id=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int amount = cursor.getInt(cursor.getColumnIndexOrThrow("amount"));
            int bonus = cursor.getInt(cursor.getColumnIndexOrThrow("bonus"));
            cursor.close();
            return amount + bonus;
        }
        return -1;
    }
}
