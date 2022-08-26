package com.farasatnovruzov.newsappjava.ui;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.farasatnovruzov.newsappjava.R;
import com.farasatnovruzov.newsappjava.model.Articles;
import com.farasatnovruzov.newsappjava.model.NewsResponse;
import com.farasatnovruzov.newsappjava.service.NewsAPI;
import com.farasatnovruzov.newsappjava.util.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFragment extends Fragment {

    private RecyclerView rvSearch;
    private SearchListAdapter adapter;
    private EditText searchText;
    private ProgressBar searchProgressBar;
    private Handler handler = new Handler();
    private String searchKey = "";
    private Retrofit retrofit;
    private ArrayList<Articles> newsList;
//    private NestedScrollView nestedSV;


    public SearchFragment() {
        // Required empty public constructor
    }

    private void loadData() {
        NewsAPI newsAPI = retrofit.create(NewsAPI.class);
        Call<NewsResponse> call = newsAPI.searchForNews(searchKey, Constants.SearchPageSize, Constants.API);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().status.equals("error")) {
//                        System.out.println(response.body().status);
                            Constants.API = Constants.API2;
                            System.out.println("API2");
                            loadData();
                            if (response.body().status.equals("error")) {
                                Constants.API = Constants.API3;
                                System.out.println("API3");
                                loadData();
                                if (response.body().status.equals("error")) {
                                    Constants.API = Constants.API4;
                                    System.out.println("API4");
                                    loadData();
                                    if (response.body().status.equals("error")) {
                                        Constants.API = Constants.API1;
                                        System.out.println("API1");
                                        loadData();
                                        if (response.body().status.equals("error")) {
                                            Constants.API = Constants.API5;
                                            System.out.println("API1");

                                        }
                                    }
                                }
                            }
                        }
                    }
//                    System.out.println(response.body());
//                    for (Articles item : response.body().articles) {
//                        System.out.println(item.url);
//                    }
                    newsList = (ArrayList<Articles>) response.body().articles;


                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //        Constants.API = Constants.API3;
        super.onCreate(savedInstanceState);
        newsList = new ArrayList<>();
//        RetrofitInstance.getInstance();
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

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
//        loadData();
        searchText = view.findViewById(R.id.search_et);
        searchProgressBar = view.findViewById(R.id.search_loading);

        setSearchEmpty();

//        nestedSV = view.findViewById(R.id.nested_SV);
        rvSearch = view.findViewById(R.id.search_rv);
        rvSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSearch.setHasFixedSize(true);
        adapter = new SearchListAdapter(new DiffUtilNewsItemCallBack());
        rvSearch.setAdapter(adapter);

        /*
        nestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.
                    if (page<=limit){
                        page+=20;
                    }
                    // on below line we are making our progress bar visible.
//                    loadingPB.setVisibility(View.VISIBLE);
//                        if (page < 10) {
                    // on below line we are again calling
                    // a method to load data in our array list.
//                            loadNewsFromPref();
                    loadData();
                    adapter.submitList(newsList);
                    adapter.notifyDataSetChanged();
//                        }
                }
            }
        });
        */
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchProgressBar.setVisibility(View.VISIBLE);
                setSearchEmpty();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchProgressBar.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        searchProgressBar.setVisibility(View.VISIBLE);
                        searchKey = searchText.getText().toString().trim();
                        loadData();

                        adapter.submitList(newsList);
                        System.out.println("list size: "+newsList.size());
                        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                            @Override
                            public void onItemRangeInserted(int positionStart, int itemCount) {
                                super.onItemRangeInserted(positionStart, itemCount);
                                rvSearch.smoothScrollToPosition(positionStart);
                                //now everything look great see u in the next part

                            }
                        });

//                        searchProgressBar.setVisibility(View.GONE);
                        if (newsList.isEmpty()){
                            searchProgressBar.setVisibility(View.VISIBLE);
                        }else if(searchKey.trim().isEmpty()){
                            searchProgressBar.setVisibility(View.GONE);
                        }else if (!newsList.isEmpty()){
                            searchProgressBar.setVisibility(View.GONE);
                        }

                        setSearchEmpty();
                    }
                }, 1300);

            }

            @Override
            public void afterTextChanged(Editable editable) {
                setSearchEmpty();
//                searchProgressBar.setVisibility(View.GONE);
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        searchProgressBar.setVisibility(View.GONE);
//                    }
//                }, 1000);
            }
        });
    }

    private void setSearchEmpty() {
        if (searchText.getText().toString().trim().isEmpty() || searchText.getText().toString().trim().toCharArray().length==0) {
            searchProgressBar.setVisibility(View.GONE);
            searchKey = "";
            newsList.clear();
        }
    }
}