package com.farasatnovruzov.newsappjava.util;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSettings {

    private SharedPreferences prefs;
    private SharedPreferences prefsLanguage;
    private SharedPreferences prefsCategory;
    private SharedPreferences prefsAPI;

    private int theme;
    private int language;
    private int category;
    private int api;



    public static final String  THEME_KEY = "theme";
    public static final int THEME_LIGHT = 0;
    public static final int THEME_DARK = 1;
    public static final int THEME_DARK_AMOLED = 2;


    public static final String  LANG_KEY = "language";
    public static final int LANG_EN = 0;
    public static final int LANG_TR = 1;
    public static final int LANG_RU = 2;
    public static final int LANG_DE = 3;


    public static final String  CAT_KEY = "category";
    public static final int CAT_GEN = 0;
    public static final int CAT_SCI = 1;
    public static final int CAT_TEC = 2;
    public static final int CAT_SPO = 3;
    public static final int CAT_HEA = 4;
    public static final int CAT_ENT = 5;
    public static final int CAT_BUS = 6;

    public static final String  API_KEY = "ea96fc26b3d14df8938fbb351e1200a3";
    public static final int API_1 = 0;
    public static final int API_2 = 1;
    public static final int API_3 = 2;
    public static final int API_4 = 3;
    public static final int API_5 = 4;
    public static final int API_6 = 5;
    public static final int API_7 = 6;
    public static final int API_8 = 7;
    public static final int API_9 = 8;
    public static final int API_10 = 9;
    public static final int API_11 = 10;
    public static final int API_12 = 11;


    public AppSettings(Context context) {
        prefs = context.getSharedPreferences("prefAppTheme",Context.MODE_PRIVATE);
        prefsLanguage = context.getSharedPreferences("prefAppLanguage",Context.MODE_PRIVATE);
        prefsCategory = context.getSharedPreferences("prefAppCategory",Context.MODE_PRIVATE);
        prefsAPI = context.getSharedPreferences("prefAppAPI",Context.MODE_PRIVATE);

        theme = prefs.getInt(THEME_KEY,0); //default theme is 0 = Light Theme index
        language = prefsLanguage.getInt(LANG_KEY,0);//default language is 0 = English language index
        category = prefsCategory.getInt(CAT_KEY,0);//default category is 0 = General category index
        api = prefsAPI.getInt(API_KEY,0);//default api is 0 = General category index


    }

    public int getTheme() {
        return theme;
    }
    public int getLanguage() {
        return language;
    }
    public int getCategory() {
        return category;
    }
    public int getApi(){
        return api;
    }

    public void setTheme(int theme) {
        this.theme = theme;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(THEME_KEY,theme);
        editor.apply();
    }



    public void setLanguage(int language) {
        this.language = language;
        SharedPreferences.Editor editor = prefsLanguage.edit();
        editor.putInt(LANG_KEY,language);
        editor.apply();
    }



    public void setCategory(int category) {
        this.category = category;
        SharedPreferences.Editor editor = prefsCategory.edit();
        editor.putInt(CAT_KEY,category);
        editor.apply();
    }

    public void setApi(int api) {
        this.api = api;
        SharedPreferences.Editor editor = prefsAPI.edit();
        editor.putInt(API_KEY,api);
        editor.apply();
    }
}
