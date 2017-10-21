package com.google.firebase.udacity.friendlychat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RequestsActivity extends AppCompatActivity {

    //ListView mUsersListView;
    UserAdapter mUserAdapter;

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mUserDatabaseReference;

    private DatabaseReference mUsersDatabase;



    private DatabaseReference mFriendReqDatabase;
    private DatabaseReference mFriendDatabase;
    private DatabaseReference mNotificationDatabase;

    private DatabaseReference mRootRef;

    private FirebaseUser mCurrent_user;

    private String mCurrent_state;
    ListView mUsersListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        //final String user_id = getIntent().getStringExtra("user_id");
        mRootRef = FirebaseDatabase.getInstance().getReference();
        //mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
        mFriendReqDatabase = FirebaseDatabase.getInstance().getReference().child("Friend_req");
        mFriendDatabase = FirebaseDatabase.getInstance().getReference().child("Friends");
        mNotificationDatabase = FirebaseDatabase.getInstance().getReference().child("notifications");
        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();
        //mUserDatabaseReference = mFirebaseDatabase.getReference().child("Users");

        mUsersListView = (ListView) findViewById(R.id.listview);

       // final List<Users> usersList = new ArrayList<>();
        //mUserAdapter = new UserAdapter(this, R.layout.item_user, usersList);
        //mUsersListView.setAdapter(mUserAdapter);

        //mProfileSendReqBtn = (Button) findViewById(R.id.profile_send_req_btn);

        //-----------------------------------------------------------

       // DatabaseReference scoresRef = FirebaseDatabase.getInstance().getReference("notifications");
        mNotificationDatabase.child(mCurrent_user.getUid()).orderByValue().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                HashMap<?, ?> requestMap = (HashMap) dataSnapshot.getValue();
                Toast.makeText(RequestsActivity.this, "The " + dataSnapshot.getKey() + " score is " + requestMap.get("from"), Toast.LENGTH_LONG).show();

                Intent profileIntent = new Intent(RequestsActivity.this, ProfileActivity.class);
                profileIntent.putExtra("user_id", requestMap.get("from").toString());
                startActivity(profileIntent);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {



            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

            // ...
        });


       /* mNotificationDatabase.child(mCurrent_user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if(dataSnapshot.exists()) {

                    HashMap<?, ?> requestMap = (HashMap) dataSnapshot.getValue();
                    HashMap<String, String> r2=requestMap.get();
                    //for(DataSnapshot user : dataSnapshot.getChildren()) {
                    //requestMap = dataSnapshot.getValue(HashMap.class);
                    Iterator i = requestMap.entrySet().iterator();
                    //String newNotificationId = dataSnapshot.getChildren();

                    while(i.hasNext())
                    {
                        Map.Entry e = (Map.Entry) i.next();
                        String key = e.getKey();
                        String value = e.getValue();
                        System.out.println(key + " " + value);
                    }
                    Toast.makeText(RequestsActivity.this, "You have a friend request: " + r2.toString(), Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(RequestsActivity.this, "You have no friend requests " , Toast.LENGTH_LONG).show();

                }


                //}

               if (dataSnapshot.hasChild(user_id)) {

                    String req_type = dataSnapshot.child(user_id).child("request_type").getValue().toString();
                    String uid= dataSnapshot.child(user_id).getValue().toString();
                    if (req_type.equals("received")) {

                        mCurrent_state = "req_received";
                        // mProfileSendReqBtn.setText("Accept Friend Request");
                       // Toast.makeText(RequestsActivity.this, "You have a friend request: "+uid, Toast.LENGTH_LONG).show();


                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {



            }

        }); */
























        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUsersListView = (ListView) findViewById(R.id.listview);

       // final List<Users> usersList = new ArrayList<>();
       // mUserAdapter = new UserAdapter(this, R.layout.item_user, usersList);
        //mUsersListView.setAdapter(mUserAdapter);

       // mUserDatabaseReference = mFirebaseDatabase.getReference().child("Users");


       /* mUsersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Users user = usersList.get(position);
                Log.d("USR", user.getName());

                Intent intent = new Intent(ChatDeployActivity.this, UserGroupActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });*/


    }
}
