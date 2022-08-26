package com.farasatnovruzov.newsappjava.ui;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.view.Gravity;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.farasatnovruzov.newsappjava.R;
import com.farasatnovruzov.newsappjava.model.NewsResponse;
import com.farasatnovruzov.newsappjava.service.NewsAPI;
import com.farasatnovruzov.newsappjava.util.AppSettings;
import com.farasatnovruzov.newsappjava.util.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private boolean doubleBackToExitPressedOnce = false;
    private NavHostFragment navHostFragment;
    private BottomNavigationView bottomNavigationView;
    private AppSettings settings;
    private Retrofit retrofit;

    private void applyTheme(int theme) {
        switch (theme) {
            case AppSettings.THEME_LIGHT: setTheme(R.style.LightTheme);break;
            case AppSettings.THEME_DARK: setTheme(R.style.DarkTheme);break;
            case AppSettings.THEME_DARK_AMOLED: setTheme(R.style.DarkTheme);break;
            default: setTheme(R.style.LightTheme);

        }
    }

    private void setUpNavigation() {
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;

//        Toast toast = Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_LONG);
        /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
        Toast toast = Toast.makeText(this, Html.fromHtml("<font color='#FFC55C' ><b>" + "Please click BACK again to exit" + "</b></font>"), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Constants.API = Constants.API6;
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        settings = new AppSettings(this);
        applyTheme(settings.getTheme());
        super.onCreate(savedInstanceState);
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        if (getSupportActionBar() != null){
//            getSupportActionBar().hide();
//        }
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.green));
        }

        setUpNavigation();

        loadData();
        System.out.println("called load data");
        System.out.println("API: "+Constants.API);
    }


    private void loadData() {

        NewsAPI newsAPI = retrofit.create(NewsAPI.class);
        Call<NewsResponse> call = newsAPI.getBreakingNews(Constants.langKey,Constants.categKey, Constants.page, Constants.API);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                System.out.println("not failed");


                if (response.isSuccessful()) {
//                    System.out.println("response is successful");
                    if (response.body() != null) {
                        if (response.body().status.equals("ok")) {
//                            System.out.println("body status: " + response.body().status);
                        }else{
//                            System.out.println("body status: "+response.body().status);
                        }
//                            Constants.API = Constants.API2;
//                            System.out.println("API2");
////                            loadData();
//                            if (response.body().status.equals("error")) {
//                                Constants.API = Constants.API3;
//                                System.out.println("API3");
////                                loadData();
//                                if (response.body().status.equals("error")) {
//                                    Constants.API = Constants.API4;
//                                    System.out.println("API4");
////                                    loadData();
//                                    if (response.body().status.equals("error")) {
//                                        Constants.API = Constants.API1;
//                                        System.out.println("API1");
////                                        loadData();
//                                    }if (response.body().status.equals("error")) {
//                                        Constants.API = Constants.API5;
//                                        System.out.println("API1");
//                                    }
//                                }
//                            }
//                        }
                        }
                    }else{
//                    System.out.println("response is not succesfull");
                }
                }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                System.out.println("response is failed");
            }
        });
    }
}