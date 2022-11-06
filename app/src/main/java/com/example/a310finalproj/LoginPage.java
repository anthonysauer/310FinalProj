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

public class LoginPage extends AppCompatActivity {
    EditText emailField;
    EditText passwordField;
    TextView error;

    FirebaseDatabase root;

    private final String EXTRA_ID = "EXTRA_ID";
    private final String EXTRA_EMAIL = "EXTRA_EMAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set activity xml page
        setContentView(R.layout.activity_login);

        emailField = findViewById(R.id.loginEmail);
        passwordField = findViewById(R.id.loginPassword);
        error = findViewById(R.id.loginError);

        root = FirebaseDatabase.getInstance();
    }

    public void login(View view) {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        if (email.isEmpty()) {
            error.setText("Missing required field: email");
            return;
        }
        if (password.isEmpty()) {
            error.setText("Missing required field: password");
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
                        if (!snapshot.exists()) {
                            error.setText("Incorrect email or password");
                        }
                        else {
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                User user = userSnapshot.getValue(User.class);

                                // If password does not match
                                if (!user.getPassword().equals(password)) {
                                    error.setText("Incorrect email or password");
                                }
                                else {
                                    Intent intent = new Intent(LoginPage.this, MainActivity.class);
                                    intent.putExtra(EXTRA_ID, user.getId());
                                    intent.putExtra(EXTRA_EMAIL, user.getEmail());
                                    startActivity(intent);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("firebase", "loadPost:onCancelled", error.toException());
                    }
                });
    }
}
