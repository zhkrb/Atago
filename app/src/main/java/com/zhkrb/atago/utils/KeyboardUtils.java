package com.zhkrb.atago.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import com.zhkrb.atago.acticity.AbsActivity;

public class KeyboardUtils {

    public static void hideKeyboard(Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (((AbsActivity)context).getCurrentFocus() != null) {
            if (imm != null) {
                imm.hideSoftInputFromWindow(((AbsActivity)context).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

}
