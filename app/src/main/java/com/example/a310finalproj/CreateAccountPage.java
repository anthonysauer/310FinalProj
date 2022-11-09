package com.example.a310finalproj;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CreateAccountPage extends AppCompatActivity {
    EditText emailField;
    EditText nameField;
    EditText passwordField;
    EditText confirmPasswordField;
    TextView error;

    Bitmap picture = null;

    FirebaseDatabase root;
    FirebaseStorage storage;

    public static final int GET_FROM_GALLERY = 1;

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
        storage = FirebaseStorage.getInstance();
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
        if (picture == null) {
            error.setText("Missing required field: picture");
            return;
        }

        DatabaseReference userRef = root.getReference("Users");
        StorageReference storageRef = storage.getReference();

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
                            User user = new User(newUserRef.getKey(), email, name, password);
                            newUserRef.setValue(user);

                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            picture.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                            if (picture.getAllocationByteCount() > (1024 * 1024)) {
                                error.setText("Picture too large: max size is 1 MB");
                                newUserRef.removeValue();
                                return;
                            }

                            byte[] data = baos.toByteArray();

                            storageRef.child("images/users/" + newUserRef.getKey()).putBytes(data)
                                    .addOnSuccessListener(taskSnapshot -> {
                                        Intent intent = new Intent(CreateAccountPage.this, MainActivity.class);
                                        intent.putExtra(Intent.EXTRA_USER, user);
                                        intent.putExtra("PICTURE", data);
                                        startActivity(intent);
                                    })
                                    .addOnFailureListener(e -> {
                                        error.setText("Failed to upload picture");
                                        newUserRef.removeValue();
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("firebase", "loadPost:onCancelled", error.toException());
                    }
                });
    }

    public void uploadPicture(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GET_FROM_GALLERY);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                picture = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
