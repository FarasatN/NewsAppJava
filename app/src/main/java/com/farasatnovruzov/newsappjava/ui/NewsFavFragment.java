package com.farasatnovruzov.newsappjava.ui;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.farasatnovruzov.newsappjava.R;
import com.farasatnovruzov.newsappjava.model.Articles;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class NewsFavFragment extends Fragment {

    private RecyclerView rvFav;
    private FavListAdapter adapter;
    private ArrayList<Articles> favList;

//    private Articles article;
//    private ArrayList<Articles> articleList;
//    private ArrayList<Articles> myList;
//    NewsFavFragment favFragment;

    public NewsFavFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        favFragment = new NewsFavFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_fav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Navigation.findNavController(view).navigate(R.id.action_favourite_to_home);
//        article = new Articles();
//        articleList = new ArrayList<>();
//        myList = new ArrayList<>();
        favList = new ArrayList<>();

//        loadFavFromPref();
        loadFavListFromPref();
        initFavList(view);

//        articleList = Parcels.unwrap(getArguments().getParcelable("articleList"));
//        articleList = getArguments().getParcelableArrayList("articleList");
//        System.out.println("article list: "+articleList);
//        myList = getActivity().getIntent().getParcelableArrayListExtra("myList");
        System.out.println("favlist size onviewcrieated: "+favList.size());

        if (loadFavListFromPref()!=null){
            adapter.submitList(loadFavListFromPref());
            System.out.println("loadFavListFromPref list size: "+loadFavListFromPref().size());
        }
        adapter.notifyDataSetChanged();
//        getActivity().recreate();

    }

    private void initFavList(@NonNull View view) {
        rvFav = view.findViewById(R.id.rv_fav);
        rvFav.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFav.setHasFixedSize(true);
        adapter = new FavListAdapter(new DiffUtilNewsItemCallBack(),getActivity());
        rvFav.setAdapter(adapter);


//        recyclerView.getRecycledViewPool().clear();
//        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
//        recyclerView.setHasFixedSize(false);
//        adapter = new NewsListAdapter(new DiffUtilNewsItemCallBack());
//        recyclerView.setAdapter(adapter);
//        adapter.submitList(newsList);
//        adapter.notifyDataSetChanged();
//        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
//            @Override
//            public void onItemRangeInserted(int positionStart, int itemCount) {
//                super.onItemRangeInserted(positionStart, itemCount);
//                rvFav.smoothScrollToPosition(positionStart);
//                //now everything look great see u in the next part
//            }
//        });
        adapter.notifyDataSetChanged();

    }


//    private void loadFavFromPref() {
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("fav2", MODE_PRIVATE);
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString("fav2", "");
//        Type type = new TypeToken<Articles>() {}.getType();
//        article = gson.fromJson(json, type);
//        if (article == null) {
//            article = new Articles();
//        }
//        favList.add(article);
//        System.out.println("fav articli in rv_fav: "+favList.size());
//    }

    private ArrayList<Articles> loadFavListFromPref() {
        ArrayList<Articles> getListFromPref = new ArrayList<>();
        SharedPreferences spList = getContext().getSharedPreferences("favs", MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonList = spList.getString("favs", null);
        Type type = new TypeToken<ArrayList<Articles>>() {}.getType();
        getListFromPref = gson.fromJson(jsonList, type);

        if (getListFromPref == null) {
            getListFromPref = new ArrayList<>();
        }
        System.out.println("fav list in rv_fav: "+getListFromPref.size());

        return getListFromPref;


    }
}





