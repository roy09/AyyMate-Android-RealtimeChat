package com.google.firebase.udacity.friendlychat;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 21/10/2017.
 */

public class UserAdapter extends ArrayAdapter<Users> {
    public UserAdapter(Context context, int resource, List<Users> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_user, parent, false);
        }

        TextView userName = (TextView) convertView.findViewById(R.id.txtViewName);

        Users user = getItem(position);

        userName.setText(user.getName());


        return convertView;
    }
}
