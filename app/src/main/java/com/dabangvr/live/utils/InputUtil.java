package com.dabangvr.live.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class InputUtil {
    /**
     * 隐藏输入软键盘
     * @param context
     * @param view
     */
    public static void hideInputManager(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view !=null && imm != null){
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);  //强制隐藏
        }
    }

}
