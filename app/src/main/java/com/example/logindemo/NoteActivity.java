package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private Button btnGoToSecondActivity;

    private DatabaseReference mDatabase;

    private static final String TAG = "NoteActivity";

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
                newSecretNote.setText(value);

                /*
                // show password from global variable
                NoteApplication app = (NoteApplication) getApplication();
                newSecretNote.setText(app.getUserPassword());
                */

                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: CHECK PASSWORD

                // TODO: CHANGE NOTE WITH AES
                newSecretNote = findViewById(R.id.etSecretNote);

                // save to database
                myRef.setValue(newSecretNote.getText().toString());
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
