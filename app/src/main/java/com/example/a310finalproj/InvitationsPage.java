package com.example.a310finalproj;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvitationsPage extends AppCompatActivity {
    User user;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitations);
        Intent intent = getIntent();
        user = intent.getParcelableExtra(Intent.EXTRA_USER);



        context = this;


        if(user != null) {
            Log.d("Invs:", "good");
        }


        // read non-user invitations from database
        // add code when session implemented to filter out user-created invitations
        FirebaseDatabase root = FirebaseDatabase.getInstance();
        DatabaseReference invitationRef = root.getReference("Invitation");
        invitationRef
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //TODO-render Invitations into display
                        ArrayList<Invitation> invitations = new ArrayList<>();

                        // PULL ALL INVITATION INFO FROM DB


                        //user id
                        //inv id
                        //biography
                        // address
                        // rent double
                        // utilities double
                        // bedrooms
                        // beds
                        // bathrooms
                        // pets bool
                        // deadline Date

                        // ITERATE THROUGH ALL INVITATION ID's
                        for(DataSnapshot ID : snapshot.getChildren()){
                            boolean thisUser = false;
                            // ITERATE THROUGH DATA MEMBERS AND ADD TO NEW INVITATION
                            Invitation inv = new Invitation();
                            for(DataSnapshot dataMember : ID.getChildren()){

                                switch(dataMember.getKey()) {
                                    case "userId":
                                        String id = dataMember.getValue().toString();
                                        if(user.getId() == id){
                                            thisUser = true;
                                        }
                                        break;
                                    case "biography":
                                        inv.setBiography(dataMember.getValue().toString());
                                        break;
                                    case "address":
                                        inv.setAddress(dataMember.getValue().toString());
                                        break;
                                    case "rent":
                                        String tempRent = dataMember.getValue().toString();
                                        Double rent = Double.parseDouble(tempRent);
                                        inv.setRent(rent);
                                        break;
                                    case "utilities":
                                        String tempUtil = dataMember.getValue().toString();
                                        Double utilities = Double.parseDouble(tempUtil);
                                        inv.setUtilities(utilities);
                                        break;
                                    case "bedrooms":
                                        String tempBeds = dataMember.getValue().toString();
                                        int beds = Integer.parseInt(tempBeds);
                                        inv.setBeds(beds);
                                        break;
                                    case "bathrooms":
                                        String tempBath = dataMember.getValue().toString();
                                        int baths = Integer.parseInt(tempBath);
                                        inv.setBathrooms(baths);
                                        break;
                                    case "pets":
                                        String tempPets = dataMember.getValue().toString();
                                        Boolean pets = Boolean.valueOf(tempPets);
                                        inv.setPets(pets);
                                        break;
                                    case "deadline":
                                        String tempDate = dataMember.getValue().toString();
                                        //TODO: ADD DATE FUNCTIONALITY
                                        break;
                                }
                            }

                            //save invitation to list
                            if(!thisUser){
                                invitations.add(inv);
                            }

                        }
                        //render invitations
                        LinearLayout insertPoint = findViewById(R.id.invList);


                        for(Invitation i : invitations){
                            Button entry = new Button(context);
                            entry.setLayoutParams(new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT));
                            entry.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    viewInvitation(entry);
                                }
                            });

                            entry.setText("View invitation at: " + i.getAddress());
                            insertPoint.addView(entry);
                        }



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("firebase", "loadPost:onCancelled", error.toException());
                    }
                });
    }

    public void viewInvitation(View view){
        String address = ((Button)view).getText().toString();
        address = address.substring(20);
        Intent intent = new Intent(this, InvitationDetails.class);
        intent.putExtra(Intent.EXTRA_USER, user);
        intent.putExtra("invAddress", address);
        //pass string to next page
        startActivity(intent);


    }

    public void returnFromInvs(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Intent.EXTRA_USER, user);
        startActivity(intent);
    }
}
