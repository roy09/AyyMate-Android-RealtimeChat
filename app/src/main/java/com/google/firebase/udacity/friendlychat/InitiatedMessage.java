package com.google.firebase.udacity.friendlychat;

/**
 * Created by Administrator on 22/10/2017.
 */

public class InitiatedMessage {
    String chat_id;
    Users user;

    public InitiatedMessage(){

    }

    public InitiatedMessage(String chat_id, Users user) {
        this.chat_id = chat_id;
        this.user = user;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
