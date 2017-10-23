package com.google.firebase.udacity.friendlychat;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



class SectionsPagerAdapter extends FragmentPagerAdapter {


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

            ChatsFragment chatsFragment = new ChatsFragment();
            return  chatsFragment;



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
