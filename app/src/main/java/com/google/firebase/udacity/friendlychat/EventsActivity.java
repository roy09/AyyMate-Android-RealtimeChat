package com.google.firebase.udacity.friendlychat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

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

        List<Event> eventsList = new ArrayList<>();
        eventsList.add(new Event("Party at Manning", "Manning Bar", "Drinks on house", "Party"));
        eventsList.add(new Event("Trivia Night", "Manning Bar", "Drinks on house", "Party"));

        mEventAdapter = new EventAdapter(this, R.layout.item_event, eventsList);
        mEventsListView.setAdapter(mEventAdapter);
        
    }
}
