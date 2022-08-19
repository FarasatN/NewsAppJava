package com.farasatnovruzov.newsappjava.ui;

import androidx.annotation.NonNull;

import com.farasatnovruzov.newsappjava.databinding.ItemFavBinding;
import com.farasatnovruzov.newsappjava.model.Articles;

public class ItemFavViewHolder extends BaseViewHolder{

    ItemFavBinding itemFavBinding;

    public ItemFavViewHolder(@NonNull ItemFavBinding itemFavBinding) {
        super(itemFavBinding.getRoot());
        this.itemFavBinding = itemFavBinding;
    }

    @Override
    public void bindData(Articles item) {
        itemFavBinding.setArticlesItems(item);
    }
}
