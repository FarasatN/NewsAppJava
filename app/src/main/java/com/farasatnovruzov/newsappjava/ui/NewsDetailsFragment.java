package com.farasatnovruzov.newsappjava.ui;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.CookieManager;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.farasatnovruzov.newsappjava.R;
import com.farasatnovruzov.newsappjava.model.Articles;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.parceler.Parcels;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class NewsDetailsFragment extends Fragment implements ViewTreeObserver.OnScrollChangedListener {

    private FloatingActionButton favButton;
    private ProgressBar detailsProgressBar;
    private SwipeRefreshLayout detailsSwipeRefreshLayout;
    private WebView webView;
    private ArrayList<Articles> favList;
    private ArrayList<Articles> articleList;
//    ArrayList<Articles> myList;
    private Articles article,articleFromFav;
//    HashMap<Integer, Articles> favMap;
    private String urlFromHome;
    private boolean clickedFav = false;
    private int position = 0;
    private Handler handler = new Handler();
    private int counter = 0;

//    Articles articleFromAdapter;


    public NewsDetailsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onScrollChanged() {

        if (webView.getScrollY() == 0) {
            detailsSwipeRefreshLayout.setEnabled(true);
        } else {
            detailsSwipeRefreshLayout.setEnabled(false);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favList = new ArrayList<>();
        try {
            favList = loadFavListFromPref();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<favList.size();i++){
                        for(int j=i+1;j<favList.size();j++){
                            if (favList.get(i).url!=null && favList.get(j).url!=null){
                                if(favList.get(i).url.equals(favList.get(j).url)){
                                    favList.remove(j);
                                    j--;
//                                    saveList(favList);
                                }
//                                saveList(favList);
                            }
//                            saveList(favList);
                        }
//                        saveList(favList);
                    }
                    saveList(favList);
                }
            },1000);

        }catch (Exception e){
            e.printStackTrace();
        }
//        Set<Articles> set = new LinkedHashSet<Articles>();
//        set.addAll(favList);
//        favList.clear();
//        favList.addAll(set);
//        for (int i=0;i<favList.size();i++){
//            if ()
//            favList.get(0).
//        }
//        ListIterator<ArrayList<Articles>> it = favList.listIterator();
//        while (it.hasNext()) {
//            T t = it.next();
//            T prev = it.previous();
//        }
//        for (Articles articles: favList){
//            if (articles.id==articles.id.)
//        }
//        articleList = loadFavListFromPref();
//        favMap = new HashMap<>();
//        articleList = new ArrayList<>();
//        myList = new ArrayList<>();
//        loadData();
        System.out.println("favlist size oncreate: "+favList.size());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
//            favList = loadFavListFromPref();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<favList.size();i++){
                        for(int j=i+1;j<favList.size();j++){
                            if (favList.get(i).url!=null && favList.get(j).url!=null){
                                if(favList.get(i).url.equals(favList.get(j).url)){
                                    favList.remove(j);
                                    j--;
//                                    saveList(favList);
                                }
//                                saveList(favList);
                            }
//                            saveList(favList);
                        }
//                        saveList(favList);
                    }
                    saveList(favList);
                }
            },1000);

        }catch (Exception e){
            e.printStackTrace();
        }
//        NewsDatabase db = Room.databaseBuilder(getContext().getApplicationContext(),
//                NewsDatabase.class, "news").build();
//        NewsDAO newsDAO = db.newsDAO();
//        List<Articles> news = newsDAO.getAll();
        if(getArguments()!=null){
            position = getArguments().getInt("position");
            urlFromHome = getArguments().getString("url");
            article = Parcels.unwrap(getArguments().getParcelable("article"));
            articleFromFav = Parcels.unwrap(getArguments().getParcelable("article_from_fav"));
        }
