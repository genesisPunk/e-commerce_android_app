package com.mydemo.elektra.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mydemo.elektra.FirstFragment;
import com.mydemo.elektra.PrimeActivity;
import com.mydemo.elektra.ProductActivity;
import com.mydemo.elektra.adapters.CategoryAdapter;
import com.mydemo.elektra.adapters.ImageAdapter;
import com.mydemo.elektra.dialog.ProductDialog;
import com.mydemo.elektra.R;
import com.mydemo.elektra.adapters.ExpandableListAdapter;
import com.mydemo.elektra.firebase.FirebaseDatabaseHelper;
import com.mydemo.elektra.models.Category;
import com.mydemo.elektra.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {

    protected Context context;

    protected FragmentManager fragmentManager;

    private StorageReference mStorageRef;

    RecyclerView categoryListview;
    LinearLayout categoriesProgressLayout;


    ViewPager viewPager;
    ImageAdapter imageAdapter;

    CategoryAdapter adapter;
    List<Category> categoryList;




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_home, container, false);

//        searchView = root.findViewById(R.id.search_view);

        categoryListview = root.findViewById(R.id.category_listview);
        categoriesProgressLayout = root.findViewById(R.id.progress_categories);

        categoriesProgressLayout.setVisibility(View.VISIBLE);
        categoryListview.setVisibility(View.GONE);

//        image1 = root.findViewById(R.id.image1);
//        image2 = root.findViewById(R.id.image2);
//        image3 = root.findViewById(R.id.image3);

        viewPager = root.findViewById(R.id.view_pager);

        //    getActivity().setTitle("Coupons");

        //setting fragment manager in global variable
        fragmentManager = getActivity().getSupportFragmentManager();




//
//        woodFinishLayout = root.findViewById(R.id.woodFinishLayout);
//        thinnersLayout = root.findViewById(R.id.thinnersLayout);
//        stainsLayout = root.findViewById(R.id.stainsLayout);
//        primersLayout = root.findViewById(R.id.primersLayout);
//        coatingLayout = root.findViewById(R.id.coatingLayout);
//        paintsLayout = root.findViewById(R.id.paintsLayout);


        return root;
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

     //   mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://elektra-rajeev.appspot.com");

        categoryList = new ArrayList<Category>(10);
        prepareList();

        prepareImageList();

    }

    private void prepareImageList()  {

        ArrayList<String> urls = new ArrayList<String>();

        urls.add("https://www.online.citibank.co.in/special-offers/home/images/New_Offers/thumbnail/New-Offers.jpg");
        urls.add("https://image.shutterstock.com/image-photo/bright-spring-view-cameo-island-260nw-1048185397.jpg");
        urls.add("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg");

        imageAdapter = new ImageAdapter(getContext(), urls);

        viewPager.setAdapter(imageAdapter);


    }

    private void prepareList() {

        new FirebaseDatabaseHelper().readCategory(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Category> categories, List<String> keys) {

                adapter = new CategoryAdapter(categories, keys, context, HomeFragment.this);

                GridLayoutManager gridLayoutManager = new GridLayoutManager( context,2, GridLayoutManager.VERTICAL, false);
                categoryListview.setLayoutManager(gridLayoutManager);
                categoryListview.setAdapter(adapter);

                categoriesProgressLayout.setVisibility(View.GONE);
                categoryListview.setVisibility(View.VISIBLE);

            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUploaded() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });



    }





}