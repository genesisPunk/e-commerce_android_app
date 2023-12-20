package com.mydemo.elektra.adapters;


import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.mydemo.elektra.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends PagerAdapter {

    Context context;
    List<String> images;

    public ImageAdapter() {
    }

    public ImageAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(imageView);

        Picasso.get()
                .load(images.get(position))
                .placeholder(R.drawable.progress_picasso)
                .error(R.drawable.error_image)
                .into(imageView);

        return imageView;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView)object);
    }


}
