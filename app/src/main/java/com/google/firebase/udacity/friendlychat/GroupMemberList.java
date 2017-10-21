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

public class GroupMemberList extends AppCompatActivity {

    ListView mListView;

    FirebaseDatabase mDatabase;
    DatabaseReference mUserGroupReference;
    DatabaseReference mGroupMemberReference;
    ArrayList<String> items;

    String userGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_member_list);

        mListView = (ListView) findViewById(R.id.listview);
        items = new ArrayList<String>();
        final ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        mListView.setAdapter(itemsAdapter);


        mDatabase = FirebaseDatabase.getInstance();

        // getting user group
        String userID;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            userID = user.getUid();

            mUserGroupReference = mDatabase.getReference().child("Users").child(userID).child("group");
            mUserGroupReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    userGroup = (String) dataSnapshot.getValue();
                    Log.e("USER GROUP", userGroup);

                    Log.e("FAIL ALERT", "ABOUT TO FAIL");
                    Log.e("FAIL ALERT", userGroup);
                    mGroupMemberReference = mDatabase.getReference().child("groups").child(userGroup).child("members");
                    mGroupMemberReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot temp: dataSnapshot.getChildren()){
                                items.add((String) temp.getValue());
                            }
                            itemsAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


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
