package com.mydemo.elektra.ui.share;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.mydemo.elektra.MyApplication;
import com.mydemo.elektra.R;
import com.mydemo.elektra.adapters.CartListViewAdapter;
import com.mydemo.elektra.dialog.CouponDialog;
import com.mydemo.elektra.dialog.ProductDialog;
import com.mydemo.elektra.models.SubItem;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Internal;

public class ShareFragment extends Fragment {


    ListView cartListView;
    ArrayList<SubItem> itemList;
    ArrayList<Integer> itemQuantity;
    CartListViewAdapter cartListViewAdapter;
    TextView totalPriceView, totalItemsView;
    Button placeOrderButton;

    int totalPrice = 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_share, container, false);

        cartListView = root.findViewById(R.id.cart_list_view);
        totalItemsView = root.findViewById(R.id.total_items_textview);
        totalPriceView = root.findViewById(R.id.total_price_textview);
        placeOrderButton = root.findViewById(R.id.placeOrderButton);

        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setListView();

        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalPrice > 10000){

                    sendMail();

                }else{

                    Toast toast = Toast.makeText(getContext(), "Minimum cart value is ₹10000/-", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

    }

    private void setListView() {

        itemList = MyApplication.getCartItemsList();
        itemQuantity = MyApplication.getQuantityList();

        totalItemsView.setText(Integer.toString(itemList.size()));

        int j = 0;

        for (int i = 0; i < itemList.size(); i++){
            j = j + ((Integer.parseInt(itemList.get(i).getPrice())) * itemQuantity.get(i));
        }

        totalPrice = j;
        totalPriceView.setText( "₹" + Integer.toString(totalPrice));


        cartListViewAdapter = new CartListViewAdapter(itemList, itemQuantity, getContext());
        cartListView.setAdapter(cartListViewAdapter);
        cartListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ProductDialog productDialog = new ProductDialog(i);
                productDialog.show(getParentFragmentManager(),"cartFragment");
            }
        });
    }

    private void sendMail() {

        String order =  "Name:" + " " + MyApplication.getUser().getName() + "\n"
                        +  "Phone no:" + " " + MyApplication.getUser().getPhone() + "\n" +
                        "------------------------------" + "\n";

        for (int i = 0; i < itemList.size(); i++){
            order = order + itemList.get(i).getName() + " : " + itemQuantity.get(i) + "\n";
        }

        order = order + "------------------------------" + "\n" + "Total Price : " + "₹"+totalPrice;

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"info@elektracoatings.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "ORDER <> " + "₹"+totalPrice);
        i.putExtra(Intent.EXTRA_TEXT   , order);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

    }

    public void update() {
        setListView();
    }

}