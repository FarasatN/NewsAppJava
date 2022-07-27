package com.farasatnovruzov.newsappjava.ui.news;


import androidx.annotation.NonNull;
import com.farasatnovruzov.newsappjava.data.NewsItem;
import com.farasatnovruzov.newsappjava.databinding.ItemNewsImageBinding;
import com.farasatnovruzov.newsappjava.databinding.ItemNewsTextBinding;
import com.farasatnovruzov.newsappjava.model.Articles;
import com.farasatnovruzov.newsappjava.model.NewsResponse;

public class ImageViewHolder extends BaseViewHolder{

    ItemNewsImageBinding itemNewsImageBinding;

    public ImageViewHolder(@NonNull ItemNewsImageBinding itemNewsImageBinding) {
        super(itemNewsImageBinding.getRoot());
        this.itemNewsImageBinding = itemNewsImageBinding;
    }
    @Override
    public void bindData(Articles item) {
        itemNewsImageBinding.setArticlesImage(item);
    }

    @Override
    public ItemNewsTextBinding getItemNewsTextBinding() {
        return null;
    }

    @Override
    public ItemNewsImageBinding getItemNewsImageBind() {
        return null;
    }



//    ItemNewsImageBinding itemNewsImageBinding;
//    public ImageViewHolder(@NonNull ItemNewsImageBinding itemNewsImageBinding) {
//        super(itemNewsImageBinding.getRoot());
//        this.itemNewsImageBinding = itemNewsImageBinding;
//    }

//    @Override
//    public void bindData(NewsItem item) {
//        itemNewsImageBinding.setNewsItemImage(item);
//    }
//
//    @Override
//    public void bindData(Articles item) {
//        itemNewsImageBinding.setArticlesImage(item);
//    }

//    @Override
//    public ItemNewsTextBinding getItemNewsTextBinding() {
//        return null;
//    }
//
//    @Override
//    public ItemNewsImageBinding getItemNewsImageBind() {
//        return null;
//    }
}
