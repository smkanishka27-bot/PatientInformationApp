package com.example.patientinformationapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayActivity extends AppCompatActivity {

    // ✅ Change these doctor contact details if needed
    private static final String DOCTOR_PHONE = "9876543210";
    private static final String DOCTOR_EMAIL = "doctor@example.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Patient Details - REGNO"); // ✅ Replace REGNO with your register number
        setContentView(R.layout.activity_display);

        TextView tvDetails = findViewById(R.id.tvDetails);
        Button btnCall = findViewById(R.id.btnCall);
        Button btnSms = findViewById(R.id.btnSms);
        Button btnEmail = findViewById(R.id.btnEmail);

        // Get values from intent
        Intent i = getIntent();
        String name = i.getStringExtra("name");
        String age = i.getStringExtra("age");
        String phone = i.getStringExtra("phone");
        String gender = i.getStringExtra("gender");
        String illness = i.getStringExtra("illness");
        String date = i.getStringExtra("date");

        String details =
                "Name: " + name + "\n" +
                        "Age: " + age + "\n" +
                        "Phone: " + phone + "\n" +
                        "Gender: " + gender + "\n" +
                        "Illness: " + illness + "\n" +
                        "Appointment Date: " + date + "\n\n" +
                        "Doctor Contact: " + DOCTOR_PHONE;

        tvDetails.setText(details);

        btnCall.setOnClickListener(v -> showConfirmDialog(
                "Call Doctor",
                "Do you want to call the doctor?",
                this::callDoctor
        ));

        btnSms.setOnClickListener(v -> showConfirmDialog(
                "Send SMS",
                "Do you want to send SMS to the doctor?",
                this::smsDoctor
        ));

        btnEmail.setOnClickListener(v -> emailDoctor());
    }

    private void showConfirmDialog(String title, String message, Runnable onYes) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes", (d, w) -> onYes.run())
                .setNegativeButton("No", (d, w) -> d.dismiss())
                .show();
    }

    private void callDoctor() {
        Intent dial = new Intent(Intent.ACTION_DIAL);
        dial.setData(Uri.parse("tel:" + DOCTOR_PHONE));
        startActivity(dial);
    }

    private void smsDoctor() {
        // ACTION_SENDTO with "smsto:" works without SMS permission (opens SMS app)
        Intent sms = new Intent(Intent.ACTION_SENDTO);
        sms.setData(Uri.parse("smsto:" + DOCTOR_PHONE));
        sms.putExtra("sms_body", "Hello Doctor, I need an appointment update.");
        startActivity(sms);
    }

    private void emailDoctor() {
        Intent email = new Intent(Intent.ACTION_SENDTO);
        email.setData(Uri.parse("mailto:" + DOCTOR_EMAIL));
        email.putExtra(Intent.EXTRA_SUBJECT, "Patient Appointment");
        email.putExtra(Intent.EXTRA_TEXT, "Hello Doctor,\nI would like to discuss my appointment.\nThank you.");
        startActivity(Intent.createChooser(email, "Choose Email App"));
    }
}