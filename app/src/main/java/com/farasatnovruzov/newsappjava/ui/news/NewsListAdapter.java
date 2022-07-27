package com.farasatnovruzov.newsappjava.ui.news;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.farasatnovruzov.newsappjava.R;
import com.farasatnovruzov.newsappjava.data.NewsItem;
import com.farasatnovruzov.newsappjava.databinding.ItemNewsImageBinding;
import com.farasatnovruzov.newsappjava.databinding.ItemNewsTextBinding;
import com.farasatnovruzov.newsappjava.model.Articles;

public class NewsListAdapter extends ListAdapter<Articles,BaseViewHolder> {


//    Glide.with(this).load(article.urlToImage).into(ivArticleImage)
//    tvSource.text = article.source.name
//    tvTitle.text = article.title
//    tvDescription.text = article.description
//    tvPublishedAt.text = article.publishedAt

    OnNewsItemClickEvent listener;
    public static final int VIEWTYPE_NEWS_TEXT = 0;
    public static final int VIEWTYPE_NEWS_IMAGE = 1;
    public static final int VIEWTYPE_NEWS_VIDEO = 2;


    public interface OnNewsItemClickEvent {
        void onItemTextClick();
        void onItemClickEvent();
        void onItemLongClick();

    }

    public void setOnNewsItemClickListener(OnNewsItemClickEvent listener){
        this.listener = listener;
    }

    public NewsListAdapter(@NonNull DiffUtil.ItemCallback<Articles> diffCallback) {
        super(diffCallback);
    }

//    @Override
//    public int getItemViewType(int position) {
//        return getItem(position).getViewType();
//    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // based on the item viewtype we create viewholder
        // we have two viewholder for now : text and image
        // and don't forget that we are going to use data Binding feature to bind our data in the viewholder class
        // we need to configure our viewholders so they can accept a binding layout like so ...
        // new we can instantiate our viewholder safely

        switch (viewType) {
            case VIEWTYPE_NEWS_TEXT:
                ItemNewsTextBinding itemNewsTextBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_news_text,parent,false);
                return new TextViewHolder(itemNewsTextBinding);
            case VIEWTYPE_NEWS_IMAGE:
                ItemNewsImageBinding itemNewsImageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_news_image,parent,false);
                return new ImageViewHolder(itemNewsImageBinding);
            default:
                ItemNewsTextBinding defaultNewsTextBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_news_image,parent,false);
                return new TextViewHolder(defaultNewsTextBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
            holder.bindData(getItem(position));
    }
}
