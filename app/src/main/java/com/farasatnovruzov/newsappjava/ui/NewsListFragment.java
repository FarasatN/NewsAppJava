package com.farasatnovruzov.newsappjava.ui;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.farasatnovruzov.newsappjava.R;
import com.farasatnovruzov.newsappjava.model.Articles;
import com.farasatnovruzov.newsappjava.model.NewsResponse;
import com.farasatnovruzov.newsappjava.service.NewsAPI;
import com.farasatnovruzov.newsappjava.util.AppSettings;
import com.farasatnovruzov.newsappjava.util.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsListFragment extends Fragment{
    RecyclerView recyclerView;
    NewsListAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    Handler handler = new Handler();
    TextView errorText;
    ProgressBar newsListProgres;

    Retrofit retrofit;
    ArrayList<Articles> newsList;
//    private final String BASE_URL = "https://newsapi.org/";
//    private final String API_KEY = "9b1f13a99d574ca482a98bb1badcbae4";

    String langKey = "en";
    String categKey = "general";
    AppSettings settings;




    public NewsListFragment() {
        // Required empty public constructor
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Intent intent = new Intent();
//        if (getActivity().getIntent().getBooleanExtra("for_back",true)){
//            Navigation.findNavController(view).navigate(R.id.action_home_to_favourite);
//        }else {

            newsList = new ArrayList<>();


            errorText = view.findViewById(R.id.error_text);
            newsListProgres = view.findViewById(R.id.news_list_loading);
            recyclerView = view.findViewById(R.id.rv_list_news);
            swipeRefreshLayout = view.findViewById(R.id.news_list_swipe);

            swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.green));
//        swipeRefreshLayout.setRefreshing(true);


//        loadData();
            loadNewsFromPref();

            newsListProgres.setVisibility(View.VISIBLE);
            if (newsList.isEmpty()) {
                errorText.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                newsListProgres.setVisibility(View.GONE);

            } else {
                errorText.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                newsListProgres.setVisibility(View.GONE);
            }
            recyclerView.getRecycledViewPool().clear();
            recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setHasFixedSize(false);
            adapter = new NewsListAdapter(new DiffUtilNewsItemCallBack());
            recyclerView.setAdapter(adapter);
            //we need a news list data to test the news recyclerview
            //i have already created a data class that generate a random list of news item


//        loadData();
            adapter.submitList(newsList);
            adapter.notifyDataSetChanged();

            adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    recyclerView.smoothScrollToPosition(positionStart);
                    //now everything look great see u in the next part
                }
            });
//        adapter.notifyDataSetChanged();

//                swipeRefreshLayout.setRefreshing(false);


            System.out.println("onviewcreated " + newsList.size());
//        System.out.println("loaded");

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
//                if (newsList.isEmpty()){
//                    loadData();
//                }
                    loadData();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            newsListProgres.setVisibility(View.VISIBLE);
//                        if (newsList.isEmpty()){
//                            loadData();
//                        }
//                        loadData();
                            if (newsList.isEmpty()) {
                                errorText.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            } else {
                                errorText.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                            adapter.submitList(newsList);
                            adapter.notifyDataSetChanged();

                            System.out.println("news when swipe: " + newsList.size());
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
//                        adapter.notifyDataSetChanged();
                            newsListProgres.setVisibility(View.GONE);
                        }
                    }, 1500);
                }
            });
//        loadData();

            System.out.println("after the swipe: " + newsList.size());
        }
