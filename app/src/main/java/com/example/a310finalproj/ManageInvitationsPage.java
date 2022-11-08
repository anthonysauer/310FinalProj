package com.example.a310finalproj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ManageInvitationsPage extends AppCompatActivity {
    User user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_invitations);

        Intent intent = getIntent();
        user = intent.getParcelableExtra(Intent.EXTRA_USER);

    }


    public void viewInvs(View view){
        Intent intent = new Intent(this, InvitationsPage.class);
        intent.putExtra(Intent.EXTRA_USER, user);
        startActivity(intent);
    }

    public void viewResponses(View view){
        Intent intent = new Intent(this, ResponsesPage.class);
        intent.putExtra(Intent.EXTRA_USER, user);
        startActivity(intent);
    }

}
