package com.mydemo.elektra.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mydemo.elektra.models.MainItem;
import com.mydemo.elektra.models.SubItem;

import java.util.ArrayList;
import java.util.List;

public class FirebaseSubDatabaseHelper {



    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference subitemReference;

    private List<SubItem> subitemsList = new ArrayList<SubItem>();


    public interface SubDataStatus{

        void DataIsLoaded(List<SubItem> Subitems);
        void DataIsInserted();
        void DataIsUploaded();
        void DataIsDeleted();

    }

    public FirebaseSubDatabaseHelper() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        subitemReference = firebaseDatabase.getReference("products/subitem");
    }


    public void readSubItems(final SubDataStatus subDataStatus){

        subitemReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for ( DataSnapshot keyNode : dataSnapshot.getChildren()){
                    SubItem subItem = keyNode.getValue(SubItem.class);
                    subitemsList.add(subItem);
                }

                subDataStatus.DataIsLoaded(subitemsList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




}
