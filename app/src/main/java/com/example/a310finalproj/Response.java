package com.example.a310finalproj;

import java.util.Date;

public class Response {
    private int invitationId;
    private int userId;
    private String message;
    private Date timestamp;

    public Response(){
        invitationId=-1;
        userId=-1;
        message="unset";
        timestamp=null;
    }

    public Response(int _invitationId, int _userId, String _message, Date _timestamp){
        invitationId = _invitationId;
        userId = _userId;
        message = _message;
        timestamp = _timestamp;
    }

    public int getInvitationId() {
        return invitationId;
    }

    public int getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
