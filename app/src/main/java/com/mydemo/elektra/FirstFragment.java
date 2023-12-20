package com.mydemo.elektra;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mydemo.elektra.adapters.ExpandableListAdapter;
import com.mydemo.elektra.firebase.FirebaseMainDatabaseHelper;
import com.mydemo.elektra.firebase.FirebaseSubDatabaseHelper;
import com.mydemo.elektra.models.MainItem;
import com.mydemo.elektra.models.SubItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class  FirstFragment extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mainitemReference;
    private DatabaseReference subitemReference;
    private List<MainItem> mainItemsList = new ArrayList<>(10);
    private List<SubItem> allSubItemsList = new ArrayList<>(10);
    private List<String> mainKey;
    protected HashMap<MainItem, List<SubItem>> hashList = new HashMap<MainItem, List<SubItem>>(10);

    protected ExpandableListAdapter listAdapter;
    protected ExpandableListView expListView;
    protected List<String> productList;
    protected HashMap<String, List<String>> listDataChild;
    protected String categoryId;

    protected LinearLayout progressMainLayout;
    protected TextView noItemsTextView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_first, container, false);

        expListView = root.findViewById(R.id.expandableListViewProducts);
        progressMainLayout = root.findViewById(R.id.progressMainitems);
        noItemsTextView = root.findViewById(R.id.notitems_textview);


        expListView.setVisibility(View.GONE);
        progressMainLayout.setVisibility(View.VISIBLE);
        noItemsTextView.setVisibility(View.GONE);



        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle b = getArguments();
        categoryId = b.getString("id");

        firebaseDatabase = FirebaseDatabase.getInstance();
        prepareMainItemListTest();

        getActivity().setTitle("xxxx");

