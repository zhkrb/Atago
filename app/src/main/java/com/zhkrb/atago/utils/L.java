package com.zhkrb.atago.utils;

import android.util.Log;

public class L {

    private final static String TAG = "log--->";

    public static void e(String s) {
        e(TAG, s);
    }

    public static void e(String tag, String s) {
        Log.e(tag, s);
    }
}
