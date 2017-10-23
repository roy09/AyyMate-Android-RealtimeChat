package com.google.firebase.udacity.friendlychat;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


class SectionsPagerAdapter extends FragmentPagerAdapter {

    boolean groupYES = true;


    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch(position) {
            case 0:
                FriendsFragment friendsFragment = new FriendsFragment();
                return friendsFragment;


                //RequestsFragment requestsFragment = new RequestsFragment();
                //return requestsFragment;
                //return  null;
            case 1:

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // User is signed in
                    String userUID = user.getUid();
                    Log.e("UID", userUID);
                    DatabaseReference quickRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userUID);
                    quickRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.e("OH MY GAWD", "EKAHNE ASCHI");
                            if(!dataSnapshot.hasChild("group")){
                                groupYES = false;
                                Log.e("OH MY GAWD", "FALSE KORE DILAM");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                } else {
                    // No user is signed in
                }

                Log.e("NONONONO", Boolean.toString(groupYES));

                if (groupYES){
                    ChatsFragment chatsFragment = new ChatsFragment();
                    return  chatsFragment;
                } else{
                    RequestsFragment reqFragment = new RequestsFragment();
                    return  reqFragment;

                }




           // case 2:
                //FriendsFragment friendsFragment = new FriendsFragment();
               // return friendsFragment;

            default:
                return null;


        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position){

        switch (position) {
            case 0:
                return "FRIENDS";

            case 1:
                return "GLOBAL MATES";

            //case 2:
               // return "FRIENDS";

            default:
                return null;
        }

    }

}
