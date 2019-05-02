package com.example.a15850.thediary.database;

import java.io.File;

import cn.bmob.v3.BmobUser;

public class MyBmobUser extends BmobUser {
    private String nickname;
    private File picture;
    private Number diaryNum;

    public String getNickname() {
        return nickname;
    }

    public File getPicture() {
        return picture;
    }

    public Number getDiaryNum() {
        return diaryNum;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPicture(File picture) {
        this.picture = picture;
    }

    public void setDiaryNum(Number diaryNum) {
        this.diaryNum = diaryNum;
    }
}
