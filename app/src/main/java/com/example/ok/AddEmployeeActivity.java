package com.example.ok;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddEmployeeActivity extends AppCompatActivity {
    private EditText editTextName, editTextDepartment, editTextSalary;
    private Button buttonAddEmployee;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        editTextName = findViewById(R.id.editTextName);
        editTextDepartment = findViewById(R.id.editTextDepartment);
        editTextSalary = findViewById(R.id.editTextSalary);
        buttonAddEmployee = findViewById(R.id.buttonAddEmployee);

        dbManager = new DatabaseManager(this);
        dbManager.open();

        buttonAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String department = editTextDepartment.getText().toString();
                String salary = editTextSalary.getText().toString();

                if (name.isEmpty() || department.isEmpty() || salary.isEmpty()) {
                    Toast.makeText(AddEmployeeActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                dbManager.addEmployee(name, department, salary);
                Toast.makeText(AddEmployeeActivity.this, "Employee added successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.close();
    }
}
