package com.farasatnovruzov.newsappjava.ui;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.farasatnovruzov.newsappjava.model.Articles;

import java.util.Objects;

public class DiffUtilNewsItemCallBack extends DiffUtil.ItemCallback<Articles> {

    @Override
    public boolean areItemsTheSame(@NonNull Articles oldItem, @NonNull Articles newItem) {
        return Objects.equals(oldItem.publishedAt, newItem.publishedAt);
    }

    @Override
    public boolean areContentsTheSame(@NonNull Articles oldItem, @NonNull Articles newItem) {
        return Objects.equals(oldItem.publishedAt, newItem.publishedAt);
    }
}
