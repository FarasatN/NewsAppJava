package com.farasatnovruzov.newsappjava.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.farasatnovruzov.newsappjava.R;
import com.farasatnovruzov.newsappjava.util.AppSettings;

public class SettingsFragment extends Fragment {

    private RadioGroup rgTheme;
    private RadioButton rbLight, rbDark, rbAmoled;
    private AppSettings settings;

    private RadioGroup rgLang;
    private RadioButton rbEn, rbTr,rbRu,rbDe;

    private RadioGroup rgCat;
    private RadioButton rbGen, rbSci,rbTec,rbSpo,rbHea,rbEnt,rbBus;

    private RadioGroup rgAPI;
    private RadioButton rbApi1, rbApi2,rbApi3,rbApi4,rbApi5,rbApi6,rbApi7,rbApi8,rbApi9,rbApi10,rbApi11,rbApi12;


    public SettingsFragment() {
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
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settings = new AppSettings(getContext());
        initViewTheme(view);
        initViewLang(view);
        initViewCategory(view);
        initViewAPI(view);
    }

    private void initViewTheme(View view) {
        rgTheme = view.findViewById(R.id.rg_theme);
        rbLight = view.findViewById(R.id.rb_light);
        rbDark = view.findViewById(R.id.rb_dark);
        rbAmoled = view.findViewById(R.id.rb_amoled);

        switch (settings.getTheme()) {
            case AppSettings.THEME_LIGHT: rbLight.setChecked(true);break;
            case AppSettings.THEME_DARK: rbDark.setChecked(true);break;
            case AppSettings.THEME_DARK_AMOLED: rbAmoled.setChecked(true);break;
            default: rbLight.setChecked(true);
        }

        rgTheme.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_light: settings.setTheme(AppSettings.THEME_LIGHT);break;
                    case R.id.rb_dark: settings.setTheme(AppSettings.THEME_DARK);break;
                    case R.id.rb_amoled: settings.setTheme(AppSettings.THEME_DARK_AMOLED);break;
                    default: settings.setTheme(AppSettings.THEME_LIGHT);

                }
                getActivity().recreate();

            }
        });

    }


    private void initViewLang(View view) {
        rgLang = view.findViewById(R.id.rg_lang);
        rbEn = view.findViewById(R.id.rb_en);
        rbTr = view.findViewById(R.id.rb_tr);
        rbRu = view.findViewById(R.id.rb_ru);
        rbDe = view.findViewById(R.id.rb_de);

        switch (settings.getLanguage()) {
            case AppSettings.LANG_EN: rbEn.setChecked(true);break;
            case AppSettings.LANG_TR: rbTr.setChecked(true);break;
            case AppSettings.LANG_RU: rbRu.setChecked(true);break;
            case AppSettings.LANG_DE: rbDe.setChecked(true);break;
            default: rbEn.setChecked(true);
        }

        rgLang.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_en: settings.setLanguage(AppSettings.LANG_EN);break;
                    case R.id.rb_tr: settings.setLanguage(AppSettings.LANG_TR);break;
                    case R.id.rb_ru: settings.setLanguage(AppSettings.LANG_RU);break;
                    case R.id.rb_de: settings.setLanguage(AppSettings.LANG_DE);break;
                    default: settings.setLanguage(AppSettings.LANG_EN);
                }
