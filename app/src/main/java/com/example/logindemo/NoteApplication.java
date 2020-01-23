package com.example.logindemo;

import android.app.Application;

public class NoteApplication extends Application {
    private String userPassword;

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
