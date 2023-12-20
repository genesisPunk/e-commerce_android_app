package com.mydemo.elektra;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.mydemo.elektra.models.SubItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SecondFragment extends Fragment {


    // SubItem subitem;

    TextView name, description, price, subtractButton, addButton, quantityText;
    ImageView imageView;

    Button addToCartButton;

    SubItem subItem;

    CountDrawable badge;

    int quantity = 1;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_second, container, false);

        name = view.findViewById(R.id.name_textview);
        price = view.findViewById(R.id.price_textview);
        description = view.findViewById(R.id.description_textview);
        imageView = view.findViewById(R.id.product_image);

        subtractButton = view.findViewById(R.id.button_subtract);
        addButton = view.findViewById(R.id.button_add);
        quantityText = view.findViewById(R.id.quantity_textview);

        addToCartButton = view.findViewById(R.id.addToCartButton);

        return view;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        subItem = new SubItem();

        Bundle b = getArguments();

        subItem.setName(b.getString("name"));
        subItem.setDescription(b.getString("description"));
        subItem.setPrice(b.getString("price"));
        subItem.setImage(b.getString("image"));

        name.setText(subItem.getName());
        description.setText(subItem.getDescription());
        price.setText("₹" + subItem.getPrice());

        quantityText.setText(Integer.toString(quantity));

        Picasso.get()
                .load(subItem.getImage())
                .placeholder(R.drawable.progress_picasso)
                .error(R.drawable.error_image)
                .into(imageView);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity++;
                quantityText.setText(Integer.toString(quantity));
            }
        });

        subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity--;
                if (quantity > 0) {
                    quantityText.setText(Integer.toString(quantity));
                } else {
                    quantityText.setText("1");
                    quantity = 1;
                }

            }
        });

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SENDTO);
//                sendIntent.setPackage("com.whatsapp");
//                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
//                sendIntent.setType("text/plain");
//                startActivity(sendIntent);

//                String order = "*" + "Name:" + "*" + " " + MyApplication.getUser().getName() + "\n"
//                        + "*" + "Phone no:" + "*" + " " + MyApplication.getUser().getPhone() + "\n" +
//                        "------------------------------" + "\n" +
//                        "*" + "Product:" + "*" + " " + subItem.getName() + "\n" +
//                        "*" + "Quantity:" + "*" + " " + quantity;
//
//                    openWhatsApp(order);


                if(MyApplication.getCartItemsList().contains(subItem)){
                    Toast toast = Toast.makeText(getContext(),"Item already added!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else{
                    MyApplication.setCartItems(subItem);
                    MyApplication.setQuantity(quantity);
                    ((PrimeActivity)getActivity()).setCount(getContext(), Integer.toString(MyApplication.getCartItemsList().size()));

                }





            }
        });

    }

    protected void openWhatsApp (String order) {
       // String smsNumber = "919404739347"; // E164 format without '+' sign
        String smsNumber = "917091693695";
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, order);
        sendIntent.putExtra("jid", smsNumber + "@s.whatsapp.net"); //phone number without "+" prefix
        sendIntent.setPackage("com.whatsapp");
        if (sendIntent.resolveActivity(getActivity().getPackageManager()) == null) {
            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(sendIntent);
    }


}
