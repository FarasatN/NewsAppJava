package com.farasatnovruzov.newsappjava.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farasatnovruzov.newsappjava.R;
import com.farasatnovruzov.newsappjava.data.FakeDataSource;
import com.farasatnovruzov.newsappjava.ui.news.DiffUtilNewsItemCallBack;
import com.farasatnovruzov.newsappjava.ui.news.NewsListAdapter;

public class SearchFragment extends Fragment {

    RecyclerView rvSearch;
    NewsListAdapter adapter;

    public SearchFragment() {
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
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvSearch = view.findViewById(R.id.search_rv);
        rvSearch.setLayoutManager(new GridLayoutManager(getContext(),2));
        rvSearch.setHasFixedSize(true);
        adapter = new NewsListAdapter(new DiffUtilNewsItemCallBack());
        rvSearch.setAdapter(adapter);
//        FakeDataSource fakeDataSource = new FakeDataSource();
//        adapter.submitList(fakeDataSource.getFakeListNews());
    }
}