package com.mydemo.elektra.ui.send;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mydemo.elektra.MyApplication;
import com.mydemo.elektra.PrimeActivity;
import com.mydemo.elektra.adapters.ListviewAdapter;
import com.mydemo.elektra.models.Transaction;
import com.mydemo.elektra.R;
import com.mydemo.elektra.models.TransactionModel;
import com.mydemo.elektra.models.User;
import com.mydemo.elektra.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SendFragment extends Fragment {


    protected Context context;

    protected ListView transactionsListView;
    protected TextView amount, date,time, message, balance, viewTransactions, balanceButton;
    protected ArrayList<TransactionModel> transactionsObjectsList;

    protected LinearLayout progressLayoutBalance ,progressLayoutTransactions;

    private static ListviewAdapter adapter;

    public SendFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_send, container, false);

        transactionsListView =  view.findViewById(R.id.transactions_listview);
        balance = view.findViewById(R.id.balance_textview);
        balanceButton = view.findViewById(R.id.balance_button);
        transactionsObjectsList = new ArrayList<TransactionModel>();

        viewTransactions = view.findViewById(R.id.viewTransactionsButton);
        progressLayoutTransactions = view.findViewById(R.id.progressbar_transactions);
        progressLayoutBalance = view.findViewById(R.id.progressbar_balance);


        balance.setVisibility(View.GONE);
        balanceButton.setVisibility(View.VISIBLE);
        progressLayoutBalance.setVisibility(View.GONE);

        transactionsListView.setVisibility(View.GONE);
        progressLayoutTransactions.setVisibility(View.GONE);
        viewTransactions.setVisibility(View.VISIBLE);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

     //   createDatabase();



        balanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                balance.setVisibility(View.GONE);
                balanceButton.setVisibility(View.GONE);
                progressLayoutBalance.setVisibility(View.VISIBLE);

                fetchBalance();

            }
        });

        viewTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchTransaction();
            }
        });

    }

    private void fetchBalance() {

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("Users/");

        myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User userProfile;
                userProfile = dataSnapshot.getValue(User.class);
                MyApplication.setUser(userProfile);

                balance.setText(MyApplication.getUser().getWallet().toString());

                balance.setVisibility(View.VISIBLE);
                balanceButton.setVisibility(View.GONE);
                progressLayoutBalance.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                balance.setVisibility(View.GONE);
                balanceButton.setVisibility(View.VISIBLE);
                progressLayoutBalance.setVisibility(View.GONE);

                Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();
            }

        });



    }

    private void fetchTransaction() {

        transactionsListView.setVisibility(View.GONE);
        progressLayoutTransactions.setVisibility(View.VISIBLE);
        viewTransactions.setVisibility(View.GONE);

        retrieveTransactions();

    }



//    http://localhost:1406/customer/transaction
//    input
//    {
//        "mobile_number": "9700905391"
//    }





    private void retrieveTransactions() {

        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();

        String url = "https://mighty-island-50443.herokuapp.com/customer/transaction";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile_number", MyApplication.getUser().getPhone());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(
                Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        transactionsListView.setVisibility(View.VISIBLE);
                        progressLayoutTransactions.setVisibility(View.GONE);
                        viewTransactions.setVisibility(View.GONE);


                        try {
                            JSONObject message = response;
                            Boolean success = message.getBoolean("success");

                            if(success){

                                JSONArray transactionsArray = message.getJSONArray("transactions");

                                if (transactionsArray.length() == 0){
                                    Toast.makeText(context, "No Transactions", Toast.LENGTH_SHORT).show();
                                    transactionsListView.setVisibility(View.GONE);
                                    progressLayoutTransactions.setVisibility(View.GONE);
                                    viewTransactions.setVisibility(View.VISIBLE);

                                }else{
                                    for (int i =0; i < transactionsArray.length() ;i++){

                                        JSONObject transactionObject = transactionsArray.getJSONObject(i);

                                        TransactionModel transaction = new TransactionModel(transactionObject.getString("timestamp"),transactionObject.getString("points"));
                                        transactionsObjectsList.add(transaction);

                                    }

                                    adapter= new ListviewAdapter(transactionsObjectsList, context);
                                    transactionsListView.setAdapter(adapter);
                                }

                            }else{
                                Toast.makeText(MyApplication.getInstance(), success.toString(), Toast.LENGTH_SHORT).show();
                        }
                        } catch (JSONException e) {
                            Toast.makeText(MyApplication.getInstance(), e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                transactionsListView.setVisibility(View.GONE);
                progressLayoutTransactions.setVisibility(View.GONE);
                viewTransactions.setVisibility(View.VISIBLE);
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