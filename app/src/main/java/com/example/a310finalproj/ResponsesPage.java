package com.example.a310finalproj;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
    String tempUsrId;
    String tempInvId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        user = intent.getParcelableExtra(Intent.EXTRA_USER);

        // TODO - assign activity xml page
        // setContentView(R.layout.<XML FILE>);


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
        lookupIdList = new ArrayList<>();
        tempUsrId = "";
        tempInvId = "";
        FirebaseDatabase root = FirebaseDatabase.getInstance();
        DatabaseReference invRef = root.getReference("Invitation");
        invRef.orderByChild("userId").equalTo(user.getId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //iterate through invitations
                        for(DataSnapshot d : snapshot.getChildren()){
                            //iterate through invitation data members

                            for(DataSnapshot dataMem : d.getChildren()){
                                //look for user ID matching current session user
                                if(dataMem.getKey().toString() == "userId"){
                                    tempUsrId = dataMem.getValue().toString();
                                }
                                else if(dataMem.getKey().toString() == "invitationId") {
                                    tempInvId = dataMem.getValue().toString();
                                }
                            }
                            // if invitation was made by this user, add its id to lookup list
                            if(tempUsrId == user.getId()){
                                lookupIdList.add(tempInvId);
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        //if no responses for this user, display message
        if(lookupIdList.isEmpty()){
            //TODO: DISPLAY 'NO INVITATION' MESSAGE
        }

        for(String resId : lookupIdList) {

            DatabaseReference resRef = root.getReference("Response");
            resRef.orderByChild("responseId").equalTo(resId)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //TODO: FORM RESPONSE OBJECT AND RENDER ON PAGE
                            for(DataSnapshot res : snapshot.getChildren()){
                                for(DataSnapshot dataMem : res.getChildren()){
                                    switch(dataMem.getKey().toString()){
                                        case "userId":


                                    }
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w("firebase", "loadPost:onCancelled", error.toException());
                        }
                    });
            count++;
        }




    }
}
