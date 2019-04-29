package com.example.a15850.thediary;

import android.widget.Button;

public class CardItem {
    String mTextResource;
    String mTitleResource;
    String diaryID=null;

    public CardItem(String title, String text,String diary_id ) {
        mTitleResource = title;
        mTextResource = text;
        diaryID=diary_id;
    }

    public String getText() {
        return mTextResource;
    }

    public String getTitle() {
        return mTitleResource;
    }

    public String getDiaryID() {
        return diaryID;
    }
}
