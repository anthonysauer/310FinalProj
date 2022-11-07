package com.example.a310finalproj;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DbAccess {
    public DatabaseReference db;

    //initialize connection to database by getting reference
    public DbAccess(){
        db  = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getDbReference(){
        return db;
    }

    public void enterUserInfo(User usr){
        Map<String,Object> userData = new HashMap<>();
        String path = "/Users/" + usr.getId();
        userData.put((path+"/email"),usr.getEmail());
        userData.put((path+"/name"),usr.getName());
        userData.put((path+"/password"),usr.getPassword());
        userData.put((path+"/name"), usr.getName());
        db.updateChildren(userData);
    }


}
