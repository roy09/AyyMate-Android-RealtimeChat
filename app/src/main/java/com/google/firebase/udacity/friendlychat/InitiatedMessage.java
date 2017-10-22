package com.google.firebase.udacity.friendlychat;

/**
 * Created by Administrator on 22/10/2017.
 */

public class InitiatedMessage {
    String senderName;
    String lastMessage;

    public InitiatedMessage(){

    }

    public InitiatedMessage(String senderName, String lastMessage){
        this.senderName = senderName;
        this.lastMessage = lastMessage;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
