package com.farasatnovruzov.newsappjava.util;


import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.farasatnovruzov.newsappjava.R;

public class BindingAdapters {

    // here we create our custom binding adapter like so

    @BindingAdapter("glide_url")
    public static void loadImg(ImageView imageView,String url) {
        // we will use glide library to load the image from a url

        Glide.with(imageView.getContext()).load(url).into(imageView);
        // new let's use this adapter in our xml file
    }

    @BindingAdapter("glide_circular")
    public static void loadUserImg(ImageView view, String url){
        //glide can make any image in a circular format with ease
        Glide.with(view.getContext()).load(url).circleCrop().into(view);
    }

    @BindingAdapter("set_background")
    public static void setBackGround(ImageView view, String color) {

        switch (color) {
            case "RED" : view.setBackgroundColor(view.getContext().getResources().getColor(R.color.red));
            break;
            case "BLACK" : view.setBackgroundColor(view.getContext().getResources().getColor(R.color.black));
            break;
            case "YELLOW" : view.setBackgroundColor(view.getContext().getResources().getColor(R.color.yellow));
            break;
            case "PURPLE" : view.setBackgroundColor(view.getContext().getResources().getColor(R.color.purple));
            break;
            case "BLUE" : view.setBackgroundColor(view.getContext().getResources().getColor(R.color.blue));
            break;
            default: view.setBackgroundColor(view.getContext().getResources().getColor(R.color.red));
        }
    }

    @BindingAdapter("set_checked")
    public static void toggleFav(ImageView view, boolean isFav) {

        if (isFav) {
            view.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.red));
        }else{
            view.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.dark_icon_tint_color));
        }
    }
}
