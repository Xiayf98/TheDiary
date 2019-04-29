package com.example.a15850.thediary.database;

import cn.bmob.v3.BmobObject;

public class Diary extends BmobObject {
    private String email;
    private String nickname;
    private String title;
    private String body;
    private boolean open=false;
    private Integer likes_record=null;

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public boolean isOpen() {
        return open;
    }

    public Integer getLikes_record() {
        return likes_record;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setLikes_record(Integer likes_record) {
        this.likes_record = likes_record;
    }
}
