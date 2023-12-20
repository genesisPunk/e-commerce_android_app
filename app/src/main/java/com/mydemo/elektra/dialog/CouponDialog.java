package com.mydemo.elektra.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.mydemo.elektra.R;

public class CouponDialog extends DialogFragment {

    LinearLayout progressBar, couponSuccessLayout, couponExpireLayout;




    public CouponDialog() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_coupon, null);

        progressBar = view.findViewById(R.id.progressbar);
        couponSuccessLayout = view.findViewById(R.id.coupon_success_layout);
        couponExpireLayout = view.findViewById(R.id.coupon_expire_layout);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        Dialog dialog = builder.create();

        return dialog;
    }

}
