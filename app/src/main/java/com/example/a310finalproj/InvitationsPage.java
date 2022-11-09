package com.example.a310finalproj;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Vector;

public class InvitationsPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    User user;
    Context context;

    String[] filters = { "none", "bathrooms", "bedrooms", "beds", "rent", "utilities", "distance" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitations);
        Intent intent = getIntent();
        user = intent.getParcelableExtra(Intent.EXTRA_USER);

        Spinner spinner = findViewById(R.id.filterSpinner);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filters);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        context = this;


        if(user != null) {
            Log.d("Invs:", "good");
        }
    }

    public void retrieveInvitations(String filter, String university) {
        // read non-user invitations from database
        // add code when session implemented to filter out user-created invitations
        FirebaseDatabase root = FirebaseDatabase.getInstance();
        DatabaseReference invitationRef = root.getReference("Invitation");

        Query query = invitationRef;
        if (!filter.equals("none")) {
            query = invitationRef.orderByChild(filter);
        }

        query.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                    case "invitationId":
                                        inv.setInvitationId(dataMember.getValue().toString());
                                        break;
                                    case "userId":
                                        String id = dataMember.getValue().toString();
                                        if(Objects.equals(user.getId(), id)){
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
                                    case "university":
                                        inv.setUniversity(dataMember.getValue().toString());
                                        break;
                                    case "distance":
                                        String tempDistance = dataMember.getValue().toString();
                                        Double distance = Double.parseDouble(tempDistance);
                                        inv.setDistance(distance);
                                        break;
                                }
                            }

                            //save invitation to list
                            if(!thisUser){
                                if (!filter.equals("distance") || university.equals(inv.getUniversity())) {
                                    invitations.add(inv);
                                }
                            }

                        }
                        //render invitations
                        LinearLayout insertPoint = findViewById(R.id.invList);

                        // Only display invitations that have not been accepted by the user
                        DatabaseReference responseRef = root.getReference("Response");

                        responseRef.orderByChild("userId").equalTo(user.getId())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        Vector<String> acceptedInvitations = new Vector<>();

                                        if (snapshot.exists()) {
                                            for (DataSnapshot responseSnapshot : snapshot.getChildren()) {
                                                Response response = responseSnapshot.getValue(Response.class);
                                                if (response != null) {
                                                    acceptedInvitations.add(response.getInvitationId());
                                                }
                                            }
                                        }

                                        for (Invitation i : invitations) {
                                            // Don't display invitation if user accepted
                                            //Log.d("IDuser: ", user.getId());
                                            //Log.d("IDinv: ", i.getUserId());
                                            String uid = user.getId();
                                            String invId = i.getUserId();
                                            if (!acceptedInvitations.contains(i.getInvitationId()) && (uid != invId)) {
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

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.w("firebase", "loadPost:onCancelled", error.toException());
                                    }
                                });
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
        intent.putExtra("PICTURE", getIntent().getByteArrayExtra("PICTURE"));
        intent.putExtra("invAddress", address);
        //pass string to next page
        startActivity(intent);


    }

    public void returnFromInvs(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Intent.EXTRA_USER, user);
        intent.putExtra("PICTURE", getIntent().getByteArrayExtra("PICTURE"));
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        if (!filters[pos].equals("distance")) {
            // Remove invitations from view
            LinearLayout insertPoint = findViewById(R.id.invList);
            insertPoint.removeAllViews();

            findViewById(R.id.filterUniversity).setVisibility(View.GONE);
            findViewById(R.id.filterDistanceButton).setVisibility(View.GONE);

            retrieveInvitations(filters[pos], "");
        }
        else {
            findViewById(R.id.filterUniversity).setVisibility(View.VISIBLE);
            findViewById(R.id.filterDistanceButton).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void filterDistance(View view) {
        EditText universityField = findViewById(R.id.filterUniversity);
        String university = universityField.getText().toString();

        if (university.isEmpty()) {
            return;
        }

        LinearLayout insertPoint = findViewById(R.id.invList);
        insertPoint.removeAllViews();

        retrieveInvitations("distance", university);
    }
}
