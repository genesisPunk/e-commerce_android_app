package com.mydemo.elektra.firebase;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mydemo.elektra.models.MainItem;

import java.util.ArrayList;
import java.util.List;

public class FirebaseMainDatabaseHelper {


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mainitemReference;

    private List<MainItem> mainitemsList = new ArrayList<MainItem>();
    private List<String> mainKey = new ArrayList<>();

    private String categoryId;


    public interface MainDataStatus{

        void DataIsLoaded(List<MainItem> mainItems, List<String> mainKeys);
        void DataIsInserted();
        void DataIsUploaded();
        void DataIsDeleted();

    }

    public FirebaseMainDatabaseHelper() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        mainitemReference = firebaseDatabase.getReference("products/mainitem");
    }

    public FirebaseMainDatabaseHelper(String categoryId) {
        this.categoryId = categoryId;
        firebaseDatabase = FirebaseDatabase.getInstance();
        mainitemReference = firebaseDatabase.getReference("products/mainitem");
    }

    public void readMainItems(final MainDataStatus mainDataStatus){


        mainitemReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for ( DataSnapshot keyNode : dataSnapshot.getChildren()){
                    MainItem mainItem = keyNode.getValue(MainItem.class);
                    if(mainItem.getCategory_id().equals(categoryId)){
                        mainitemsList.add(mainItem);
                        mainKey.add(keyNode.getKey());
                    }
                }

                mainDataStatus.DataIsLoaded(mainitemsList, mainKey);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
