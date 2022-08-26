package com.farasatnovruzov.newsappjava.ui;

import androidx.annotation.NonNull;

import com.farasatnovruzov.newsappjava.databinding.ItemNewsBinding;
import com.farasatnovruzov.newsappjava.model.Articles;

public class ItemViewHolder extends BaseViewHolder{

    private ItemNewsBinding itemNewsBinding;

    public ItemViewHolder(@NonNull ItemNewsBinding itemNewsBinding) {
        super(itemNewsBinding.getRoot());
        this.itemNewsBinding = itemNewsBinding;
    }


    @Override
    public void bindData(Articles item) {
        itemNewsBinding.setArticlesItems(item);
    }
}
