package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState)

        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_note);

            newSecretNote = findViewById(R.id.etSecretNote);
            save = findViewById(R.id.btnSaveNote);

            firebaseAuth = FirebaseAuth.getInstance();
            firebaseDatabase = FirebaseDatabase.getInstance();

            final DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
            // DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

            //   newSecretNote.setText("Milena");

            save.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Note note = new Note(newSecretNote.toString());

                    myRef.setValue(note);

                }
            });







        }
}
