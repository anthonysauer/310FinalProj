package com.example.a310finalproj;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context context = this;

        Intent intent = getIntent();
        user = intent.getParcelableExtra(Intent.EXTRA_USER);

        if (user != null) {
            //TextView userIdTextView = findViewById(R.id.userId);
            TextView userEmailTextView = findViewById(R.id.userEmail);

            //userIdTextView.setText(user.getId());
            userEmailTextView.setText("Logged in as " + user.getEmail());

            findViewById(R.id.loginButton).setVisibility(View.INVISIBLE);
            findViewById(R.id.createAccountButton).setVisibility(View.INVISIBLE);
            findViewById(R.id.editProfileButton).setVisibility(View.VISIBLE);
            findViewById(R.id.createInvitationButton).setVisibility(View.VISIBLE);
            findViewById(R.id.manageInvitationsButton).setVisibility(View.VISIBLE);
            findViewById(R.id.logoutButton).setVisibility(View.VISIBLE);

            // find any updated response statuses
            final LinearLayout insertPoint = findViewById(R.id.resStatusList);
            FirebaseDatabase root = FirebaseDatabase.getInstance();
            DatabaseReference resRef = root.getReference("Response");
            resRef.orderByChild("userId").equalTo(user.getId())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot res : snapshot.getChildren()){
                                String invId = "";
                                for(DataSnapshot findId : res.getChildren()){
                                    if(findId.getKey().toString().equals("invitationId")){
                                        invId = findId.getValue().toString();
                                    }
                                }
                                for(DataSnapshot inner: res.getChildren()){
                                    if(inner.getKey().toString().equals("status")){
                                        if(inner.getValue().toString().equals("accepted")){
                                            //get invitation and format message
                                            FirebaseDatabase root = FirebaseDatabase.getInstance();
                                            DatabaseReference invRef = root.getReference("Invitation");
                                            invRef.orderByChild("invitationId").equalTo(invId)
                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            //find address and display message
                                                            //find address and display message
                                                            String address = "";
                                                            for(DataSnapshot inv : snapshot.getChildren()){
                                                                for(DataSnapshot mem : inv.getChildren()){
                                                                    if(mem.getKey().toString().equals("address")){

                                                                        address = mem.getValue().toString();
                                                                        Button entry = new Button(context);
                                                                        entry.setLayoutParams(new LinearLayout.LayoutParams(
                                                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                                                LinearLayout.LayoutParams.MATCH_PARENT));
                                                                        entry.setOnClickListener(new View.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(View view) {
                                                                                view.setVisibility(View.INVISIBLE);
                                                                            }
                                                                        });
                                                                        entry.setText("Your response to the invitation at " + address + " has been accepted (click to dismiss)");
                                                                        insertPoint.addView(entry);
                                                                        break;
                                                                    }
                                                                }
                                                            }

                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });
                                        }
                                        else if(inner.getValue().toString().equals("rejected")){
                                            //get invitation and format message
                                            FirebaseDatabase root = FirebaseDatabase.getInstance();
                                            DatabaseReference invRef = root.getReference("Invitation");
                                            invRef.orderByChild("invitationId").equalTo(invId)
                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            //find address and display message
                                                            String address = "";
                                                            for(DataSnapshot inv : snapshot.getChildren()){
                                                                for(DataSnapshot mem : inv.getChildren()){
                                                                    if(mem.getKey().toString().equals("address")){

                                                                        address = mem.getValue().toString();
                                                                        Button entry = new Button(context);
                                                                        entry.setLayoutParams(new LinearLayout.LayoutParams(
                                                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                                                LinearLayout.LayoutParams.MATCH_PARENT));
                                                                        entry.setOnClickListener(new View.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(View view) {
                                                                                view.setVisibility(View.INVISIBLE);
                                                                            }
                                                                        });
                                                                        entry.setText("Your response to the invitation at " + address + " has been rejected (click to dismiss)");
                                                                        insertPoint.addView(entry);
                                                                        break;
                                                                    }
                                                                }
                                                            }


                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });
                                        }
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
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

    public void goToCreateInvitation(View view) {
        Intent intent = new Intent(this, CreateInvitationPage.class);
        intent.putExtra(Intent.EXTRA_USER, user);
        intent.putExtra("PICTURE", getIntent().getByteArrayExtra("PICTURE"));
        startActivity(intent);
    }

    public void goToManageInvitations(View view) {
        Intent intent = new Intent(this, ManageInvitationsPage.class);
        intent.putExtra(Intent.EXTRA_USER, user);
        intent.putExtra("PICTURE", getIntent().getByteArrayExtra("PICTURE"));
        startActivity(intent);
    }

    public void logout(View view) {
        user = null;

        TextView userEmailTextView = findViewById(R.id.userEmail);
        userEmailTextView.setText("");

        findViewById(R.id.loginButton).setVisibility(View.VISIBLE);
        findViewById(R.id.createAccountButton).setVisibility(View.VISIBLE);
        findViewById(R.id.editProfileButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.createInvitationButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.manageInvitationsButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.logoutButton).setVisibility(View.INVISIBLE);

        // remove any updated response statuses
        final LinearLayout resStatusList = findViewById(R.id.resStatusList);
        resStatusList.removeAllViews();
    }
}