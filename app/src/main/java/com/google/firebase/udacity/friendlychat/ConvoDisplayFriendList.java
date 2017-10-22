package com.google.firebase.udacity.friendlychat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    DatabaseReference mUserDatabaseReference;

    String uid;

    ArrayAdapter<String> adapter;

    ArrayList<String> users;

    UserAdapter mUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convo_display_friend_list);

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        ListView listView = (ListView) findViewById(R.id.listview);
        final List<Users> usersList = new ArrayList<>();
        mUserAdapter = new UserAdapter(this, R.layout.item_user, usersList);
        listView.setAdapter(mUserAdapter);

        users = new ArrayList<String>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            uid = user.getUid();
            Log.e("MY UID", uid);
            mFriendsReference = mFirebaseDatabase.getReference().child("Friends").child(uid);
            mFriendsReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot friendKey: dataSnapshot.getChildren()){
                        users.add((String) friendKey.getKey());
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



            mUserDatabaseReference = mFirebaseDatabase.getReference().child("Users");
            mUserDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot user : dataSnapshot.getChildren()) {
                        Users userObject = user.getValue(Users.class);
                        for(String s: users){
                            if(userObject.uid.equals(s)){
                                Log.e("FRIEND FOUND", "UKA");
                                mUserAdapter.add(userObject);
                                mUserAdapter.notifyDataSetChanged();
                            }
                        }

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } else {
            // No user is signed in
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
