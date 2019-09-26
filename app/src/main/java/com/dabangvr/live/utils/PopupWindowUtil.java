package com.dabangvr.live.utils;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dabangvr.live.R;

import java.util.List;

public class PopupWindowUtil {
    private static PopupWindowUtil popupWindowUtil;
    private static Activity mActivity;

    private PopupWindow popupWindow;

    public static PopupWindowUtil getInstance(Activity activity) {
        mActivity = activity;
        if (popupWindowUtil == null) {
            popupWindowUtil = new PopupWindowUtil();
        }
        return popupWindowUtil;
    }

    public void showWindow(View view) {

        View layout = LayoutInflater.from(mActivity).inflate(
                R.layout.popup_view, null);
        popupWindow = new PopupWindow(view);
        // 设置弹框的宽度为布局文件的宽
        popupWindow.setWidth(300);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置一个透明的背景，不然无法实现点击弹框外，弹框消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置点击弹框外部，弹框消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setContentView(layout);
        // 设置弹框出现的位置，在v的正下方横轴偏移textview的宽度，为了对齐~纵轴不偏移
        popupWindow.showAsDropDown(view, 0, 0);

        layout.findViewById(R.id.tvMy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTuchCalBack.onclickItem(view.getId());
            }
        });

        layout.findViewById(R.id.tvYy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTuchCalBack.onclickItem(view.getId());
            }
        });

        layout.findViewById(R.id.tvKg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTuchCalBack.onclickItem(view.getId());
            }
        });

        layout.findViewById(R.id.tvLp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTuchCalBack.onclickItem(view.getId());
            }
        });

    }

    private OnTuchCalBack onTuchCalBack;

    public interface OnTuchCalBack{
        void onclickItem(int vId);
    }

    public void setOnTuchCalBack(OnTuchCalBack onTuchCalBack) {
        this.onTuchCalBack = onTuchCalBack;
    }

    public void destroy(){
        if (popupWindow!=null){
            popupWindow.dismiss();
            popupWindow = null;
        }

        if (popupWindowUtil!=null){
            popupWindowUtil = null;
        }
    }
}
