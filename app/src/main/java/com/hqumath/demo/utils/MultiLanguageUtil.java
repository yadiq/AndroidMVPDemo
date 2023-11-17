package com.hqumath.demo.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import com.hqumath.demo.app.Constant;

import java.util.Locale;

/**
 * 多语言切换的帮助类
 * 注意：appcompat版本需1.3.0及以上
 * 修改App和BaseActivity
 */
public class MultiLanguageUtil {

    public static final int LANGUAGE_EN = 0;    //英文
    public static final int LANGUAGE_CHINESE_SIMPLIFIED = 1; //简体中文
    public static final int LANGUAGE_CHINESE_TRADITIONAL = 2;  //繁体中文

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
        //更新系统资源配置
        updateConfiguration(context.getApplicationContext());
    }

    /**
     * 多语言切换
     *
     * @param context
     * @return
     */
    public static Context updateConfiguration(Context context) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = getLanguageLocale(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocales(new LocaleList(locale));
        }
        configuration.setLocale(locale);
        Context newContext = context.createConfigurationContext(configuration);//api>=25
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(configuration, dm);
        return newContext;
    }
}
