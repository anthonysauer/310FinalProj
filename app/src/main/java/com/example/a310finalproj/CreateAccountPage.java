package com.example.a310finalproj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountPage extends AppCompatActivity {
    EditText emailField;
    EditText nameField;
    EditText passwordField;
    EditText confirmPasswordField;
    TextView error;

    FirebaseDatabase root;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set activity xml page
        setContentView(R.layout.activity_create_account);

        emailField = findViewById(R.id.createAccountEmail);
        nameField = findViewById(R.id.createAccountName);
        passwordField = findViewById(R.id.createAccountPassword);
        confirmPasswordField = findViewById(R.id.createAccountConfirmPassword);
        error = findViewById(R.id.createAccountError);
    }

    public void createAccount(View view) {
        String email = emailField.getText().toString();
        String name = nameField.getText().toString();
        String password = passwordField.getText().toString();
        String confirmPassword = confirmPasswordField.getText().toString();

        if (email.isEmpty()) {
            error.setText("Missing required field: email");
            return;
        }
        if (name.isEmpty()) {
            error.setText("Missing required field: name");
            return;
        }
        if (password.isEmpty()) {
            error.setText("Missing required field: password");
            return;
        }
        if (!password.equals(confirmPassword)) {
            error.setText("Your password and confirm password must match");
            return;
        }

        root = FirebaseDatabase.getInstance();
        reference = root.getReference("Users");

        DatabaseReference newUserRef = reference.push();
        newUserRef.setValue(new User(newUserRef.getKey(), email, name, password));

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
