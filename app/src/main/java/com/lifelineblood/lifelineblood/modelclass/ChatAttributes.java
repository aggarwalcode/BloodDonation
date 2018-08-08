package com.lifelineblood.lifelineblood.modelclass;

public class ChatAttributes {

    String receiverId;
    String senderId;
    String message;
    int timestamp;

    public ChatAttributes(String receiverId, String senderId, String message, int timestamp) {

        this.receiverId = receiverId;
        this.senderId = senderId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getMessage() {
        return message;
    }

    public int getTimestamp() {
        return timestamp;
    }


}
