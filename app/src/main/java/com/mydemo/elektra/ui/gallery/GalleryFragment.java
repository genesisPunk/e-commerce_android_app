package com.mydemo.elektra.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mydemo.elektra.MyApplication;
import com.mydemo.elektra.adapters.ListviewAdapter;
import com.mydemo.elektra.dialog.CouponDialog;
import com.mydemo.elektra.models.Transaction;
import com.mydemo.elektra.R;
import com.mydemo.elektra.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GalleryFragment extends Fragment {

    Button couponButton;
    EditText couponEditText;
    LinearLayout progressLayout, buttonLayout;

    //data fetching
    List<Transaction> transactions = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        couponButton = root.findViewById(R.id.coupon_button);
        couponEditText = root.findViewById(R.id.coupon_edittext);
        progressLayout = root.findViewById(R.id.progressbar_coupon);
        buttonLayout = root.findViewById(R.id.layout_button);
        return root;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressLayout.setVisibility(View.GONE);
        buttonLayout.setVisibility(View.VISIBLE);

        couponButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = couponEditText.getText().toString().trim();

                if(number.isEmpty()){
                    couponEditText.setError("Coupon code is required");
                    couponEditText.requestFocus();
                    return;
                }

                if(number.length() != 5) {
                    couponEditText.setError("Enter all 5 digits/letters of coupon");
                    couponEditText.requestFocus();
                    return;
                }

                redeemCoupon(number);

            }
        });
    }

    private void redeemCoupon(String number) {

        progressLayout.setVisibility(View.VISIBLE);
        buttonLayout.setVisibility(View.GONE);

        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();

        String url = "https://mighty-island-50443.herokuapp.com/coupon/redeem-points";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("value", number);
            jsonObject.put("mobile_number", MyApplication.getUser().getPhone());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(
                Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        progressLayout.setVisibility(View.GONE);
                        buttonLayout.setVisibility(View.VISIBLE);

                        try {
                            JSONObject message = response;
                            Boolean success = message.getBoolean("success");

                            if(success){
                                CouponDialog couponDialog = new CouponDialog();
                                couponDialog.show(getParentFragmentManager(),"couponFragment");
                            }else{
                                Toast.makeText(getContext(), "Coupon is invalid or already redeemed!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            Toast.makeText(MyApplication.getInstance(), e.toString(), Toast.LENGTH_SHORT).show();
                        }




                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
                buttonLayout.setVisibility(View.VISIBLE);
                Toast.makeText(MyApplication.getInstance(),error.toString(), Toast.LENGTH_SHORT).show();
            }
        })

        {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        requestQueue.add(jsonObjRequest);




    }
}