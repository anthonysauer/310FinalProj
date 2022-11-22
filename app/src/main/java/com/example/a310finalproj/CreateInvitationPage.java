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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateInvitationPage extends AppCompatActivity {
    EditText addressField;
    EditText bioField;
    EditText rentField;
    EditText utilitiesField;
    EditText bedroomsField;
    EditText bedsField;
    EditText bathroomsField;
    EditText deadlineField;
    EditText petsField;
    EditText universityField;
    EditText distanceField;
    TextView error;

    String addressStr;
    String bio;
    String rentStr;
    String utilitiesStr;
    String bedroomsStr;
    String bedsStr;
    String bathroomsStr;
    String deadlineStr;
    String petsStr;
    String universityStr;
    String distanceStr;
    Double rent;
    Double utilities;
    Double distance;
    int bedrooms;
    int beds;
    int bathrooms;
    int pets;
    boolean hasPets;
    Date deadline;
    User user;
    Intent intent;


    FirebaseDatabase root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_invitation);

        intent = getIntent();
        user = intent.getParcelableExtra(Intent.EXTRA_USER);

        addressField = findViewById(R.id.createInvAddress);
        bioField = findViewById(R.id.createInvBio);
        rentField = findViewById(R.id.createInvRent);
        utilitiesField = findViewById(R.id.createInvUtilities);
        bedroomsField = findViewById(R.id.createInvBedrooms);
        bedsField = findViewById(R.id.createInvBeds);
        bathroomsField = findViewById(R.id.createInvBathrooms);
        deadlineField = findViewById(R.id.createInvDeadline);
        petsField = findViewById(R.id.createInvPets);
        universityField = findViewById(R.id.createInvUniversity);
        distanceField = findViewById(R.id.createInvDistance);
        error = findViewById(R.id.createInvError);

        root = FirebaseDatabase.getInstance();


    }

    //TODO: PARSE/VERIFY ADDRESS
    public void createInv(View view){
        addressStr = addressField.getText().toString();
        bio = bioField.getText().toString();
        rentStr = rentField.getText().toString();
        utilitiesStr = utilitiesField.getText().toString();
        bedroomsStr = bedroomsField.getText().toString();
        bedsStr = bedsField.getText().toString();
        bathroomsStr = bathroomsField.getText().toString();
        deadlineStr = deadlineField.getText().toString();
        petsStr = petsField.getText().toString();
        universityStr = universityField.getText().toString();
        distanceStr = distanceField.getText().toString();
        rent = -1.0;
        utilities = -1.0;
        distance = -1.0;
        bedrooms = -1;
        beds = -1;
        bathrooms = -1;
        pets = -1;
        deadline = null;




        // verify full fields
        if(addressStr.isEmpty() || bio.isEmpty() || rentStr.isEmpty() || utilitiesStr.isEmpty()
                || bedroomsStr.isEmpty() || bedsStr.isEmpty() || bathroomsStr.isEmpty() || deadlineStr.isEmpty() || petsStr.isEmpty() || universityStr.isEmpty() || distanceStr.isEmpty()){
            error.setText("Missing required field(s) (All fields required)");
            return;
        }

        if(!fieldVerificationUtil.isValidAddress(addressStr)){
            error.setText("Invalid address format");
            return;
        }


        //parse/verify numerical entries
        Boolean invalid = false;
        if(!fieldVerificationUtil.isValidDoubleEntry(rentStr)){invalid = true;}
        if(!fieldVerificationUtil.isValidDoubleEntry(utilitiesStr)){invalid = true;}
        if(!fieldVerificationUtil.isValidDoubleEntry(distanceStr)){invalid = true;}
        if(!fieldVerificationUtil.isValidIntEntry(bedroomsStr)){invalid=true;}
        if(!fieldVerificationUtil.isValidIntEntry(bedsStr)){invalid=true;}
        if(!fieldVerificationUtil.isValidIntEntry(bathroomsStr)){invalid=true;}
        if(!fieldVerificationUtil.isValidIntEntry(petsStr)){invalid=true;}


        try{
            rent = Double.parseDouble(rentStr);
            utilities = Double.parseDouble(utilitiesStr);
            distance = Double.parseDouble(distanceStr);
            bedrooms = Integer.parseInt(bedroomsStr);
            beds = Integer.parseInt(bedsStr);
            bathrooms = Integer.parseInt(bathroomsStr);
            pets = Integer.parseInt(petsStr);

        }
        catch (Exception e){
            invalid = true;
        }


        if(invalid){
            error.setText("Incorrect numerical field, please try again.");
            return;
        }
        hasPets = false;
        if(pets>0){
            hasPets = true;
        }

        //parse deadline
        SimpleDateFormat formatter = new SimpleDateFormat("MM/DD/yyyy", Locale.ENGLISH);
        try{
            deadline = formatter.parse(deadlineStr);
        }
        catch(Exception e){
            error.setText("Invalid date format (MM/DD/YY)");
            return;
        }
        if(deadline == null){
            error.setText("Invalid date format (MM/DD/YY)");
            return;
        }
        if(!fieldVerificationUtil.isValidDeadline(deadlineStr)){
            error.setText("Invalid deadline (must be within one year from now)");
            return;
        }

        //verify non-duplicate entry
        root = FirebaseDatabase.getInstance();
        DatabaseReference invRef = root.getReference("Invitation");

        // Check if invitation with address exists
        // If it does not exist, create invitation
        invRef.orderByChild("address").equalTo(addressStr)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            error.setText("Invitation for this address already exists");
                        }
                        else {
                            DatabaseReference newInvRef = invRef.push();
                            newInvRef.setValue(
                                    new Invitation(newInvRef.getKey(), user.getId(), addressStr, bio, universityStr, rent, utilities, distance, bedrooms, beds, bathrooms, hasPets, deadline));


                            Intent intent = new Intent(CreateInvitationPage.this, MainActivity.class);
                            intent.putExtra(Intent.EXTRA_USER, user);
                            intent.putExtra("PICTURE", getIntent().getByteArrayExtra("PICTURE"));
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("firebase", "loadPost:onCancelled", error.toException());
                    }
                });



    }
}