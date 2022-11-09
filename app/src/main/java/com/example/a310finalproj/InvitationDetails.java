package com.example.a310finalproj;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

//TODO: ADD DEADLINE FUNCTIONALITY

public class InvitationDetails extends AppCompatActivity {
    User user;
    String invAdd;
    Context context;
    Invitation inv;

    /*
    TextView addr;
    TextView bio;
    TextView rent;
    TextView utilities;
    TextView bedrooms;
    TextView beds;
    TextView bathrooms;
    TextView pets;*/

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_details);

        Intent intent = getIntent();
        user = intent.getParcelableExtra(Intent.EXTRA_USER);
        //inv = intent.getParcelableExtra(Intent.EXTRA_INVITATION);
        Bundle p = getIntent().getExtras();
        invAdd =p.getString("invAddress");
        context = this;
        inv = new Invitation();

        final TextView addr = findViewById(R.id.invDetailsAddress);
        final TextView bio = findViewById(R.id.invDetailsBio);
        final TextView rent = findViewById(R.id.invDetailsRent);
        final TextView utilities = findViewById(R.id.invDetailsUtilities);
        final TextView bedrooms = findViewById(R.id.invDetailsBedrooms);
        final TextView beds = findViewById(R.id.invDetailsBeds);
        final TextView bathrooms = findViewById(R.id.invDetailsBathrooms);
        final TextView pets = findViewById(R.id.invDetailsPets);
        final TextView university = findViewById(R.id.invDetailsUniversity);
        final TextView distance = findViewById(R.id.invDetailsDistance);

        FirebaseDatabase root = FirebaseDatabase.getInstance();
        DatabaseReference invitationRef = root.getReference("Invitation");
        invitationRef.orderByChild("address").equalTo(invAdd)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot invSnapShot : snapshot.getChildren()){
                        for(DataSnapshot dataMember : invSnapShot.getChildren()){
                            switch(dataMember.getKey()) {
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

                                    break;
                                case "university":
                                    inv.setUniversity(dataMember.getValue().toString());
                                    break;
                                case "distance":
                                    String tempDistance = dataMember.getValue().toString();
                                    Double distance = Double.parseDouble(tempDistance);
                                    inv.setDistance(distance);
                                    break;
                            }
                        }}


                        // fill text fields
                        addr.setText("Address: " + inv.getAddress());
                        bio.setText("Bio: " + inv.getBiography());
                        rent.setText("Rent: " + String.valueOf(inv.getRent()));
                        utilities.setText("Utilities: "+String.valueOf(inv.getUtilities()));
                        bedrooms.setText("Bedrooms: " + String.valueOf(inv.getBedrooms()));
                        beds.setText("Beds: " + String.valueOf(inv.getBeds()));
                        bathrooms.setText("Bathrooms: " + String.valueOf(inv.getBathrooms()));
                        university.setText("University: " + inv.getUniversity());
                        distance.setText("Distance to university: " + String.valueOf(inv.getDistance()));
                        if(inv.isPets()){
                            pets.setText("Has pets");
                        }
                        else{
                            pets.setText("No pets");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    public void acceptInv(View view){
        EditText msgField = findViewById(R.id.invAcceptMessage);
        String message = msgField.getText().toString();

        //get invitation info from page
        FirebaseDatabase root = FirebaseDatabase.getInstance();
        DatabaseReference invitationRef = root.getReference("Invitation");
        final TextView addr = findViewById(R.id.invDetailsAddress);
        invitationRef.orderByChild("address").equalTo(invAdd)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String address = addr.getText().toString();
                        for(DataSnapshot outer : snapshot.getChildren()){
                            for(DataSnapshot inner: outer.getChildren()){
                                switch(inner.getKey().toString()){
                                    case "invitationId":
                                        String refId = null;
                                        refId = inner.getValue().toString();
                                        //enter new response into DB
                                        FirebaseDatabase root = FirebaseDatabase.getInstance();
                                        DatabaseReference resRef = root.getReference("Response");
                                        DatabaseReference newResRef = resRef.push();

                                        newResRef.setValue(new Response(refId, user.getId(), message, null));

                                        //return to invitations
                                        Intent intent = new Intent(InvitationDetails.this, ManageInvitationsPage.class);
                                        intent.putExtra(Intent.EXTRA_USER, user);
                                        intent.putExtra("PICTURE", getIntent().getByteArrayExtra("PICTURE"));
                                        startActivity(intent);
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

    public void rejectInv(View view){
        //return to invitations
        Intent intent = new Intent(InvitationDetails.this, InvitationsPage.class);
        intent.putExtra(Intent.EXTRA_USER, user);
        intent.putExtra("PICTURE", getIntent().getByteArrayExtra("PICTURE"));
        startActivity(intent);
    }
}
