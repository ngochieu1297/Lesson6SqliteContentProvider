package com.example.admin.lesson6sqlitecontentprovider;

public class Contact {
    private String mName;
    private String mPhoneNumber;
    private boolean mIsFavorite;

    public Contact(String name, String phoneNumber, boolean isFavorite) {
        mName = name;
        mPhoneNumber = phoneNumber;
        mIsFavorite = isFavorite;
    }

    public String getName() {
        return mName;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public boolean isFavorite() {
        return mIsFavorite;
    }

    public void setFavorite(boolean isFavorite) {
        mIsFavorite = isFavorite;
    }
}
