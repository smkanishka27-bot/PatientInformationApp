package com.example.patientinformationapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText etName, etAge, etPhone;
    private RadioGroup rgGender;
    private Spinner spIllness;
    private TextView tvDate;
    private Button btnPickDate, btnSubmit;

    private String selectedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Patient App - REGNO"); // ✅ Replace REGNO with your register number
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etPhone = findViewById(R.id.etPhone);
        rgGender = findViewById(R.id.rgGender);
        spIllness = findViewById(R.id.spIllness);
        tvDate = findViewById(R.id.tvDate);
        btnPickDate = findViewById(R.id.btnPickDate);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Spinner setup from arrays.xml
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.illness_types,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spIllness.setAdapter(adapter);

        btnPickDate.setOnClickListener(v -> openDatePicker());

        btnSubmit.setOnClickListener(v -> submitDetails());
    }

    private void openDatePicker() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (DatePicker view, int y, int m, int d) -> {
                    // Month is 0-based in Calendar
                    selectedDate = d + "/" + (m + 1) + "/" + y;
                    tvDate.setText("Appointment Date: " + selectedDate);
                },
                year, month, day
        );
        dialog.show();
    }

    private void submitDetails() {
        String name = etName.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        int checkedId = rgGender.getCheckedRadioButtonId();
        String gender = "";
        if (checkedId != -1) {
            RadioButton rb = findViewById(checkedId);
            gender = rb.getText().toString();
        }

        String illness = spIllness.getSelectedItem().toString();

        // Basic validation
        if (name.isEmpty()) {
            etName.setError("Enter name");
            etName.requestFocus();
            return;
        }
        if (age.isEmpty()) {
            etAge.setError("Enter age");
            etAge.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            etPhone.setError("Enter phone number");
            etPhone.requestFocus();
            return;
        }
        if (checkedId == -1) {
            Toast.makeText(this, "Select gender", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Select appointment date", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("age", age);
        intent.putExtra("phone", phone);
        intent.putExtra("gender", gender);
        intent.putExtra("illness", illness);
        intent.putExtra("date", selectedDate);
        startActivity(intent);
    }
}