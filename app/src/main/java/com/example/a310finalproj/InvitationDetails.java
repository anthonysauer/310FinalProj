package com.example.a310finalproj;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.Date;

//TODO: ADD DEADLINE FUNCTIONALITY

public class InvitationDetails extends AppCompatActivity {
    User user;
    Invitation inv;


    TextView addr;
    TextView bio;
    TextView rent;
    TextView utilities;
    TextView bedrooms;
    TextView beds;
    TextView bathrooms;
    TextView pets;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_details);

        Intent intent = getIntent();
        user = intent.getParcelableExtra(Intent.EXTRA_USER);
        //inv = intent.getParcelableExtra(Intent.EXTRA_INVITATION);



        //for testing
        Date d = new Date();
        inv = new Invitation("a","a","address","Hello please buy", 1.0, 2.0, 1, 2, 3, true, d);
        //
        addr = findViewById(R.id.invDetailsAddress);
        bio = findViewById(R.id.invDetailsBio);
        rent = findViewById(R.id.invDetailsRent);
        utilities = findViewById(R.id.invDetailsUtilities);
        bedrooms = findViewById(R.id.invDetailsBedrooms);
        beds = findViewById(R.id.invDetailsBeds);
        bathrooms = findViewById(R.id.invDetailsBathrooms);
        pets = findViewById(R.id.invDetailsPets);

        // fill text fields
        addr.setText("Address: " + inv.getAddress());
        bio.setText("Bio: " + inv.getBiography());
        rent.setText("Rent: " + String.valueOf(inv.getRent()));
        utilities.setText("Utilities: "+String.valueOf(inv.getUtilities()));
        bedrooms.setText("Bedrooms: " + String.valueOf(inv.getBedrooms()));
        beds.setText("Beds: " + String.valueOf(inv.getBeds()));
        bathrooms.setText("Bathrooms: " + String.valueOf(inv.getBathrooms()));
        if(inv.isPets()){
            pets.setText("Has pets");
        }
        else{
            pets.setText("No pets");
        }
    }

    public void acceptInv(View view){
        EditText msgField = findViewById(R.id.invAcceptMessage);
        String message = msgField.getText().toString();

        //enter new response into DB
        FirebaseDatabase root = FirebaseDatabase.getInstance();
        DatabaseReference resRef = root.getReference("Response");
        DatabaseReference newResRef = resRef.push();

        newResRef.setValue(new Response(inv.getInvitationId(), user.getId(), message, null));

        //return to invitations
        Intent intent = new Intent(InvitationDetails.this, InvitationsPage.class);
        startActivity(intent);

    }

    public void rejectInv(View view){
        //return to invitations
        Intent intent = new Intent(InvitationDetails.this, InvitationsPage.class);
        startActivity(intent);
    }
}
