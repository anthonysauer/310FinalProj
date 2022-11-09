package com.example.a310finalproj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        user = intent.getParcelableExtra(Intent.EXTRA_USER);

        if (user != null) {
            TextView userIdTextView = findViewById(R.id.userId);
            TextView userEmailTextView = findViewById(R.id.userEmail);

            userIdTextView.setText(user.getId());
            userEmailTextView.setText(user.getEmail());

            findViewById(R.id.loginButton).setVisibility(View.INVISIBLE);
            findViewById(R.id.createAccountButton).setVisibility(View.INVISIBLE);
            findViewById(R.id.editProfileButton).setVisibility(View.VISIBLE);
            findViewById(R.id.createInvitationButton).setVisibility(View.VISIBLE);
            findViewById(R.id.manageInvitationsButton).setVisibility(View.VISIBLE);
        }
    }

    public void createAccount(View view) {
        Intent intent = new Intent(this, CreateAccountPage.class);
        startActivity(intent);
    }

    public void login(View view) {
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }

    public void editProfile(View view) {
        Intent intent = new Intent(this, EditProfilePage.class);
        intent.putExtra(Intent.EXTRA_USER, user);
        intent.putExtra("PICTURE", getIntent().getByteArrayExtra("PICTURE"));
        startActivity(intent);
    }

    public void goToCreateInvitation(View view){
        Intent intent = new Intent(this, CreateInvitationPage.class);
        intent.putExtra(Intent.EXTRA_USER, user);
        intent.putExtra("PICTURE", getIntent().getByteArrayExtra("PICTURE"));
        startActivity(intent);
    }

    public void goToManageInvitations(View view){
        Intent intent = new Intent(this, ManageInvitationsPage.class);
        intent.putExtra(Intent.EXTRA_USER, user);
        intent.putExtra("PICTURE", getIntent().getByteArrayExtra("PICTURE"));
        startActivity(intent);
    }
}