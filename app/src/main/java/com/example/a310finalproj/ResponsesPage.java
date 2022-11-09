package com.example.a310finalproj;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResponsesPage extends AppCompatActivity {
    User user;
    Intent intent;
    ArrayList<String> lookupIdList;
    ArrayList<Response> responses;

    //Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        user = intent.getParcelableExtra(Intent.EXTRA_USER);
        final Context context = this;

        setContentView(R.layout.activity_respones_page);


        //DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        //Map<String,Object> ex = new HashMap<>();

        //ex.put("/Invitation/test/id","none");
        //ex.put("/Invitation/test7/test","test");



        //db.updateChildren(ex);

        // read responses for this user from database
        // filter for responses with invitationId's invitation's userId matching this user ID
        // inv->userID matches this user

        // first get ID's of invitations from this user
        // get responses with invitation ID's matching these ID's
        final LinearLayout insertPoint = findViewById(R.id.resList);

        FirebaseDatabase root = FirebaseDatabase.getInstance();
        DatabaseReference invRef = root.getReference("Invitation");
        invRef.orderByChild("userId").equalTo(user.getId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //iterate through invitations
                        lookupIdList = new ArrayList<>();
                        for(DataSnapshot d : snapshot.getChildren()){

                                for(DataSnapshot dataMem: d.getChildren()){
                                    //iterate through invitation data members
                                    Log.d("test", dataMem.toString());

                                    //look for user ID matching current session user
                                    switch(dataMem.getKey().toString()){
                                        case "invitationId":
                                            final String tempInvId = dataMem.getValue().toString();
                                            lookupIdList.add(tempInvId);
                                            DatabaseReference resRef = root.getReference("Response");
                                            resRef.orderByChild("invitationId").equalTo(tempInvId)
                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            //TODO: FORM RESPONSE OBJECT AND RENDER ON PAGE
                                                            for(DataSnapshot res : snapshot.getChildren()){
                                                                final String resId = res.getKey().toString();
                                                                for(DataSnapshot dataMem : res.getChildren()){
                                                                    switch(dataMem.getKey().toString()){
                                                                        case "userId":
                                                                            //get user, render response
                                                                            String uid  = dataMem.getValue().toString();
                                                                            FirebaseDatabase root = FirebaseDatabase.getInstance();
                                                                            DatabaseReference userRef = root.getReference("Users");
                                                                            userRef.orderByChild("id").equalTo(uid)
                                                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                        @Override
                                                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                            for(DataSnapshot d : snapshot.getChildren()){
                                                                                                    for(DataSnapshot inner: d.getChildren()){
                                                                                                        switch(inner.getKey()){
                                                                                                            case "email":
                                                                                                                String email = inner.getValue().toString();
                                                                                                                // display "response from 'user'"
                                                                                                                // store resId in View
                                                                                                                ResponseView entry = new ResponseView(context);
                                                                                                                entry.setLayoutParams(new LinearLayout.LayoutParams(
                                                                                                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                                                                                                        LinearLayout.LayoutParams.MATCH_PARENT));
                                                                                                                entry.setResponseId(resId);
                                                                                                                entry.setEmail(email);
                                                                                                                entry.setOnClickListener(new View.OnClickListener() {
                                                                                                                    @Override
                                                                                                                    public void onClick(View view) {
                                                                                                                        viewResponse(entry);
                                                                                                                    }
                                                                                                                });
                                                                                                                entry.setText("View response from: " + email);
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

                                                                            break;
                                                                    }
                                                                }
                                                            }

                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {
                                                            Log.w("firebase", "loadPost:onCancelled", error.toException());
                                                        }
                                                    });
                                            break;
                                    }

                                }
                            }





                        //if no responses for this user, display message
                        if(lookupIdList.isEmpty()){
                            //TODO: DISPLAY 'NO INVITATION' MESSAGE
                            Log.d("Test", "empty");
                        }



                        for(final String resId : lookupIdList) {
                            Log.d("Test", "iterating");




                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void returnFromResponses(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Intent.EXTRA_USER, user);
        startActivity(intent);

    }

    public void viewResponse(View view){
        String resId = ((ResponseView)view).responseId;
        String email = ((ResponseView)view).email;
        Intent intent = new Intent(this, ResponseDetails.class);
        intent.putExtra(Intent.EXTRA_USER, user);
        intent.putExtra("responseId", resId);
        intent.putExtra("responderEmail", email);

        startActivity(intent);
    }
}