//    }


    private void loadData() {

        NewsAPI newsAPI = retrofit.create(NewsAPI.class);
        Call<NewsResponse> call = newsAPI.getBreakingNews(langKey,categKey, Constants.BreakingPageSize, Constants.API1);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
//                    System.out.println(response.body().totalResults);
                    assert response.body() != null;
                    if (response.body().status.equals("error")){
//                        System.out.println(response.body().status);
                        Constants.API = Constants.API2;
                    }else if (response.body().status.equals("error")){
                        Constants.API = Constants.API3;
                    }else if (response.body().status.equals("error")){
                        Constants.API = Constants.API1;
                    }

//                    if (!newsList.isEmpty()){
//                        newsList.clear();
//                    }

                    newsList = (ArrayList<Articles>) response.body().articles;
//                    list.clear();
//                    list = (ArrayList<Articles>) response.body().articles;
                    System.out.println("list size: "+ newsList.size());
                    saveData(newsList);

                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }


    private void loadNewsFromPref() {
       SharedPreferences sharedPreferences = getContext().getSharedPreferences("news", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("news", "");
        Type type = new TypeToken<ArrayList<Articles>>() {}.getType();

        newsList = gson.fromJson(json, type);

        if (newsList == null) {
            // if the array list is empty
            // creating a new array list.
            newsList = new ArrayList<>();
        }

    }

    private void saveData(ArrayList<Articles> list) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("news", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
        Gson gson = new Gson();
        String json = gson.toJson(newsList);
        editor.putString("news", json);
        editor.apply();
        System.out.println("shared news: "+newsList.size());

        adapter.notifyDataSetChanged();
}










    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Constants.API = Constants.API1;
        settings = new AppSettings(getContext().getApplicationContext());
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        applyLanguage(settings.getLanguage());
        applyCategory(settings.getCategory());

        if (settings.getLanguage()==0){
            langKey = "en";
        }else if (settings.getLanguage()==1){
            langKey = "tr";
        }else if (settings.getLanguage()==2){
            langKey = "ru";
        }else if (settings.getLanguage()==3){
            langKey = "de";
        }

        if (settings.getCategory()==0){
            categKey = "general";
        }else if (settings.getCategory()==1){
            categKey = "science";
        }else if (settings.getCategory()==2){
            categKey = "technology";
        }else if (settings.getCategory()==3){
            categKey = "sports";
        }else if (settings.getCategory()==4){
            categKey = "health";
        }else if (settings.getCategory()==5){
            categKey = "entertainment";
        }else if (settings.getCategory()==6) {
            categKey = "business";
        }
//        newsList = new ArrayList<>();
//        loadData();
//        System.out.println("oncreate: "+newsList.size());

    }











    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);

//        loadData();
//        System.out.println("oncreateview: "+newsList.size());
        //        loadData();
//        recyclerView = view.findViewById(R.id.rv_list_news);
//        recyclerView.setHasFixedSize(false);
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
//        adapter = new NewsListAdapter(new DiffUtilNewsItemCallBack());
//        recyclerView.setAdapter(adapter);
        //we need a news list data to test the news recyclerview
        //i have already created a data class that generate a random list of news item

//        adapter.submitList(newsList);
//
//        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
//            @Override
//            public void onItemRangeInserted(int positionStart, int itemCount) {
//                super.onItemRangeInserted(positionStart, itemCount);
//                recyclerView.smoothScrollToPosition(positionStart);
//                //now everything look great see u in the next part
//
//            }
//        });
//        swipeRefreshLayout  = view.findViewById(R.id.news_list_swipe);

        return view;
    }


    private void applyLanguage(int language) {
        switch (language) {
            case AppSettings.LANG_EN: settings.setLanguage(0);break;
            case AppSettings.LANG_TR: settings.setLanguage(1);break;
            case AppSettings.LANG_RU: settings.setLanguage(2);break;
            case AppSettings.LANG_DE: settings.setLanguage(3);break;
            default: settings.setLanguage(0);
        }

    }


    private void applyCategory(int category) {
        switch (category) {
            case AppSettings.CAT_GEN: settings.setCategory(0);break;
            case AppSettings.CAT_SCI: settings.setCategory(1);break;
            case AppSettings.CAT_TEC: settings.setCategory(2);break;
            case AppSettings.CAT_SPO: settings.setCategory(3);break;
            case AppSettings.CAT_HEA: settings.setCategory(4);break;
            case AppSettings.CAT_ENT: settings.setCategory(5);break;
            case AppSettings.CAT_BUS: settings.setCategory(6);break;
            default: settings.setCategory(0);

        }
    }

}

class WrapContentLinearLayoutManager extends LinearLayoutManager {
    public WrapContentLinearLayoutManager(Context context) {
        super(context);
    }

    public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public WrapContentLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            Log.e("TAG", "meet a IOOBE in RecyclerView");
        }
    }


}