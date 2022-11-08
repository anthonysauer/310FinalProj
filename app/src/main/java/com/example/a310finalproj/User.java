package com.example.a310finalproj;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

//NOTES:
// createInvitation and createResponse dependent on respective
// class constructors (changes to either must be considered carefully)

public class User implements Parcelable {
    private String id;
    private String email;
    private String name;
    private String password;
    private Bitmap picture;

    public User() {
        picture = null;
    }

    // UNFINISHED --> profile picture not yet implemented
    public User(String userId, String userEmail, String userName, String userPassword) {
        id = userId;
        email = userEmail;
        name = userName;
        password = userPassword;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    //incomplete
    public Invitation createInvitation(){
        Invitation inv = new Invitation();
        return inv;
    };

    public Response createResponse(String invitationId, String userId, String message, Date timestamp){
        Response res = new Response(invitationId, userId, message, timestamp);
        return res;
    }

    // Code to implement Parcelable (allow User to be passed through Intents)
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(email);
        parcel.writeString(name);
        parcel.writeString(password);
        parcel.writeValue(picture);
    }
    public static final Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private User(Parcel in){
        // the order needs to be the same as in writeToParcel() method
        this.id = in.readString();
        this.email = in.readString();
        this.name = in.readString();
        this.password = in.readString();
        this.picture = in.readParcelable(Bitmap.class.getClassLoader());
    }
}
