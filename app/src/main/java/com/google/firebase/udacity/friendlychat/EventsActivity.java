package com.google.firebase.udacity.friendlychat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventsActivity extends AppCompatActivity {

    private ListView mEventsListView;
    private EventAdapter mEventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);


        mEventsListView = (ListView) findViewById(R.id.listview);

        final List<Event> eventsList = new ArrayList<>();
        mEventAdapter = new EventAdapter(this, R.layout.item_event, eventsList);
        mEventsListView.setAdapter(mEventAdapter);


        DatabaseReference mEventsReference = FirebaseDatabase.getInstance().getReference().child("Events");
        mEventsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot event: dataSnapshot.getChildren()){
                    Event tempEvent = event.getValue(Event.class);
                    mEventAdapter.add(tempEvent);
                    mEventAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mEventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event event = eventsList.get(position);
                Intent intent = new Intent(EventsActivity.this, SpecificEventActivity.class);
                intent.putExtra("event", event);
                startActivity(intent);
            }
        });
    }
}