//        listAdapter = new ExpandableListAdapter(getContext(), productList, listDataChild);

}

    @Override
    public void onDestroy() {
        super.onDestroy();

//        listDataChild.clear();;
   //     productList.clear();

    }

    private void prepareListData() {

        productList = new ArrayList<String>(31);
        listDataChild = new HashMap<String, List<String>>(31);

        if (productList.isEmpty()) {

            //Adding child Data
            productList.add("NC FILLERS");
            productList.add("NC SANDING SEALER");
            productList.add("NC LACQUERS");
            productList.add("R.T.U. NC LACQUERS");
            productList.add("CLEAR MELAMINE WOOD COATINGS");

            productList.add("PREMIUM SUPER SERIES");
            productList.add("PIGMENTED MELAMINE COATINGS");
            productList.add("MELAMINE HARDENER");
            productList.add("WOOD STAINS");
            productList.add("ENAMEL COATINGS & PAINTS");

            productList.add("PU WOOD FINISHES \n - ALKYDS System");
            productList.add("PU WOOD FINISHES \n  - ACRYLIC System");
            productList.add("ISOLATOR");
            productList.add("PU PIGMENTED COATINGS");
            productList.add("PU PIGMENTED COATINGS - PURE ACRYLICS");

            productList.add("GLASS COATINGS");
            productList.add("NC PUTTY");
            productList.add("NC PRIMERS & BASECOATS");
            productList.add("NC PAINTS");
            productList.add("NC METALLICS");

            productList.add("AUTO REFINISHES - FD/QD");
            productList.add("ENAMEL COATINGS & PAINTS ");
            productList.add("HAMMER TONE PAINTS ");
            productList.add("STOVING COATINGS & PAINTS");
            productList.add("ANTICORROSIVE & WATER PROOFING");

            productList.add("PAINT REMOVER / STRIPPER");
            productList.add("FLOOR COATINGS");
            productList.add("THINNERS & DILUENTS");
            productList.add("DECORATIVE PAINTS");


            //Adding child data
            List<String> product1 = new ArrayList<String>();
            product1.add("NC WOOD FILLER");


            //Adding child data
            List<String> product2 = new ArrayList<String>();
            product2.add("SANDING SEALER(White)");
            product2.add("SANDING SEALER(Ple/Yellow)");


            //Adding child data
            List<String> product3 = new ArrayList<String>();
            product3.add("NC LACQUER CLEAR");
            product3.add("NC LACQUER CLEAR - WW 100");
            product3.add("NC LACQUER CLEAR - MATT");
            product3.add("NC LACQUER CLEAR - G 05.10.20.30.50.60");


            //Adding child data
            List<String> product4 = new ArrayList<String>();
            product4.add("RTU LACQUER CLEAR");
            product4.add("RTU LACQUER CLEAR - WW 100");
            product4.add("RTU LACQUER CLEAR - MATT");
            product4.add("RTU LACQUER CLEAR - G 05.10.20.30.50.60");

            //Adding child data
            List<String> product5 = new ArrayList<String>();
            product5.add("MELAMINE SEALER");
            product5.add("MELAMINE GLOSS");
            product5.add("MELAMINE SATIN");
            product5.add("MELAMINE MATT");

            //Adding child data
            List<String> product6 = new ArrayList<String>();
            product6.add("PREMIUM SUPER SEALER");
            product6.add("PREMIUM SUPER GLOSS");
            product6.add("PREMIUM SUPER MATT");
            product6.add("PREMIUM SUPER SATIN");


            //Adding child data
            List<String> product7 = new ArrayList<String>();
            product7.add("RED MELAMINE GLOSS");
            product7.add("WHITE MELAMINE GLOSS");
            product7.add("BLACK MELAMINE GLOSS");
            product7.add("WHITE MELAMINE MATT");
            product7.add("BLACK MELAMINE MATT");

            //Adding child data
            List<String> product8 = new ArrayList<String>();
            product8.add("MELAMINE HARDENER");

            //Adding child data
            List<String> product9 = new ArrayList<String>();
            product9.add("STAINS");

            //Adding child data
            List<String> product10 = new ArrayList<String>();
            product10.add("SYN.CLEAR VARNISH");
            product10.add("1K SUPER GLOSS ");


            //Adding child data
            List<String> product11 = new ArrayList<String>();
            product11.add("PU CLEAR SEALER");
            product11.add("PU CLEAR MATT");
            product11.add("PU CLEAR SATIN");
            product11.add("PU CLEAR HIGH GLOSS");

            //Adding child data
            List<String> product12 = new ArrayList<String>();
            product12.add("PU CLEAR SEALER");
            product12.add("PU CLEAR MATT");
            product12.add("PU CLEAR SATIN");
            product12.add("PU CLEAR HIGH GLOSS");

            //Adding child data
            List<String> product13 = new ArrayList<String>();
            product13.add("ISOLATOR / Barrier properties");


            //Adding child data
            List<String> product14 = new ArrayList<String>();
            product14.add("PU WHITE PRIMER");
            product14.add("PU GREY PRIMER");
            product14.add("PU BLACK PRIMER");
            product14.add("PU WHITE GLOSS");
            product14.add("PU BLACK GLOSS");
            product14.add("PU MATT WHITE");
            product14.add("PU MATT BLACK");

            //Adding child data
            List<String> product15 = new ArrayList<String>();
            product15.add("ACRYLIC WHITE PRIMER");
            product15.add("ACRYLIC WHITE GLOSS");
            product15.add("ACRYLIC BLACK GLOSS");
            product15.add("ACRYLIC MATT WHITE");
            product15.add("ACRYLIC MATT BLACK");


            //Adding child data
            List<String> product16 = new ArrayList<String>();
            product16.add("CLEAR GLASS COATING");
            product16.add("WHITE GLASS COATING");
            product16.add("BLACK GLASS COATING");


            //Adding child data
            List<String> product17 = new ArrayList<String>();
            product17.add("NC PUTTY GREY");


            //Adding child data
            List<String> product19 = new ArrayList<String>();
            product19.add("NC PS GREY");
            product19.add("NC PS BLACK");
            product19.add("NC PS WHITE");
            product19.add("NC SUPER PS WHITE");


            //Adding child data
            List<String> product20 = new ArrayList<String>();
            product20.add("NC JET BLACK");
            product20.add("NC MATT BLACK");
            product20.add("NC BLACK");
            product20.add("NC COLORS Group I");
            product20.add("NC COLORS Group II");
            product20.add("NC MATT COLORS Group I");
            product20.add("NC MATT COLORS Group II");


            //Adding child data
            List<String> product21 = new ArrayList<String>();
            product21.add("NC SILVER");
            product21.add("NC SPARKLING SILVER");
            product21.add("FLAMBOUYANT COLORS");
            product21.add("NC GOLD");


            //Adding child data
            List<String> product22 = new ArrayList<String>();
            product22.add("AUTO CLEAR");
            product22.add("AUTO SILVER");
            product22.add("AUTO SPARKLING SILVER");
            product22.add("AUTO FLAMBOUYANT COLORS");
            product22.add("AUTO MATT COLORS");
            product22.add("AUTO BLACK");
            product22.add("AUTO COLORS Group I");
            product22.add("AUTO MATT COLORS Group II");


            //Adding child data
            List<String> product23 = new ArrayList<String>();
            product23.add("ENAMEL BLACK");
            product23.add("ENAMEL COLORS Group I");
            product23.add("ENAMEL COLORS Group II");


            //Adding child data
            List<String> product24 = new ArrayList<String>();
            product24.add("HAMMER TONE COLORS");


            //Adding child data
            List<String> product25 = new ArrayList<String>();
            product25.add("STOVING CLEAR");
            product25.add("STOVING MATT CLEAR");
            product25.add("STOVING BLACK");
            product25.add("STOVING WHITE");
            product25.add("STOVING COLORS");


            //Adding child data
            List<String> product26 = new ArrayList<String>();
            product26.add("RUBBER COAT(Multifunctional)");
            product26.add("CHLORINATED RUBBER PAINTS(Topcoat)");


            //Adding child data
            List<String> product27 = new ArrayList<String>();
            product27.add("PAINT REMOVER (Industrial)");


            //Adding child data
            List<String> product28 = new ArrayList<String>();
            product28.add("PAVER CLEAR");
            product28.add("PAVER COLORS");


            //Adding child data
            List<String> product29 = new ArrayList<String>();
            product29.add("PREMIUM SUPER THINNER");
            product29.add("NC THINNER 999");
            product29.add("NC THINNER 911");
            product29.add("NC THINNER EX-14");
            product29.add("MELAMINE THINNER");
            product29.add("PREMIUM SUPER THINNER - Spout Tin");
            product29.add("NC THINNER 999 - Spout Tin");
            product29.add("NC THINNER 911 - Spout Tin");
            product29.add("MELAMINE THINNER - Sput Tin");
            product29.add("PU THINNER - SD");
            product29.add("PU THINNER - MD");
            product29.add("PU THINNER - RD");
            product29.add("STOVING THINNER");
            product29.add("EPOXY THINNER");
            product29.add("RETARDER THINNER");
            product29.add("NC THINNER - DUC");
            product29.add("NC THINNER - PPS");
            product29.add("CLEANING THINNER");

            //Adding child data
            List<String> product30 = new ArrayList<String>();
            product30.add("Ennovace WALL PRIMER");


            listDataChild.put(productList.get(0), product1);
            listDataChild.put(productList.get(1), product2);
            listDataChild.put(productList.get(2), product3);
            listDataChild.put(productList.get(3), product4);
            listDataChild.put(productList.get(4), product5);
            listDataChild.put(productList.get(5), product6);
            listDataChild.put(productList.get(6), product7);
            listDataChild.put(productList.get(7), product8);
            listDataChild.put(productList.get(8), product9);
            listDataChild.put(productList.get(9), product10);
            listDataChild.put(productList.get(10), product11);
            listDataChild.put(productList.get(11), product12);
            listDataChild.put(productList.get(12), product13);
            listDataChild.put(productList.get(13), product14);
            listDataChild.put(productList.get(14), product15);
            listDataChild.put(productList.get(15), product16);
            listDataChild.put(productList.get(16), product17);
            listDataChild.put(productList.get(17), product19);
            listDataChild.put(productList.get(18), product20);
            listDataChild.put(productList.get(19), product21);
            listDataChild.put(productList.get(20), product22);
            listDataChild.put(productList.get(21), product23);
            listDataChild.put(productList.get(22), product24);
            listDataChild.put(productList.get(23), product25);
            listDataChild.put(productList.get(24), product26);
            listDataChild.put(productList.get(25), product27);
            listDataChild.put(productList.get(26), product28);
            listDataChild.put(productList.get(27), product29);
            listDataChild.put(productList.get(28), product30);
        }

    }

    private void prepareMainItemList(){

        mainitemReference = firebaseDatabase.getReference("products/mainitem");

        mainitemReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mainKey = new ArrayList();

                for ( DataSnapshot keyNode : dataSnapshot.getChildren()){
                    MainItem mainItem = keyNode.getValue(MainItem.class);
                    if(mainItem.getCategory_id().equals(categoryId)){
                        mainItemsList.add(mainItem);
                        mainKey.add(keyNode.getKey());
                    }
                }

       //         prepareSubItemList();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void prepareSubItemList() {

        subitemReference = firebaseDatabase.getReference("products/subitem");

        subitemReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<SubItem> allSubItemsList = new ArrayList<SubItem>();
                List<String> subKey = new ArrayList();

                for ( DataSnapshot keyNode : dataSnapshot.getChildren()){
                    SubItem subItem = keyNode.getValue(SubItem.class);
                        allSubItemsList.add(subItem);
                        subKey.add(keyNode.getKey());
                }

                fetchSubItems(allSubItemsList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void prepareMainItemListTest(){

        mainitemReference = firebaseDatabase.getReference("products/mainitem");

        mainKey = new ArrayList();

        new FirebaseMainDatabaseHelper(categoryId).readMainItems(new FirebaseMainDatabaseHelper.MainDataStatus() {

            @Override
            public void DataIsLoaded(List<MainItem> mainItems, List<String> mainKeys) {

                mainItemsList.clear();

                if (listAdapter != null){
                    listAdapter.notifyDataSetChanged();
                } else{

                }

                mainItemsList = mainItems;
                mainKey = mainKeys;
                prepareSubItemListTest();

                if(mainItems.size() == 0){

                    expListView.setVisibility(View.GONE);
                    progressMainLayout.setVisibility(View.GONE);
                    noItemsTextView.setVisibility(View.VISIBLE);


                }

                Log.d("testing", "size:" + mainItems.size());

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

    private void prepareSubItemListTest() {

        new FirebaseSubDatabaseHelper().readSubItems(new FirebaseSubDatabaseHelper.SubDataStatus() {

            @Override
            public void DataIsLoaded(List<SubItem> subItems) {

                allSubItemsList.clear();

                allSubItemsList = subItems;


                fetchSubItems(allSubItemsList);

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


    private void fetchSubItems(List<SubItem> allSubItemsList) {

        for(int i=0; (i<mainItemsList.size()) ; i++){

            List<SubItem> subitemsList = new ArrayList<SubItem>();


            for ( SubItem subitem : allSubItemsList ) {
               if( subitem.getMainitem_id().equals(mainKey.get(i))){
                    subitemsList.add(subitem);
               }
            }
            hashList.put(mainItemsList.get(i),subitemsList);

            setupAdapter();
        }






    }

    private void setupAdapter() {

        listAdapter = new ExpandableListAdapter(getContext(), mainItemsList, hashList);

        expListView.setVisibility(View.VISIBLE);
        progressMainLayout.setVisibility(View.GONE);
        noItemsTextView.setVisibility(View.GONE);

        expListView.setAdapter(listAdapter);



        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    expListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {


               SubItem subitem = hashList.get(mainItemsList.get(groupPosition)).get(childPosition);

                final  Bundle args = new Bundle();
                args.putString("name", subitem.getName());
                args.putString("description", subitem.getDescription());
                args.putString("price", subitem.getPrice());
                args.putString("image", subitem.getImage() );


                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_firstFragment_to_secondFragment, args);

                return false;
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mainItemsList.clear();
        hashList.clear();
    }
}
