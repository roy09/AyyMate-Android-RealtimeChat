package com.google.firebase.udacity.friendlychat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TempActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);


    }

    public void groupChat(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void personalChat(View v){
        Intent intent = new Intent(this, PersonalChat.class);
        startActivity(intent);
    }
}
