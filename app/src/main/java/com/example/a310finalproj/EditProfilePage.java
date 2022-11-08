package com.example.a310finalproj;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

public class EditProfilePage extends AppCompatActivity {
    EditText emailField;
    EditText nameField;
    EditText oldPasswordField;
    EditText passwordField;
    EditText confirmPasswordField;
    ImageView selectedPicture;
    TextView error;

    Bitmap picture = null;

    User user;

    FirebaseDatabase root;
    FirebaseStorage storage;

    private static final int GET_FROM_GALLERY = 1;

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
        selectedPicture = findViewById(R.id.editSelectedPicture);
        error = findViewById(R.id.editError);

        Intent intent = getIntent();
        user = intent.getParcelableExtra(Intent.EXTRA_USER);
        byte[] byteArray = intent.getByteArrayExtra("PICTURE");
        picture = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        emailField.setText(user.getEmail());
        nameField.setText(user.getName());
        selectedPicture.setImageBitmap(picture);

        root = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
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

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        if (picture.getAllocationByteCount() > (1024 * 1024)) {
            error.setText("Picture too large: max size is 1 MB");
            return;
        }

        byte[] data = baos.toByteArray();

        root = FirebaseDatabase.getInstance();
        DatabaseReference userRef = root.getReference("Users");
        StorageReference storageRef = storage.getReference();

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

                                        storageRef.child("images/users/" + user.getId()).putBytes(data);
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
                selectedPicture.setImageBitmap(picture);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
