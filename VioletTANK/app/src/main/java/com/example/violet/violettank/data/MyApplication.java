package com.example.violet.violettank.data;

import android.app.Application;

import com.example.violet.violettank.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by violet.
 *
 * @ author VioLet.
 * @ email 286716191@qq.com
 * @ date 2017/10/31.
 */

public class MyApplication extends Application {

    private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        // Custom fonts
        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Roboto-Monospace-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }


    public static MyApplication getInstance() {
        return sInstance;
    }
}
