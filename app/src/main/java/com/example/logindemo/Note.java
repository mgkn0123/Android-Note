package com.example.logindemo;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Note {
    public  String content;


    public Note() {
    }

    public Note(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
