package com.mydemo.elektra.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mydemo.elektra.models.Transaction;
import com.mydemo.elektra.R;
import com.mydemo.elektra.models.TransactionModel;

import java.util.ArrayList;


public class ListviewAdapter extends BaseAdapter {

    private ArrayList<TransactionModel> dataSet;
    private Context context;
    LayoutInflater layoutInflater = null;

    public ListviewAdapter(ArrayList<TransactionModel> dataSet, Context context) {
        this.dataSet = dataSet;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView date;
        TextView amount;

        View v;
        if(convertView == null){

            v = layoutInflater.inflate(R.layout.transactions_list_item, null);
            date = v.findViewById(R.id.textview_date);
            amount = v.findViewById(R.id.textview_amount);

//            if(dataSet.get(position).isCredit()){
//                amount.setTextColor(context.getResources().getColor(R.color.green));
//            }else{
//                amount.setTextColor(context.getResources().getColor(R.color.red));
//            }


        }else{
            return convertView;
        }

        date.setText(dataSet.get(position).getDate());
        amount.setText(dataSet.get(position).getAmount());

        return v;
    }
}
