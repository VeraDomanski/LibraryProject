package com.example.ok;




import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private DatabaseManager dbManager;
    private TextView textView;
    private Button buttonOpenAddSalary, buttonOpenAddEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DatabaseManager(this);
        dbManager.open();

        textView = findViewById(R.id.textView);
        buttonOpenAddSalary = findViewById(R.id.buttonOpenAddSalary);
        buttonOpenAddEmployee = findViewById(R.id.buttonOpenAddEmployee);

        // Add sample data
        dbManager.addDepartment("First Department", 50000);
        dbManager.addSalary("Gery", 3000, 500);
        dbManager.addEmployee("John Doe", "First Department", "Gery");

        // Fetch and display data
        textView.setText(getEmployees());

        buttonOpenAddSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddSalaryActivity.class);
                startActivity(intent);
            }
        });

        buttonOpenAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEmployeeActivity.class);
                startActivity(intent);
            }
        });

        dbManager.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.open();
        textView.setText(getEmployees());
        dbManager.close();
    }

    private String getEmployees() {
        StringBuilder result = new StringBuilder();
        Cursor cursor = dbManager.getDatabase().query("Employee", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            int departmentId = cursor.getInt(cursor.getColumnIndexOrThrow("department_id"));
            int salaryId = cursor.getInt(cursor.getColumnIndexOrThrow("salary_id"));

            String departmentName = getDepartmentNameById(departmentId);
            int totalSalary = getSalaryTotalById(salaryId);

            result.append("Employee: ").append(name)
                    .append(", Department: ").append(departmentName)
                    .append(", Total Salary: ").append(totalSalary).append("\n");
        }
        cursor.close();
        return result.toString();
    }

    private String getDepartmentNameById(int id) {
        Cursor cursor = dbManager.getDatabase().query("Departments", new String[]{"name"}, "id=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            cursor.close();
            return name;
        }
        return null;
    }

    private int getSalaryTotalById(int id) {
        return dbManager.getSalaryTotalById(id);
    }
}
