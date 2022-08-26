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
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
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

    private ProgressBar loadingPB;
    private NestedScrollView nestedSV;
    private RecyclerView recyclerView;
    private NewsListAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler handler = new Handler();
    private TextView errorText;
    private ProgressBar newsListProgres;
    private SharedPreferences sharedPreferences;
    private Retrofit retrofit;
    private ArrayList<Articles> newsList;
    private AppSettings settings;
//    private final String BASE_URL = "https://newsapi.org/";
//    private final String API_KEY = "9b1f13a99d574ca482a98bb1badcbae4";
//    String langKey = "en";
//    String categKey = "general";

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

            System.out.println("page: "+Constants.page);
            errorText = view.findViewById(R.id.error_text);
            newsListProgres = view.findViewById(R.id.news_list_loading);
            recyclerView = view.findViewById(R.id.rv_list_news);
            nestedSV = view.findViewById(R.id.nested_SV);
            loadingPB = view.findViewById(R.id.pb_loading);
            swipeRefreshLayout = view.findViewById(R.id.news_list_swipe);
            swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.green));
//        swipeRefreshLayout.setRefreshing(true);


            loadData(Constants.page,Constants.API);
            loadNewsFromPref();
//            adapter.submitList(newsList);
        System.out.println("page after load: "+Constants.page);

            newsListProgres.setVisibility(View.VISIBLE);
            if (newsList.isEmpty()) {
                errorText.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                newsListProgres.setVisibility(View.GONE);
                newsListProgres.setVisibility(View.GONE);
                loadingPB.setVisibility(View.GONE);
            }else {
                errorText.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                newsListProgres.setVisibility(View.GONE);
            }

            recyclerView.getRecycledViewPool().clear();
            recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setHasFixedSize(false);
            adapter = new NewsListAdapter(new DiffUtilNewsItemCallBack());
            recyclerView.setAdapter(adapter);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
            recyclerView.setNestedScrollingEnabled(false);
//        loadData();
            adapter.submitList(newsList);
//            adapter.notifyDataSetChanged();
            adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    recyclerView.smoothScrollToPosition(positionStart);
                    //now everything look great see u in the next part
                }
            });
//                swipeRefreshLayout.setRefreshing(false);

            nestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                        // in this method we are incrementing page number,
                        // making progress bar visible and calling get data method.

                        // on below line we are making our progress bar visible.
                        loadingPB.setVisibility(View.VISIBLE);
                        recyclerView.getRecycledViewPool().clear();
//                        if (page < 10) {
                            // on below line we are again calling
                            // a method to load data in our array list.
//                            loadNewsFromPref();

                        loadData(Constants.page,Constants.API);
                        if (Constants.page<=Constants.limit){
                            if (newsList.size()==Constants.page){
                                Constants.page+=20;
                            }
                        }
                        adapter.submitList(newsList);

                        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                            @Override
                            public void onItemRangeInserted(int positionStart, int itemCount) {
                                super.onItemRangeInserted(positionStart, itemCount);
                                recyclerView.smoothScrollToPosition(positionStart);
                                //now everything look great see u in the next part
                            }
                        });
//                            adapter.notifyDataSetChanged();
                        saveData(newsList);
//                        }
                    }
                }
            });



            System.out.println("onviewcreated " + newsList.size());
//        System.out.println("loaded");

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
//                if (newsList.isEmpty()){
//                    loadData();
//                }
                    loadData(Constants.page,Constants.API);
//                    requireActivity().recreate();
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
                            recyclerView.getRecycledViewPool().clear();
//                            newsList.clear();
//                            newsList.addAll(newsList);
                            adapter.submitList(newsList);
//                            adapter.notifyDataSetChanged();

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
//                            recyclerView.getRecycledViewPool().clear();
                        }
                    }, 1300);
                }
            });
//        loadData();

            System.out.println("after the swipe: " + newsList.size());
        }
