package com.mydemo.elektra.adapters;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApiNotAvailableException;
import com.mydemo.elektra.R;
import com.mydemo.elektra.models.Category;
import com.mydemo.elektra.ui.home.HomeFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    List<Category> categories;
    List<String> keys;
    Context context;
    LayoutInflater inflater;
    HomeFragment homeFragment;

    public CategoryAdapter(List<Category> categories, List<String> keys, Context context, HomeFragment homeFragment) {
        this.categories = categories;
        this.keys = keys;
        this.inflater = LayoutInflater.from(context);
        this.homeFragment = homeFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_category, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final  Bundle args = new Bundle();
        args.putString("id",keys.get(position));

        holder.name.setText(categories.get(position).getName());


        Picasso.get()
                .load(categories.get(position).getImage())
                .placeholder(R.drawable.progress_picasso)
                .error(R.drawable.error_image)
                .into(holder.image);

       // holder.description.setText(categories.get(position).getDescription());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(homeFragment)
                        .navigate(R.id.action_nav_home_to_firstFragment, args);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, description;
        ImageView image;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cat_name);

        //    description = itemView.findViewById(R.id.cat_description);
            image = itemView.findViewById(R.id.cat_imageview);
            view = itemView;
        }
    }


}
