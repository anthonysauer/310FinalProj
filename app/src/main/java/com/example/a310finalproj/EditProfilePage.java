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

public class EditProfilePage extends AppCompatActivity {
    EditText emailField;
    EditText nameField;
    EditText oldPasswordField;
    EditText passwordField;
    EditText confirmPasswordField;
    TextView error;

    User user;

    FirebaseDatabase root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set activity xml page
        setContentView(R.layout.activity_edit_profile);

        emailField = findViewById(R.id.editEmail);
        nameField = findViewById(R.id.editName);
        oldPasswordField = findViewById(R.id.oldPassword);
        passwordField = findViewById(R.id.editPassword);
        confirmPasswordField = findViewById(R.id.editConfirmPassword);
        error = findViewById(R.id.editError);

        Intent intent = getIntent();
        user = intent.getParcelableExtra(Intent.EXTRA_USER);

        emailField.setText(user.getEmail());
        nameField.setText(user.getName());

        root = FirebaseDatabase.getInstance();
    }

    public void editProfile(View view) {
        String email = emailField.getText().toString();
        String name = nameField.getText().toString();
        String oldPassword = oldPasswordField.getText().toString();
        String password = passwordField.getText().toString();
        String confirmPassword = confirmPasswordField.getText().toString();

        User newUser = new User(user.getId(), email, name, password);

        if (email.isEmpty()) {
            error.setText("Missing required field: email");
            return;
        }
        if (name.isEmpty()) {
            error.setText("Missing required field: name");
            return;
        }
        if (oldPassword.isEmpty()) {
            error.setText("Missing required field: old password");
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
        if (!user.getPassword().equals(oldPassword)) {
            error.setText("Old password is incorrect");
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
                        if (!email.equals(user.getEmail()) && snapshot.exists()) {
                            error.setText("Account with that email already exists");
                        }
                        else {
                            userRef.child(user.getId()).setValue(newUser)
                                    .addOnSuccessListener(unused -> {
                                        error.setText("Success! Profile updated");
                                        oldPasswordField.getText().clear();
                                        passwordField.getText().clear();
                                        confirmPasswordField.getText().clear();
                                        user = newUser;
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("firebase", "loadPost:onCancelled", error.toException());
                    }
                });
    }
}
