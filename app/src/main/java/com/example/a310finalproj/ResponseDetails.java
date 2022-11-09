package com.example.a310finalproj;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResponseDetails extends AppCompatActivity {
    User user;
    Intent intent;
    String globalResId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_details);
        intent = getIntent();
        user = intent.getParcelableExtra(Intent.EXTRA_USER);
        final Context context = this;

        Bundle p = getIntent().getExtras();
        final String resId = p.getString("responseId");
        globalResId = p.getString("responseId");
        final String responderEmail = p.getString("responderEmail");

        final TextView userView = findViewById(R.id.resUser);
        final TextView messageView = findViewById(R.id.resMessage);

        userView.setText("Response from " + responderEmail);

        FirebaseDatabase root = FirebaseDatabase.getInstance();
        DatabaseReference resRef = root.getReference("Response");
        resRef.
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot response : snapshot.getChildren()){
                            //if matching response found, get+render message

                            if(response.getKey().toString().equals(resId)){
                                for(DataSnapshot val : response.getChildren()){
                                    if(val.getKey().toString().equals("message")){
                                        messageView.setText("Message: " + val.getValue().toString());
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

    public void rejectRes(View view){
        //update response rejected
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("Response").child(globalResId).child("status").setValue("rejected");

        Intent intent = new Intent(this, ManageInvitationsPage.class);
        intent.putExtra(Intent.EXTRA_USER, user);
        startActivity(intent);
    }

    public void acceptRes(View view){
        //update response accepted
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("Response").child(globalResId).child("status").setValue("accepted");

        Intent intent = new Intent(this, ManageInvitationsPage.class);
        intent.putExtra(Intent.EXTRA_USER, user);
        startActivity(intent);
    }

}
