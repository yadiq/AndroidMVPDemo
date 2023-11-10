package com.hqumath.demo.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import androidx.annotation.RequiresApi;

import com.hqumath.demo.app.Constant;

import java.util.Locale;

/**
 * 多语言切换的帮助类
 */
public class MultiLanguageUtil {

    public static final int LANGUAGE_EN = 0;    //英文
    public static final int LANGUAGE_CHINESE_SIMPLIFIED = 1; //简体中文
    public static final int LANGUAGE_CHINESE_TRADITIONAL = 2;  //繁体中文

    private static MultiLanguageUtil instance;

    public static MultiLanguageUtil getInstance() {
        if (instance == null) {
            synchronized (MultiLanguageUtil.class) {
                if (instance == null) {
                    instance = new MultiLanguageUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 获取本地存储的语言
     *
     * @return
     */
    public static Locale getLanguageLocale(Context context) {
        int languageType = SPUtil.getInstance(context).getInt(Constant.LANGUAGE, LANGUAGE_EN);
        Locale locale = Locale.ENGLISH;
        if (languageType == LANGUAGE_EN) {
            locale = Locale.ENGLISH;
        } else if (languageType == LANGUAGE_CHINESE_SIMPLIFIED) {
            locale = Locale.SIMPLIFIED_CHINESE;
        } else if (languageType == LANGUAGE_CHINESE_TRADITIONAL) {
            locale = Locale.TRADITIONAL_CHINESE;
        }
        return locale;
    }

    /**
     * 设置语言
     *
     * @param context
     * @param languageType
     */
    public static void updateLanguage(Context context, int languageType) {
        SPUtil.getInstance(context).put(Constant.LANGUAGE, languageType);
        //更新Application Resources。只有ApplicationContext updateConfiguration才生效
        Locale locale = getLanguageLocale(context);
        updateConfiguration(context.getApplicationContext(), locale);
    }

    /**
     * 多语言切换，Application/Service/Activity Resources
     *
     * @param context
     * @return
     */
    public static Context attachBaseContext(Context context) {
        Locale locale = getLanguageLocale(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            return createConfigurationContext(context, locale);
        } else {
            return updateConfiguration(context, locale);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    private static Context createConfigurationContext(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        LocaleList localeList = new LocaleList(locale);
        configuration.setLocales(localeList);
        return context.createConfigurationContext(configuration);
    }

    private static Context updateConfiguration(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocales(new LocaleList(locale));
        } else {
            configuration.setLocale(locale);//This field was deprecated in API level 24
        }
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(configuration, dm);//This method was deprecated in API level 25
        return context;
    }
}
