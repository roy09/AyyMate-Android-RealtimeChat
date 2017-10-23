package com.google.firebase.udacity.friendlychat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SpecificEventActivity extends AppCompatActivity {

    TextView mEventName;
    TextView mEventLocation;
    TextView mEventType;
    TextView mEventDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_event);

        Event event = (Event) getIntent().getSerializableExtra("event");

        mEventName = (TextView) findViewById(R.id.editTextEventName);
        mEventLocation = (TextView) findViewById(R.id.editTextEventLocation);
        mEventType = (TextView) findViewById(R.id.editTextEventType);
        mEventDesc = (TextView) findViewById(R.id.editTextEventDesc);

        mEventName.setText(event.getTitle());
        mEventLocation.setText(event.getLocaiton());
        mEventType.setText(event.getType());
        mEventDesc.setText(event.getDesc());

    }
}
