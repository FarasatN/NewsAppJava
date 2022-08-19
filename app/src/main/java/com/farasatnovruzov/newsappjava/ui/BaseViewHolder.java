package com.farasatnovruzov.newsappjava.ui;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.farasatnovruzov.newsappjava.model.Articles;


abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

//    public abstract void bindData(NewsItem item);
    public abstract void bindData(Articles item);


    //we will need these methods for shared view animation purpose
//    public abstract ItemNewsTextBinding getItemNewsTextBinding();
//    public abstract ItemNewsImageBinding getItemNewsImageBind();

}
