package com.farasatnovruzov.newsappjava.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farasatnovruzov.newsappjava.R;
import com.farasatnovruzov.newsappjava.api.NewsAPI;
import com.farasatnovruzov.newsappjava.data.FakeDataSource;
import com.farasatnovruzov.newsappjava.model.Articles;
import com.farasatnovruzov.newsappjava.model.NewsResponse;
import com.farasatnovruzov.newsappjava.ui.news.DiffUtilNewsItemCallBack;
import com.farasatnovruzov.newsappjava.ui.news.NewsListAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsListFragment extends Fragment {

    RecyclerView recyclerView;
    NewsListAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    Retrofit retrofit;
    ArrayList<NewsResponse> newsList;
    private final String BASE_URL = "https://newsapi.org/";
    private final String API_KEY = "ea96fc26b3d14df8938fbb351e1200a3";

    public NewsListFragment() {
        // Required empty public constructor
    }

    private void loadData() {
        NewsAPI newsAPI = retrofit.create(NewsAPI.class);
        Call<NewsResponse> call = newsAPI.getBreakingNews("az", "science",1,API_KEY);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
//                    System.out.println(response.body().totalResults);
//                    System.out.println(response.body());
                    for (Articles item : response.body().articles) {
                        System.out.println(item.url);
                    }


                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadData();


        recyclerView = view.findViewById(R.id.rv_list_news);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        adapter = new NewsListAdapter(new DiffUtilNewsItemCallBack());
        recyclerView.setAdapter(adapter);
        //we need a news list data to test the news recyclerview
        //i have already created a data class that generate a random list of news item
//        FakeDataSource fakeDataSource = new FakeDataSource();
//        adapter.submitList(fakeDataSource.getFakeListNews());

        //let's instantiate the swiperefreshlayout we could use view binding, but i will use the classic findViewById method
        swipeRefreshLayout  = view.findViewById(R.id.news_list_swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //TODO: get the new list of news list will do that when we'll work with a real news
                // for now i will just simulate a refresh process
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //here we update the news list with new data
                        //i will just call a helper method to generate a new updated
//                        adapter.submitList(fakeDataSource.getFakeUpdatedStaticListNews());

                        swipeRefreshLayout.setRefreshing(false);
                        //as you can see the list news updated successfully but the recyclerview doesn't notify the user about the new data
                        //to do that i will listen for any data change on recyclerview and if there is any data added or changed
                        //I will scroll the rv to that data item position....

                        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                            @Override
                            public void onItemRangeInserted(int positionStart, int itemCount) {
                                super.onItemRangeInserted(positionStart, itemCount);
                                recyclerView.smoothScrollToPosition(positionStart);
                                //now everything look great see u in the next part

                            }
                        });
                    }
                },1200);
            }
        });
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

//        loadData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }
}