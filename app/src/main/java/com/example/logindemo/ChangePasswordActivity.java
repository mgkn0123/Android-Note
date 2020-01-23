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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import android.util.Log;

public class ChangePasswordActivity extends AppCompatActivity {

    private Button btnSaveNewPassword;
    private Button btnGoToSecondActivity;
    private TextView tvNewPassword;
    private FirebaseAuth firebaseAuth;
    private EditText etNewPassword;
    private EditText etOldPassword;
    private static final String TAG = "ChangePasswordActivity";
    private EncryptionDecryption encryptionDecryption = new EncryptionDecryption();
    private Boolean clicked = true;


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

                                                NoteApplication app = (NoteApplication) getApplication();
                                                app.setUserPassword(etNewPassword.getText().toString());

                                                Toast.makeText(ChangePasswordActivity.this, "Changed", Toast.LENGTH_SHORT).show();

                                                // save note by new key
                                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                final DatabaseReference myRef = database.getReference("note");


                                                myRef.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        String note = dataSnapshot.getValue(String.class);

                                                        // decryption note
                                                        NoteApplication app = (NoteApplication) getApplication();
                                                        String userPassword = app.getUserPassword();

                                                        String decryptionCode = "";
                                                        try {
                                                            decryptionCode = encryptionDecryption.decrypt(userPassword, note);
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }

                                                        // encryption note with new password
                                                        String encryptionCode = "";
                                                        try {
                                                            encryptionCode = encryptionDecryption.encrypt(userPassword, decryptionCode);
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }

                                                        if (clicked == true) {
                                                            myRef.setValue(encryptionCode);
                                                            clicked = false;
                                                        }

                                                        Log.d(TAG, "Value is: " + encryptionCode);
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError error) {
                                                        Log.w(TAG, "Failed to read value.", error.toException());
                                                    }
                                                });


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

