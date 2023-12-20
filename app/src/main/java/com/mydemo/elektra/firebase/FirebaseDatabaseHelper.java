package com.mydemo.elektra.firebase;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mydemo.elektra.models.Category;
import com.mydemo.elektra.models.MainItem;
import com.mydemo.elektra.models.SubItem;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference categoryReference;

    private List<Category> categoriesList = new ArrayList<Category>();


    public interface DataStatus{

        void DataIsLoaded(List<Category> categories, List<String> keys);
        void DataIsInserted();
        void DataIsUploaded();
        void DataIsDeleted();

    }

    public FirebaseDatabaseHelper() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        categoryReference = firebaseDatabase.getReference("products/category");
    }


    public void readCategory(final DataStatus dataStatus){

        categoryReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                categoriesList.clear();
                List<String> keys = new ArrayList();

                for ( DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Category category = keyNode.getValue(Category.class);
                    categoriesList.add(category);
                }

                dataStatus.DataIsLoaded(categoriesList, keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
