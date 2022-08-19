package com.farasatnovruzov.newsappjava.ui;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.farasatnovruzov.newsappjava.R;
import com.farasatnovruzov.newsappjava.util.AppSettings;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    NavHostFragment navHostFragment;
    BottomNavigationView bottomNavigationView;

    AppSettings settings;


//    Retrofit retrofit;
//    ArrayList<NewsResponse> newsList;
//    private final String BASE_URL = "https://newsapi.org/";
//    private final String API_KEY = "ea96fc26b3d14df8938fbb351e1200a3";


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        settings = new AppSettings(this);
        applyTheme(settings.getTheme());

        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        if (getSupportActionBar() != null){
//            getSupportActionBar().hide();
//        }
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.green));
        }

        setUpNavigation();

//        Gson gson = new GsonBuilder().setLenient().create();
//        retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();

    }



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
}