package com.example.logindemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SecondActivity extends AppCompatActivity {
    private Button SecretNote;
    private Button ChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

       SecretNote = (Button)findViewById(R.id.btnGoToSecretNote);
       ChangePassword = (Button)findViewById(R.id.btnGoToPasswordChange);


        SecretNote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(SecondActivity.this, NoteActivity.class));

            }
        });


        ChangePassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(SecondActivity.this, ChangePasswordActivity.class));

            }
        });
    }
}