//    }


    private void loadData(int page,String api) {
//        api = Constants.API;
//        Constants.API = Constants.API1;
        NewsAPI newsAPI = retrofit.create(NewsAPI.class);
        Call<NewsResponse> call = newsAPI.getBreakingNews(Constants.langKey,Constants.categKey, page, api);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    System.out.println("fragment: response is succes: "+response.body().status);
                    if (response.body() != null) {

                        if(response.body().status.equals("ok")) {
                            System.out.println("fragment: " + response.body().status);
                            newsList = (ArrayList<Articles>) response.body().articles;
                            System.out.println("list size: "+ newsList.size());
                            saveData(newsList);
                            System.out.println("key: "+Constants.API+" api no: 1");
                        }else{
                            Constants.API = Constants.API2;
                            loadData(Constants.page,Constants.API);
                            if (response.body().status.equals("ok")){
                                System.out.println("fragment: " + response.body().status);
                                newsList = (ArrayList<Articles>) response.body().articles;
                                System.out.println("list size: "+ newsList.size());
                                saveData(newsList);
                                System.out.println("key: "+Constants.API+" api no: 2");

                            }else {
                                Constants.API = Constants.API3;
                                loadData(Constants.page,Constants.API);
                                if (response.body().status.equals("ok")){
                                    System.out.println("fragment: " + response.body().status);
                                    newsList = (ArrayList<Articles>) response.body().articles;
                                    System.out.println("list size: "+ newsList.size());
                                    saveData(newsList);
                                    System.out.println("key: "+Constants.API+" api no: 3");

                                }else {
                                    Constants.API = Constants.API4;
                                    loadData(Constants.page,Constants.API);
                                    if (response.body().status.equals("ok")){
                                        System.out.println("fragment: " + response.body().status);
                                        newsList = (ArrayList<Articles>) response.body().articles;
                                        System.out.println("list size: "+ newsList.size());
                                        saveData(newsList);
                                        System.out.println("key: "+Constants.API+" api no: 4");

                                    }else {
                                        Constants.API = Constants.API5;
                                        loadData(Constants.page,Constants.API);
                                        if (response.body().status.equals("ok")){
                                            System.out.println("fragment: " + response.body().status);
                                            newsList = (ArrayList<Articles>) response.body().articles;
                                            System.out.println("list size: "+ newsList.size());
                                            saveData(newsList);
                                            System.out.println("key: "+Constants.API+" api no: 5");

                                        }else {
                                            Constants.API = Constants.API6;
                                            loadData(Constants.page,Constants.API);
                                            if (response.body().status.equals("ok")){
                                                System.out.println("fragment: " + response.body().status);
                                                newsList = (ArrayList<Articles>) response.body().articles;
                                                System.out.println("list size: "+ newsList.size());
                                                saveData(newsList);
                                                System.out.println("key: "+Constants.API+" api no: 6");

                                            }else {
                                                Constants.API = Constants.API7;
                                                loadData(Constants.page,Constants.API);
                                                if (response.body().status.equals("ok")){
                                                    System.out.println("fragment: " + response.body().status);
                                                    newsList = (ArrayList<Articles>) response.body().articles;
                                                    System.out.println("list size: "+ newsList.size());
                                                    saveData(newsList);
                                                    System.out.println("key: "+Constants.API+" api no: 7");

                                                }else {
                                                    Constants.API = Constants.API8;
                                                    loadData(Constants.page,Constants.API);
                                                    if (response.body().status.equals("ok")){
                                                        System.out.println("fragment: " + response.body().status);
                                                        newsList = (ArrayList<Articles>) response.body().articles;
                                                        System.out.println("list size: "+ newsList.size());
                                                        saveData(newsList);
                                                        System.out.println("key: "+Constants.API+" api no: 8");

                                                    }else {
                                                        Constants.API = Constants.API9;
                                                        loadData(Constants.page,Constants.API);
                                                        if (response.body().status.equals("ok")){
                                                            System.out.println("fragment: " + response.body().status);
                                                            newsList = (ArrayList<Articles>) response.body().articles;
                                                            System.out.println("list size: "+ newsList.size());
                                                            saveData(newsList);
                                                            System.out.println("key: "+Constants.API+" api no: 9");

                                                        }else {
                                                            Constants.API = Constants.API10;
                                                            loadData(Constants.page,Constants.API);
                                                            if (response.body().status.equals("ok")){
                                                                System.out.println("fragment: " + response.body().status);
                                                                newsList = (ArrayList<Articles>) response.body().articles;
                                                                System.out.println("list size: "+ newsList.size());
                                                                saveData(newsList);
                                                                System.out.println("key: "+Constants.API+" api no: 10");

                                                            }else {
                                                                Constants.API = Constants.API11;
                                                                loadData(Constants.page,Constants.API);
                                                                if (response.body().status.equals("ok")){
                                                                    System.out.println("fragment: " + response.body().status);
                                                                    newsList = (ArrayList<Articles>) response.body().articles;
                                                                    System.out.println("list size: "+ newsList.size());
                                                                    saveData(newsList);
                                                                    System.out.println("key: "+Constants.API+" api no: 11");

                                                                }else {
                                                                    Constants.API = Constants.API12;
                                                                    loadData(Constants.page,Constants.API);
                                                                    if (response.body().status.equals("ok")){
                                                                        System.out.println("fragment: " + response.body().status);
                                                                        newsList = (ArrayList<Articles>) response.body().articles;
                                                                        System.out.println("list size: "+ newsList.size());
                                                                        saveData(newsList);
                                                                        System.out.println("key: "+Constants.API+" api no: 12");

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    }
//                    if (!newsList.isEmpty()){
//                        newsList.clear();
//                    }
                }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    private void loadNewsFromPref() {
//       SharedPreferences sharedPreferences = getContext().getSharedPreferences("news", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("news", null);
        Type type = new TypeToken<ArrayList<Articles>>() {}.getType();
        newsList = gson.fromJson(json, type);

        if (newsList == null) {
            // if the array list is empty
            // creating a new array list.
            newsList = new ArrayList<>();
        }

    }

    private void saveData(ArrayList<Articles> list) {
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("news", MODE_PRIVATE);
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
//        Constants.API = Constants.API1;
        super.onCreate(savedInstanceState);
        sharedPreferences  = getContext().getSharedPreferences("news", MODE_PRIVATE);
        settings = new AppSettings(getContext().getApplicationContext());
//        RetrofitInstance.getInstance();
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        applyLanguage(settings.getLanguage());
        applyCategory(settings.getCategory());
        applyAPI(settings.getApi());


        if (settings.getLanguage()==0){
            Constants.langKey = "en";
        }else if (settings.getLanguage()==1){
            Constants.langKey = "tr";
        }else if (settings.getLanguage()==2){
            Constants.langKey = "ru";
        }else if (settings.getLanguage()==3){
            Constants.langKey = "de";
        }

        if (settings.getCategory()==0){
            Constants.categKey = "general";
        }else if (settings.getCategory()==1){
            Constants.categKey = "science";
        }else if (settings.getCategory()==2){
            Constants.categKey = "technology";
        }else if (settings.getCategory()==3){
            Constants.categKey = "sports";
        }else if (settings.getCategory()==4){
            Constants.categKey = "health";
        }else if (settings.getCategory()==5){
            Constants.categKey = "entertainment";
        }else if (settings.getCategory()==6) {
            Constants.categKey = "business";
        }

        if (settings.getApi()==0){
            Constants.API = Constants.API1;
        }else if (settings.getApi()==1){
            Constants.API = Constants.API2;
        }else if (settings.getApi()==2){
            Constants.API = Constants.API3;
        }else if (settings.getApi()==3){
            Constants.API = Constants.API4;
        }else if (settings.getApi()==4){
            Constants.API = Constants.API5;
        }else if (settings.getApi()==5){
            Constants.API = Constants.API6;
        }else if (settings.getApi()==6) {
            Constants.API = Constants.API7;
        }else if (settings.getApi()==7) {
            Constants.API = Constants.API8;
        }else if (settings.getApi()==8) {
            Constants.API = Constants.API9;
        }else if (settings.getApi()==9) {
            Constants.API = Constants.API10;
        }else if (settings.getApi()==10) {
            Constants.API = Constants.API11;
        }else if (settings.getApi()==11) {
            Constants.API = Constants.API12;
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

    private void applyAPI(int api) {
        switch (api) {
            case AppSettings.API_1: settings.setApi(0);break;
            case AppSettings.API_2: settings.setApi(1);break;
            case AppSettings.API_3: settings.setApi(2);break;
            case AppSettings.API_4: settings.setApi(3);break;
            case AppSettings.API_5: settings.setApi(4);break;
            case AppSettings.API_6: settings.setApi(5);break;
            case AppSettings.API_7: settings.setApi(6);break;
            case AppSettings.API_8: settings.setApi(7);break;
            case AppSettings.API_9: settings.setApi(8);break;
            case AppSettings.API_10: settings.setApi(9);break;
            case AppSettings.API_11: settings.setApi(10);break;
            case AppSettings.API_12: settings.setApi(11);break;
            default: settings.setApi(0);

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