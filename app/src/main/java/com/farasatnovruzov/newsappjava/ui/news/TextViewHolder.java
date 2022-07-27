package com.farasatnovruzov.newsappjava.ui.news;


import androidx.annotation.NonNull;
import com.farasatnovruzov.newsappjava.databinding.ItemNewsImageBinding;
import com.farasatnovruzov.newsappjava.databinding.ItemNewsTextBinding;
import com.farasatnovruzov.newsappjava.model.Articles;

public class TextViewHolder extends BaseViewHolder{

    ItemNewsTextBinding itemNewsTextBinding;

    public TextViewHolder(@NonNull ItemNewsTextBinding itemNewsTextBinding) {
        super(itemNewsTextBinding.getRoot());
        this.itemNewsTextBinding = itemNewsTextBinding;
    }

    @Override
    public void bindData(Articles item) {
        itemNewsTextBinding.setArticlesText(item);
    }

    @Override
    public ItemNewsTextBinding getItemNewsTextBinding() {
        return null;
    }

    @Override
    public ItemNewsImageBinding getItemNewsImageBind() {
        return null;
    }

//    ItemNewsTextBinding itemNewsTextBinding;
//
//    public TextViewHolder(@NonNull ItemNewsTextBinding itemNewsTextBinding) {
//        super(itemNewsTextBinding.getRoot());
//        this.itemNewsTextBinding = itemNewsTextBinding;
//
//    }
//
////    @Override
////    public void bindData(NewsItem item) {
////        itemNewsTextBinding.setNewsItemText(item);
////    }
//
//
//    @Override
//    public void bindData(NewsResponse item) {
//        itemNewsTextBinding.setNewsItemText(item);
//    }
//
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
