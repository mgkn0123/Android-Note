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

public class NoteActivity extends AppCompatActivity {
    private EditText newSecretNote;
    private Button save;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private Button btnGoToSecondActivity;

    private EditText password;

    private DatabaseReference mDatabase;
    Note note = new Note();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        btnGoToSecondActivity = (Button) findViewById(R.id.btnGoToSecondActivity);
        password = (EditText) findViewById(R.id.etPassword);
        save = findViewById(R.id.btnSaveNote);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());



        newSecretNote = findViewById(R.id.etSecretNote);
      //  newSecretNote.setText(note.getContent());
      //  newSecretNote.setText("pppp");

// ...
        mDatabase = FirebaseDatabase.getInstance().getReference();
      // String content =  mDatabase.child("note").child("content").toString();
        String content =  firebaseDatabase.getReference().getRoot().child("note").child("content").toString();



        newSecretNote.setText(content);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: CHECK PASSWORD

                // TODO: CHANGE NOTE WITH AES
                newSecretNote = findViewById(R.id.etSecretNote);
                note.setContent(newSecretNote.getText().toString());
                myRef.setValue(note);
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
