package com.farasatnovruzov.newsappjava.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farasatnovruzov.newsappjava.R;
import com.farasatnovruzov.newsappjava.data.FakeDataSource;
import com.farasatnovruzov.newsappjava.data.NewsItem;
import com.farasatnovruzov.newsappjava.databinding.FragmentNewsDetailsBinding;


public class NewsDetailsFragment extends Fragment {

    public NewsDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentNewsDetailsBinding fragmentNewsDetailsBinding =
                DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.fragment_news_details,container,false);
        // we need to test if databinding works fine : so we need a news item
        // we can get a random item from the datasource class

        FakeDataSource fakeDataSource = new FakeDataSource();
        NewsItem item = fakeDataSource.generateRandomNewsItem();

        item.setFav(false);
        fragmentNewsDetailsBinding.setNewsItemData(item);


        return fragmentNewsDetailsBinding.getRoot();
//                inflater.inflate(R.layout.fragment_news_details, container, false);
        //now let's test this out, for now we can't access this fragment
        // i'll make some changes to navigation graph so i can display this fragment ...

    }
}