package com.example.a15850.thediary;

import android.widget.Button;

public class CardItem {
    private int mTextResource;
    private int mTitleResource;

    public CardItem(int title, int text ) {
        mTitleResource = title;
        mTextResource = text;
    }

    public int getText() {
        return mTextResource;
    }

    public int getTitle() {
        return mTitleResource;
    }
}
