package com.google.firebase.udacity.friendlychat;

/**
 * Created by Rifat on 23/10/2017.
 */
public class Groups {
    private String itemName;
    private String itemDescription;

    public Groups(String name, String description) {
        this.itemName = name;
        this.itemDescription = description;
    }

    public String getItemName() {
        return this.itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }
}