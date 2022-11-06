package com.example.a310finalproj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateAccountPage extends AppCompatActivity {
    EditText emailField;
    EditText nameField;
    EditText passwordField;
    EditText confirmPasswordField;
    TextView error;

    FirebaseDatabase root;

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

        root = FirebaseDatabase.getInstance();
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
        DatabaseReference userRef = root.getReference("Users");

        // Check if account with email exists
        // If it does not exist, create the account
        userRef.orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            error.setText("Account with that email already exists");
                        }
                        else {
                            DatabaseReference newUserRef = userRef.push();
                            newUserRef.setValue(new User(newUserRef.getKey(), email, name, password));

                            Intent intent = new Intent(CreateAccountPage.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("firebase", "loadPost:onCancelled", error.toException());
                    }
                });
    }
}