//        for(int i=0;i<favList.size();i++){
//            for(int j=i+1;j<favList.size();j++){
//                if(favList.get(i).id==(favList.get(j).id)){
//                    favList.remove(j);
//                    saveList(favList);
//                    j--;
//                }
//            }
//        }
//        favList = new ArrayList<Articles>();
//        favList.add(article);
        detailsProgressBar = view.findViewById(R.id.news_details_loading);
        detailsSwipeRefreshLayout = view.findViewById(R.id.news_details_swipe);
        detailsSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.green));
        favButton = view.findViewById(R.id.fab);
        if (clickedFav == false){
//            for (Articles previousArticles: favList){
//                System.out.println("previous articles: "+previousArticles.hashCode());
////                        if (!favList.contains(article)){
////                            System.out.println("does favlist contains? "+favList.contains(article));
//                favList.add(article);
////                        }
//            }
            favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("fav clicked: ");
                    counter++;
                    favButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                if (counter==1){
                    favList.add(article);
                    System.out.println("favlist size: "+favList.size());
                    saveList(favList);
                    System.out.println("counter: "+counter);
//                    for(int i=0;i<favList.size();i++){
//                        for(int j=i+1;j<favList.size();j++){
//                            if(favList.get(i).id==(favList.get(j).id)){
//                                favList.remove(j);
//                                j--;
//                                saveList(favList);
//                            }
//                        }
//                    }
//                    saveList(favList);

                }
//                if (counter>1){
                    counter = 1;
//                }
                }
            });
            clickedFav = true;
//            counter = 1;
        }



        detailsProgressBar.setVisibility(View.VISIBLE);
        webView = view.findViewById(R.id.webView);
        webView.getViewTreeObserver().addOnScrollChangedListener(this);
//        webView.setJavaScriptEnabled(true);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webSettings.setDomStorageEnabled(true);
//        webView.loadUrl("https://techcrunch.com/2022/08/03/google-asian-owned-label-profiles-maps-search/");
//        loadData();
//        favList = (ArrayList<Articles>) getArguments().getSerializable("article");
//        Articles article = getArguments().getParcelable("article");
        System.out.println("position :"+position);
        webView.clearCache(true);
        webView.clearHistory();
        webView.clearFormData();
        webView.clearSslPreferences();
        WebStorage.getInstance().deleteAllData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            CookieSyncManager.createInstance(getContext().getApplicationContext());
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        }
        getContext().getApplicationContext().deleteDatabase("webView.db");
        getContext().getApplicationContext().deleteDatabase("webViewCache.db");

        if (getArguments().getBoolean("from_saved",false)){
            webView.loadUrl(getArguments().getString("from_fav_url"));

//            counter = 1;
//            clickedFav = true;
//            if (counter==1){
                favButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                favButton.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        System.out.println("fav long from saved clicked: ");
                        favList.remove(articleFromFav);
                        saveList(favList);
                        showAlertDialog(view);
                        favButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
//                        clickedFav = false;
                        return true;
                    }
                });
//            }
//            clickedFav = true;
//            System.out.println("counter in");
        }else if(!(getArguments().getBoolean("from_saved",false))){
            if (urlFromHome != null) {
                webView.loadUrl(urlFromHome);

            }
            favButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    System.out.println("fav long clicked: ");
//                if (counter>=0){
//                    counter--;
//                }

                    showAlertDialog(view);
                    favButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
//                favList.remove(article);
//                saveList(favList);
//                Toast.makeText(view.getContext(), "Article removed from favourite list", Toast.LENGTH_SHORT).show();
//                clickedFav = false;
                    return true;
                }
            });
        }else{
            if (urlFromHome != null) {
                webView.loadUrl(urlFromHome);
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                        webView.loadUrl(getArguments().getString("from_fav_url"));

                    }
                });
                if (webView.getUrl() == null) {
                    Navigation.findNavController(view).navigate(R.id.action_details_to_home);
                    favButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }
            } else {
                Navigation.findNavController(view).navigate(R.id.action_details_to_home);
            }
        }
        detailsProgressBar.setVisibility(View.GONE);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        webView.getSettings().getDisplayZoomControls();
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);

        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    WebView webView = (WebView) view;
                    switch (i) {
                        case KeyEvent.KEYCODE_BACK:
                            if (webView.canGoBack()) {
                                webView.goBack();
                                return true;
                            }
                            break;
                    }
                }
                return false;
            }
        });
        detailsSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        detailsSwipeRefreshLayout.setRefreshing(false);
//                        webView.loadUrl(urlFromHome);
                        webView.reload();
                        detailsSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1200);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_details, container, false);
        return view;
