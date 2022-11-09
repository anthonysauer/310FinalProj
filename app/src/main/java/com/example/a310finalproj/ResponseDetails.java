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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_details);
        intent = getIntent();
        user = intent.getParcelableExtra(Intent.EXTRA_USER);
        final Context context = this;

        Bundle p = getIntent().getExtras();
        final String resId = p.getString("responseId");
        final String responderEmail = p.getString("responderEmail");

        final TextView userView = findViewById(R.id.resUser);
        final TextView messageView = findViewById(R.id.resMessage);

        userView.setText("Response from " + responderEmail);

        FirebaseDatabase root = FirebaseDatabase.getInstance();
        DatabaseReference invRef = root.getReference("Response");
        invRef.
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot response : snapshot.getChildren()){
                            Log.d("resDet", response.getKey().toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }

    public void rejectRes(View view){

    }

    public void acceptRes(View view){

    }

}
