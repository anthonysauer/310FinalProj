package com.example.a310finalproj;

import java.util.Date;

public class Response {
    private String invitationId;
    private String userId;
    private String message;
    private Date timestamp;

    public Response(){
        invitationId="unset";
        userId="unset";
        message="unset";
        timestamp=null;
    }

    public Response(String _invitationId, String _userId, String _message, Date _timestamp){
        invitationId = _invitationId;
        userId = _userId;
        message = _message;
        timestamp = _timestamp;
    }

    public String getInvitationId() {
        return invitationId;
    }

    public String getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