//        FragmentNewsDetailsBinding fragmentNewsDetailsBinding =
//                DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.fragment_news_details,container,false);
//        fragmentNewsDetailsBinding.setNewsItemData(item);
//        return fragmentNewsDetailsBinding.getRoot();
//                inflater.inflate(R.layout.fragment_news_details, container, false);
    }





    private void saveList(ArrayList<Articles> list) {
        try {
            SharedPreferences spList = getContext().getApplicationContext().getSharedPreferences("favs", MODE_PRIVATE);
            SharedPreferences.Editor editor = spList.edit();
            Gson gson = new Gson();
            String json = gson.toJson(favList);
            editor.putString("favs", json);
            editor.apply();
//        Type type = new TypeToken<ArrayList<Articles>>() {}.getType();
//        favList = gson.fromJson(getJson, type);
            System.out.println("favs list saved size : " + favList.size());
        }catch (Exception e){
            e.printStackTrace();
        }

  }
    private ArrayList<Articles> loadFavListFromPref() {
        ArrayList<Articles> getListFromPref = new ArrayList<>();

        try {
            SharedPreferences spList = getContext().getSharedPreferences("favs", MODE_PRIVATE);
            Gson gson = new Gson();
            String jsonList = spList.getString("favs", null);
            Type type = new TypeToken<ArrayList<Articles>>() {
            }.getType();
            getListFromPref = gson.fromJson(jsonList, type);

            if (getListFromPref == null) {
                getListFromPref = new ArrayList<>();
            }
            System.out.println("fav list in rv_fav: " + getListFromPref.size());

        }catch (Exception e){
            e.printStackTrace();
        }

            return getListFromPref;
    }

    private void showAlertDialog(View view){
        try {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
            builder1.setMessage("Are you sure to delete this item?");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
//                        removeFavItem(holder,position);
                            favList.remove(article);
                            saveList(favList);
//                            Toast.makeText(view.getContext(), "Article removed from favourite list", Toast.LENGTH_SHORT).show();
                            Toast toast = Toast.makeText(view.getContext(), Html.fromHtml("<font color='#FFC55C' ><b>" + "Article removed from favourite list" + "</b></font>"), Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.TOP, 0, 0);
                            toast.show();
                            dialog.cancel();
                        }
                    });
            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

/*
    private void saveDataToList(@NonNull ArrayList<Articles> list, Articles article) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("favlist", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        list.add(article);
        System.out.print("list size: "+list.size());
        System.out.println(" / article: "+article);
        String json = gson.toJson(list);
        editor.putString("favlist", json);
        editor.apply();
        System.out.println("favlist size: "+list.size());
    }


    private void removeData2(Articles article) {
        SharedPreferences sharedPreferences = getContext().getApplicationContext().getSharedPreferences("fav2", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear().apply();
        editor.remove("fav2").apply();
        editor.clear().apply();
        Gson gson = new Gson();
        String json = gson.toJson(favList);
        editor.remove(json);

        System.out.println("fav2 removed size : " + favList.size());
    }
 */
/*
    private void saveData(HashMap<Integer,Articles> favMap,String position) {
        SharedPreferences sharedPreferences = getContext().getApplicationContext().getSharedPreferences("fav", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear().apply();
        Gson gson = new Gson();
        String json = gson.toJson(favMap);
//        String json2 = gson.toJson(article.url);
        editor.putString(position, json);
        editor.apply();

        System.out.println("fav saved size : " + favMap.size());
    }

    private void removeData(HashMap<Integer,Articles> favMap,String position) {
        SharedPreferences sharedPreferences = getContext().getApplicationContext().getSharedPreferences("fav", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(position).apply();
//        editor.clear().apply();
        Gson gson = new Gson();
        String json = gson.toJson(favMap);
        editor.remove(json).apply();

//        editor.putString("fav", json);
//        editor.apply();

        System.out.println("fav removed size : " + favMap.size());
        System.out.println("position after remove : "+position);
    }

 */



/*
    private void saveData2(Articles article) {
        SharedPreferences sharedPreferences = getContext().getApplicationContext().getSharedPreferences("fav2", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear().apply();
        Gson gson = new Gson();
        String json = gson.toJson(article);
        editor.putString("fav2", json);
        editor.apply();
        Type type = new TypeToken<Articles>() {}.getType();
        article = gson.fromJson(json, type);
        if (article == null) {
            article = new Articles();
        }
        favList.add(article);
        for (int i=0;i<30;i++){
            favList.add(article);
        }
        System.out.println("fav2 saved size : " + favList.size());
    }
 */
}