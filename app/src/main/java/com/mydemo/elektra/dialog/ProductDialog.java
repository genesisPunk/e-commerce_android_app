package com.mydemo.elektra.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.mydemo.elektra.MyApplication;
import com.mydemo.elektra.PrimeActivity;
import com.mydemo.elektra.R;
import com.mydemo.elektra.ui.share.ShareFragment;
import com.squareup.picasso.Picasso;

public class ProductDialog extends DialogFragment {



    TextView removeButton, updateButton,name, price, subtractButton, addButton, quantityText;
    ImageView productImage;
    public int x, y, position, quantity;


    public ProductDialog() {
        // Required empty public constructor
    }

    public ProductDialog(int position) {
        this.position = position;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_product, null);

        removeButton = view.findViewById(R.id.removeButton);
        updateButton = view.findViewById(R.id.update_button);
        name = view.findViewById(R.id.dialog_product_name);
        price = view.findViewById(R.id.dialog_product_price);
        productImage = view.findViewById(R.id.dialog_product_image);
        subtractButton = view.findViewById(R.id.dialog_button_subtract);
        addButton = view.findViewById(R.id.dialog_button_add);
        quantityText = view.findViewById(R.id.dialog_quantity_textview);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.setCancelable(false);

        return dialog;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        quantity = MyApplication.getQuantityList().get(position);

        name.setText(MyApplication.getCartItemsList().get(position).getName());
        price.setText("₹" + MyApplication.getCartItemsList().get(position).getPrice());
        quantityText.setText(Integer.toString(quantity));

        Picasso.get()
                .load(MyApplication.getCartItemsList().get(position).getImage())
                .placeholder(R.drawable.progress_picasso)
                .error(R.drawable.error_image)
                .into(productImage);

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


        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyApplication.getCartItemsList().remove(position);
                MyApplication.getQuantityList().remove(position);
                ((PrimeActivity)getActivity()).setCount(getContext(), Integer.toString(MyApplication.getCartItemsList().size()));
                ShareFragment shareFragment1 = (ShareFragment) getParentFragment().getChildFragmentManager().getFragments().get(0);
                shareFragment1.update();
                dismiss();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                MyApplication.getQuantityList().set(position, quantity);

                ShareFragment shareFragment1 = (ShareFragment) getParentFragment().getChildFragmentManager().getFragments().get(0);
                shareFragment1.update();
                dismiss();
            }
        });

    }

}
