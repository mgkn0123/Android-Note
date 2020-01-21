package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.Tag;

import android.util.Log;

public class ChangePasswordActivity extends AppCompatActivity {

    private Button btnSaveNewPassword;
    private Button btnGoToSecondActivity;
    private TextView tvNewPassword;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    private EditText etNewPassword;
    private EditText etOldPassword;
    private static final String TAG = "ChangePasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        btnSaveNewPassword = (Button) findViewById(R.id.btnSaveNewPassword);
        tvNewPassword = (TextView) findViewById(R.id.tvNewPassword);
        firebaseAuth = FirebaseAuth.getInstance();
        etOldPassword = (EditText) findViewById(R.id.etOldPassword);
        etNewPassword = (EditText) findViewById(R.id.etNewPassword);

        btnGoToSecondActivity = (Button) findViewById(R.id.btnGoToSecondActivity);

        btnGoToSecondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChangePasswordActivity.this, SecondActivity.class));

            }
        });


        btnSaveNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();

                AuthCredential credential = EmailAuthProvider
                        .getCredential(user.getEmail(), etOldPassword.getText().toString());

                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    user.updatePassword(etNewPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(ChangePasswordActivity.this, "Changed", Toast.LENGTH_SHORT).show();
                                                // TODO: save note by new key

                                            } else {
                                                Toast.makeText(ChangePasswordActivity.this, "Error password not updated", Toast.LENGTH_SHORT).show();
                                                Log.e(TAG, "Error in updating password",
                                                        task.getException());
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(ChangePasswordActivity.this, "Error auth failed", Toast.LENGTH_SHORT).show();

                                }


                            }
                        });


        /*

             // WITHOUT OLD PASSWORD VERSSION

                user.updatePassword(etNewPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ChangePasswordActivity.this,"Changed", Toast.LENGTH_SHORT).show();
                                } else {

                                    Log.e(TAG, "Error in updating password",
                                            task.getException());
                                    Toast.makeText(ChangePasswordActivity.this,
                                            "Failed to update password.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

        */

            }


        });

    }


}

