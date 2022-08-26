package com.farasatnovruzov.newsappjava.util;


import android.graphics.Color;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.farasatnovruzov.newsappjava.R;
import com.squareup.picasso.Picasso;

public class BindingAdapters {

    // here we create our custom binding adapter like so

    @BindingAdapter("glide_url")
    public static void loadImg(ImageView imageView,String url) {

        CircularProgressDrawable drawable = new CircularProgressDrawable(imageView.getContext());
        drawable.setColorSchemeColors(Color.parseColor("#01C9B6"));
        drawable.setCenterRadius(25f);
        drawable.setStrokeWidth(5f);
        // set all other properties as you would see fit and start it
        drawable.start();
        if (url!=null){
            try {

                Picasso.get()
                        .load(url)
                        .resize(170,110)
                        .centerCrop()
                        .error(android.R.drawable.screen_background_light_transparent)
                        .placeholder(drawable)
                                .into(imageView);


/*
                Glide.with(imageView.getContext()).load(url)
                        .centerCrop()
                        .timeout(6000)
//                .error(R.drawable.exclamation)
                        .listener(new RequestListener<Drawable>() {

                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                            imageView.setImageResource(R.drawable.exclamation);
//                            imageView.buildDrawingCache();
                                imageView.setVisibility(View.INVISIBLE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                return false;
                            }
                        })
//                    .thumbnail(0.5f)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(drawable)
                        .into(imageView);

 */
            }catch (Exception e){
                e.printStackTrace();
            }

        }

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
