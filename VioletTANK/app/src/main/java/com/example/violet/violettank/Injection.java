package com.example.violet.violettank;

import android.content.Context;

import com.example.violet.violettank.data.MyApplication;

/**
 * Created by violet.
 *
 * @ author VioLet.
 * @ email 286716191@qq.com
 * @ date 2017/11/1.
 */

public class Injection {
    public static Context provideContext() {
        return MyApplication.getInstance();
    }
}
