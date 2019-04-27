package com.example.a15850.thediary;

import android.widget.Button;

public class CardItem {
    String mTextResource;
    String mTitleResource;

    public CardItem(String title, String text ) {
        mTitleResource = title;
        mTextResource = text;
    }

    public String getText() {
        return mTextResource;
    }

    public String getTitle() {
        return mTitleResource;
    }
}
