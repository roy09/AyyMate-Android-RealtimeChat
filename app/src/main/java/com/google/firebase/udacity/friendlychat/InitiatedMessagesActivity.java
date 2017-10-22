package com.google.firebase.udacity.friendlychat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class InitiatedMessagesActivity extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mInitiatedReference;

    ListView mListView;
    InitiatedMessageAdapter mInitiatedMessageAdapter;

    String uid;

    ArrayList<String> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiated_messages);

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mListView = (ListView) findViewById(R.id.listview);

        final List<InitiatedMessage> initiatedConversations = new ArrayList<InitiatedMessage>();
        mInitiatedMessageAdapter = new InitiatedMessageAdapter(this, R.layout.item_initiated_message, initiatedConversations);
        mListView.setAdapter(mInitiatedMessageAdapter);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            uid = user.getUid();
            mInitiatedReference = mFirebaseDatabase.getReference().child("Users").child(uid).child("chats");
            mInitiatedReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot message : dataSnapshot.getChildren()){
                        final String id = message.child("id").getValue().toString();
                        final String chat_id = message.child("key").getValue().toString();

                        DatabaseReference temp = mFirebaseDatabase.getReference().child("Users").child(id);
                        temp.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Users tempUser = dataSnapshot.getValue(Users.class);
                                mInitiatedMessageAdapter.add(new InitiatedMessage(chat_id, tempUser));
                                mInitiatedMessageAdapter.notifyDataSetChanged();
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

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    InitiatedMessage tempMessage = initiatedConversations.get(position);


                    Intent intent = new Intent(InitiatedMessagesActivity.this, PersonalChat.class);
                    intent.putExtra("newChat", "false");
                    intent.putExtra("chat_id", tempMessage.getChat_id());
                    startActivity(intent);
                }
            });


        } else {
            // No user is signed in
        }

//        if (initiatedConversations.size() == 0){
//            TextView mWarning = (TextView) findViewById(R.id.txtViewWarning);
//
//            mWarning.setText("Hey, you got no friends!");
//        }
    }

    public void ConvoDisplayFriendList(View v){
        Intent intent = new Intent(this, ConvoDisplayFriendList.class);
        startActivity(intent);
    }
}
