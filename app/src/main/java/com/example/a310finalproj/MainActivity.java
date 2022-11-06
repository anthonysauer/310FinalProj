package com.example.a310finalproj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final String EXTRA_ID = "EXTRA_ID";
    private final String EXTRA_EMAIL = "EXTRA_EMAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView userId = findViewById(R.id.userId);
        TextView userEmail = findViewById(R.id.userEmail);

        Intent intent = getIntent();
        userId.setText(intent.getStringExtra(EXTRA_ID));
        userEmail.setText(intent.getStringExtra(EXTRA_EMAIL));
    }

    public void createAccount(View view) {
        Intent intent = new Intent(this, CreateAccountPage.class);
        startActivity(intent);
    }

    public void login(View view) {
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }
}