//                getActivity().recreate();

            }
        });

    }


    private void initViewCategory(View view) {
        rgCat = view.findViewById(R.id.rg_category);
        rbGen = view.findViewById(R.id.rb_general);
        rbSci = view.findViewById(R.id.rb_science);
        rbTec = view.findViewById(R.id.rb_technology);
        rbSpo = view.findViewById(R.id.rb_sports);
        rbHea = view.findViewById(R.id.rb_health);
        rbEnt = view.findViewById(R.id.rb_entertainment);
        rbBus = view.findViewById(R.id.rb_business);

        switch (settings.getCategory()) {
            case AppSettings.CAT_GEN: rbGen.setChecked(true);break;
            case AppSettings.CAT_SCI: rbSci.setChecked(true);break;
            case AppSettings.CAT_TEC: rbTec.setChecked(true);break;
            case AppSettings.CAT_SPO: rbSpo.setChecked(true);break;
            case AppSettings.CAT_HEA: rbHea.setChecked(true);break;
            case AppSettings.CAT_ENT: rbEnt.setChecked(true);break;
            case AppSettings.CAT_BUS: rbBus.setChecked(true);break;
            default: rbGen.setChecked(true);
        }

        rgCat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_general: settings.setCategory(AppSettings.CAT_GEN);break;
                    case R.id.rb_science: settings.setCategory(AppSettings.CAT_SCI);break;
                    case R.id.rb_technology: settings.setCategory(AppSettings.CAT_TEC);break;
                    case R.id.rb_sports: settings.setCategory(AppSettings.CAT_SPO);break;
                    case R.id.rb_health: settings.setCategory(AppSettings.CAT_HEA);break;
                    case R.id.rb_entertainment: settings.setCategory(AppSettings.CAT_ENT);break;
                    case R.id.rb_business: settings.setCategory(AppSettings.CAT_BUS);break;
                    default: settings.setCategory(AppSettings.CAT_GEN);

                }
//                getActivity().recreate();

            }
        });

    }

    private void initViewAPI(View view) {
        rgAPI = view.findViewById(R.id.rg_api);
        rbApi1 = view.findViewById(R.id.rb_api1);
        rbApi2 = view.findViewById(R.id.rb_api2);
        rbApi3 = view.findViewById(R.id.rb_api3);
        rbApi4 = view.findViewById(R.id.rb_api4);
        rbApi5 = view.findViewById(R.id.rb_api5);
        rbApi6 = view.findViewById(R.id.rb_api6);
        rbApi7 = view.findViewById(R.id.rb_api7);
        rbApi8 = view.findViewById(R.id.rb_api8);
        rbApi9 = view.findViewById(R.id.rb_api9);
        rbApi10 = view.findViewById(R.id.rb_api10);
        rbApi11 = view.findViewById(R.id.rb_api11);
        rbApi12 = view.findViewById(R.id.rb_api12);

        switch (settings.getApi()) {
            case AppSettings.API_1: rbApi1.setChecked(true);break;
            case AppSettings.API_2: rbApi2.setChecked(true);break;
            case AppSettings.API_3: rbApi3.setChecked(true);break;
            case AppSettings.API_4: rbApi4.setChecked(true);break;
            case AppSettings.API_5: rbApi5.setChecked(true);break;
            case AppSettings.API_6: rbApi6.setChecked(true);break;
            case AppSettings.API_7: rbApi7.setChecked(true);break;
            case AppSettings.API_8: rbApi8.setChecked(true);break;
            case AppSettings.API_9: rbApi9.setChecked(true);break;
            case AppSettings.API_10: rbApi10.setChecked(true);break;
            case AppSettings.API_11: rbApi11.setChecked(true);break;
            case AppSettings.API_12: rbApi12.setChecked(true);break;
            default: rbApi1.setChecked(true);
        }

        rgAPI.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_api1: settings.setApi(AppSettings.API_1);break;
                    case R.id.rb_api2: settings.setApi(AppSettings.API_2);break;
                    case R.id.rb_api3: settings.setApi(AppSettings.API_3);break;
                    case R.id.rb_api4: settings.setApi(AppSettings.API_4);break;
                    case R.id.rb_api5: settings.setApi(AppSettings.API_5);break;
                    case R.id.rb_api6: settings.setApi(AppSettings.API_6);break;
                    case R.id.rb_api7: settings.setApi(AppSettings.API_7);break;
                    case R.id.rb_api8: settings.setApi(AppSettings.API_8);break;
                    case R.id.rb_api9: settings.setApi(AppSettings.API_9);break;
                    case R.id.rb_api10: settings.setApi(AppSettings.API_10);break;
                    case R.id.rb_api11: settings.setApi(AppSettings.API_11);break;
                    case R.id.rb_api12: settings.setApi(AppSettings.API_12);break;
                    default: settings.setApi(AppSettings.API_1);

                }
//                getActivity().recreate();

            }
        });

    }
}