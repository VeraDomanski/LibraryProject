package com.example.ok;



import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddSalaryActivity extends AppCompatActivity {
    private EditText editTextEmployeeName, editTextSalaryAmount, editTextBonus;
    private Button buttonAddSalary;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_salary);

        editTextEmployeeName = findViewById(R.id.editTextEmployeeName);
        editTextSalaryAmount = findViewById(R.id.editTextSalaryAmount);
        editTextBonus = findViewById(R.id.editTextBonus);
        buttonAddSalary = findViewById(R.id.buttonAddSalary);

        dbManager = new DatabaseManager(this);
        dbManager.open();

        buttonAddSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String employeeName = editTextEmployeeName.getText().toString();
                String salaryAmountStr = editTextSalaryAmount.getText().toString();
                String bonusStr = editTextBonus.getText().toString();

                if (employeeName.isEmpty() || salaryAmountStr.isEmpty() || bonusStr.isEmpty()) {
                    Toast.makeText(AddSalaryActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                int salaryAmount = Integer.parseInt(salaryAmountStr);
                int bonus = Integer.parseInt(bonusStr);

                dbManager.addSalary(employeeName, salaryAmount, bonus);
                Toast.makeText(AddSalaryActivity.this, "Salary added successfully", Toast.LENGTH_SHORT).show();
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
