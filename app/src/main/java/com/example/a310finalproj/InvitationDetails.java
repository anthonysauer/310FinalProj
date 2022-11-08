package com.example.a310finalproj;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
        Date d = new Date();

        //for testing
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
}
