package com.google.firebase.udacity.friendlychat;

import android.content.Intent;
import android.support.constraint.Group;
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

public class GroupMemberList extends AppCompatActivity {

    ListView mListView;

    FirebaseDatabase mDatabase;
    DatabaseReference mUserGroupReference;
    DatabaseReference mGroupMemberReference;
    DatabaseReference mUserDatabaseReference;

    UserAdapter mUserAdapter;

    String userGroup;
    String userID;
    DataSnapshot tempUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_member_list);

        mListView = (ListView) findViewById(R.id.listview);

        final List<Users> usersList = new ArrayList<>();
        mUserAdapter = new UserAdapter(this, R.layout.item_user, usersList);
        mListView.setAdapter(mUserAdapter);


        mDatabase = FirebaseDatabase.getInstance();

        // getting user group


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
                                tempUser = temp;
                                Log.e("Now checking", (String) tempUser.getValue());
                                mUserDatabaseReference = mDatabase.getReference().child("Users");
                                mUserDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for(DataSnapshot user : dataSnapshot.getChildren()) {
                                            Users userObject = user.getValue(Users.class);
                                            Log.e("Checking against", userObject.getUid());
                                            if(userObject.getUid().equals(tempUser.getValue())){
                                                Log.e("OH", "WE BE HRE, BRO");
                                                usersList.add(userObject);
                                                mUserAdapter.notifyDataSetChanged();
                                            }

                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                            }

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

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Users user = usersList.get(position);

                Intent intent = new Intent(GroupMemberList.this, ProfileActivity.class);
                intent.putExtra("user_id", user.getUid());
                startActivity(intent);
            }
        });



    }
}
