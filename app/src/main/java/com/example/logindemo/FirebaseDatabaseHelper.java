package com.example.logindemo;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceNotes;
    private List<Note> notes = new ArrayList<>();
    public interface DataStatus{
        void DataIsLoaded(List<Note> notes, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }


    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceNotes= mDatabase.getReference("notes");
    }

    public void readNotes(final DataStatus dataStatus){
        mReferenceNotes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               notes.clear();
               List<String> keys = new ArrayList<>();
               for (DataSnapshot keyNode:dataSnapshot.getChildren()){
                   keys.add(keyNode.getKey());
                   Note note = keyNode.getValue(Note.class);
                   notes.add(note);


               }
               dataStatus.DataIsLoaded(notes,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
