package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.util.Log;

import com.google.firebase.database.core.Tag;


public class NoteActivity extends AppCompatActivity {
    private EditText newSecretNote;
    private Button save;
    private Button btnGoToSecondActivity;
    private static final String TAG = "NoteActivity";
    private EncryptionDecryption encryptionDecryption = new EncryptionDecryption();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        btnGoToSecondActivity = (Button) findViewById(R.id.btnGoToSecondActivity);
        save = findViewById(R.id.btnSaveNote);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("note");

        newSecretNote = findViewById(R.id.etSecretNote);


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);

                NoteApplication app = (NoteApplication) getApplication();
                String userPassword = app.getUserPassword();

                String decryptionCode = "";
                try {
                    decryptionCode = encryptionDecryption.decrypt(userPassword, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                newSecretNote.setText(decryptionCode);

                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                newSecretNote = findViewById(R.id.etSecretNote);
                String originalString = newSecretNote.getText().toString();

                NoteApplication app = (NoteApplication) getApplication();
                String userPassword = app.getUserPassword();

                // Change note
                String encryptionCode = "";
                try {
                    encryptionCode = encryptionDecryption.encrypt(userPassword, originalString);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // save to database
                myRef.setValue(encryptionCode);
                Toast.makeText(NoteActivity.this, "Changed", Toast.LENGTH_SHORT).show();
            }
        });


        btnGoToSecondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NoteActivity.this, SecondActivity.class));
            }
        });


    }
}
