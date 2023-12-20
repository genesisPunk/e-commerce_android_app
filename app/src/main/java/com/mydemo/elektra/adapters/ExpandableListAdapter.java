package com.mydemo.elektra.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mydemo.elektra.R;
import com.mydemo.elektra.models.MainItem;
import com.mydemo.elektra.models.SubItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<MainItem> listProducts = new ArrayList<MainItem>(5) ;
    private HashMap<MainItem, List<SubItem>> listDataChild = new HashMap<MainItem, List<SubItem>>(5);


    public ExpandableListAdapter(Context context, List<MainItem> listProducts, HashMap<MainItem, List<SubItem>> listDataChild) {
        this.context = context;
        this.listProducts = listProducts;
        this.listDataChild = listDataChild;

        Log.d("testing", Integer.toString(listProducts.size()));
    }

    @Override
    public int getGroupCount() {
        return listProducts.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listDataChild.get(this.listProducts.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listProducts.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listDataChild.get(this.listProducts.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        MainItem mainItem = (MainItem) getGroup(groupPosition);

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }

        TextView productTextView = convertView.findViewById(R.id.main_name_textview);
        TextView productDescription = convertView.findViewById(R.id.main_description_textview);
        ImageView productImage = convertView.findViewById(R.id.main_product_image);
        productTextView.setText(mainItem.getName());
        productDescription.setText(mainItem.getDescription());

        Picasso.get()
                .load(mainItem.getImage())
                .placeholder(R.drawable.progress_picasso)
                .error(R.drawable.error_image)
                .into(productImage);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

   //     final String childText = (String) getChild(groupPosition, childPosition);

        SubItem subItem = (SubItem) getChild(groupPosition, childPosition);

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }

        TextView listChild = convertView.findViewById(R.id.sub_name_textview);

        listChild.setText(subItem.getName());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
