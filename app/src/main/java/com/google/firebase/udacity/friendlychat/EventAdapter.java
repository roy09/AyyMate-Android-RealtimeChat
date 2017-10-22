package com.google.firebase.udacity.friendlychat;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EventAdapter extends ArrayAdapter<Event> {
    public EventAdapter(Context context, int resource, List<Event> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_event, parent, false);
        }

        TextView eventName = (TextView) convertView.findViewById(R.id.txtViewName);
        TextView eventLocation = (TextView) convertView.findViewById(R.id.txtViewNameLocation);
        TextView eventType = (TextView) convertView.findViewById(R.id.txtViewType);

        Event event = getItem(position);

        eventName.setText(event.getTitle());
        eventLocation.setText(event.getLocaiton());
        eventType.setText(event.getType());

        return convertView;
    }
}
