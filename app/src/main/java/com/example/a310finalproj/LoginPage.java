package com.example.a310finalproj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.test.espresso.idling.CountingIdlingResource;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class LoginPage extends AppCompatActivity {
    EditText emailField;
    EditText passwordField;
    TextView error;

    FirebaseDatabase root;
    FirebaseStorage storage;

    @Nullable
    public CountingIdlingResource idlingResource = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set activity xml page
        setContentView(R.layout.activity_login);

        emailField = findViewById(R.id.loginEmail);
        passwordField = findViewById(R.id.loginPassword);
        error = findViewById(R.id.loginError);

        root = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public void login(View view) {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        // Check if fields are empty
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

        if (idlingResource != null) {
            idlingResource.increment();
        }

        // Check if account with email and password exists
        userRef.orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // Check if email exists
                        if (!snapshot.exists()) {
                            error.setText("Incorrect email or password");
                            if (idlingResource != null && !idlingResource.isIdleNow()) {
                                idlingResource.decrement();
                            }
                        }
                        else {
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                User user = userSnapshot.getValue(User.class);

                                // If password does not match
                                if (!user.getPassword().equals(password)) {
                                    error.setText("Incorrect email or password");
                                    if (idlingResource != null && !idlingResource.isIdleNow()) {
                                        idlingResource.decrement();
                                    }
                                }
                                else {
                                    if (idlingResource != null && !idlingResource.isIdleNow()) {
                                        idlingResource.decrement();
                                    }

                                    StorageReference storageRef = storage.getReference("images/users/" + user.getId());
                                    storageRef.getBytes(1024 * 1024).addOnSuccessListener(bytes -> {
                                        Intent intent = new Intent(LoginPage.this, MainActivity.class);
                                        intent.putExtra(Intent.EXTRA_USER, user);
                                        intent.putExtra("PICTURE",bytes);
                                        startActivity(intent);
                                    }).addOnFailureListener(e -> error.setText("Failed to retrieve profile picture"));
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("firebase", "loadPost:onCancelled", error.toException());

                        if (idlingResource != null && !idlingResource.isIdleNow()) {
                            idlingResource.decrement();
                        }
                    }
                });
    }
}
