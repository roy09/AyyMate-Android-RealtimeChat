package com.google.firebase.udacity.friendlychat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ConvoDisplayFriendList extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mFriendsReference;

    String uid;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convo_display_friend_list);

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        ListView listView = (ListView) findViewById(R.id.listview);
        final List<String> friendList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, friendList);
        listView.setAdapter(adapter);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            uid = user.getUid();
            Log.e("MY UID", uid);
            mFriendsReference = mFirebaseDatabase.getReference().child("Friends").child(uid);
            mFriendsReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//                    for(dataSnapshot )
                    Log.e("MY FRIEND", (String) dataSnapshot.getValue());
                    adapter.add((String) dataSnapshot.getValue());
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            // No user is signed in
        }
    }
}
