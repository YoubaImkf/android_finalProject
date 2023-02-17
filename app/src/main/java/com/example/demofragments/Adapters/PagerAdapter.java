package com.example.demofragments.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.demofragments.Fragments.ResponsesFragment;
import com.example.demofragments.Fragments.SenderFragment;
import com.example.demofragments.Fragments.ContactsFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        switch (position){
            case 0:
                return new ContactsFragment();
            case 1:
                return new ResponsesFragment();
            case 2:
                return new SenderFragment();
            default:
                return null;

        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Contacts";
            case 1 :
                return "Responses";
            case 2 :
                return "Send SMS";
            default:
                return "";

        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}





