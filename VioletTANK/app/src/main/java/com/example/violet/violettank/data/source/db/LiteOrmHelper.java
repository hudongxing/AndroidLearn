package com.example.violet.violettank.data.source.db;

import com.example.violet.violettank.BuildConfig;
import com.example.violet.violettank.Injection;
import com.litesuits.orm.LiteOrm;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/10/16
 * Time: 4:00 PM
 * Desc: LiteOrmHelper
 */
public class LiteOrmHelper {

    private static final String DB_NAME = "music-player.db";

    private static volatile LiteOrm sInstance;

    private LiteOrmHelper() {
        // Avoid direct instantiate
    }

    public static LiteOrm getInstance() {
        if (sInstance == null) {
            synchronized (LiteOrmHelper.class) {
                if (sInstance == null) {
                    sInstance = LiteOrm.newCascadeInstance(Injection.provideContext(), DB_NAME);
                    sInstance.setDebugged(BuildConfig.DEBUG);
                }
            }
        }
        return sInstance;
    }
}
