package com.mydemo.elektra.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mydemo.elektra.MyApplication;
import com.mydemo.elektra.R;
import com.mydemo.elektra.models.SubItem;
import com.mydemo.elektra.models.Transaction;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CartListViewAdapter extends BaseAdapter {

    protected List<SubItem> itemsList;
    protected ArrayList<Integer> quantityList;
//    protected int quantity;
    private Context context;
    LayoutInflater layoutInflater = null;

    int position;

    public CartListViewAdapter(ArrayList<SubItem> itemsList, ArrayList<Integer> quantityList, Context context) {
        this.itemsList = itemsList;
        this.quantityList = quantityList;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return itemsList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ImageView image;
        TextView name, subtractButton, addButton, price;
        final TextView quantityTextView;

  //      quantity = quantityList.get(i);

        position = i;

        View v;
        if(view == null){

            v = layoutInflater.inflate(R.layout.list_cart_item, null);
            image = v.findViewById(R.id.product_image);
            name = v.findViewById(R.id.name_textview);
            quantityTextView = v.findViewById(R.id.cart_quantity_textview);
            price = v.findViewById(R.id.cart_price_textview);

        }else{
            return view;
        }

        name.setText(itemsList.get(i).getName());
        price.setText("₹"+ itemsList.get(i).getPrice());
        quantityTextView.setText(quantityList.get(i).toString());
        Picasso.get()
                .load(itemsList.get(i).getImage())
                .placeholder(R.drawable.progress_picasso)
                .error(R.drawable.error_image)
                .into(image);


//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Toast.makeText(context, Integer.toString(position), Toast.LENGTH_SHORT).show();
//
//                int j = quantityList.get(position);
//                j++;
//                quantityList.set(position, j);
//                quantityTextView.setText(Integer.toString(j));
//
//            }
//        });


//        subtractButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Toast.makeText(context, Integer.toString(position), Toast.LENGTH_SHORT).show();
//
//                int j = quantityList.get(position);
//                j--;
//
//                if (j > 0) {
//                    quantityList.set(position, j);
//                    quantityTextView.setText(Integer.toString(j));
//                } else {
//                    j = 1;
//                    quantityList.set(position, j);
//                    quantityTextView.setText(Integer.toString(j));
//                }
//
//            }
//        });

        return v;

    }


}
