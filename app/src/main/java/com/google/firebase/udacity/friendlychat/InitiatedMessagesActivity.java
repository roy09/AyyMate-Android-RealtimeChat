package com.google.firebase.udacity.friendlychat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class InitiatedMessagesActivity extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mInitiatedReference;

    ListView mListView;
    InitiatedMessageAdapter mInitiatedMessageAdapter;

    String uid;

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
            mInitiatedReference = mFirebaseDatabase.getReference().child("personal").child(uid);


        } else {
            // No user is signed in
        }

        if (initiatedConversations.size() == 0){
            TextView mWarning = (TextView) findViewById(R.id.txtViewWarning);

            mWarning.setText("Hey, you got no friends!");
        }
    }
}
