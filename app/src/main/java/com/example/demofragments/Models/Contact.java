package com.example.demofragments.Models;

public class Contact {
    private String mName;
    private String mPhoneNumber;
    private boolean mIsSelected;

    public Contact(String name, String phoneNumber) {
        mName = name;
        mPhoneNumber = phoneNumber;
        mIsSelected = false;
    }

    public String getName() {
        return mName;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setSelected(boolean selected) {
        mIsSelected = selected;
    }

    @Override
    public String toString() {
        return mName + " " + mPhoneNumber;
    }
}