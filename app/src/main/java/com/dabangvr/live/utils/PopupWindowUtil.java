package com.dabangvr.live.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dabangvr.live.R;
import com.dabangvr.live.base.BaseRecyclerHolder;
import com.dabangvr.live.base.RecyclerAdapter;
import com.dbvr.baselibrary.base.Contents;

import java.util.List;

public abstract class PopupWindowUtil {
    private  Activity mActivity;
    private PopupWindow popupWindow;

    public PopupWindowUtil(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void showWindow(View view, int resources , int position, int width) {
        View layout = LayoutInflater.from(mActivity).inflate(
                resources, null);
        popupWindow = new PopupWindow(view);
        // 设置弹框的宽度为布局文件的宽
        popupWindow.setWidth(width);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置一个透明的背景，不然无法实现点击弹框外，弹框消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置点击弹框外部，弹框消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setContentView(layout);
        popupWindow.setAnimationStyle(R.anim.dialog_in);
        // 设置弹框出现的位置，在v的正下方横轴偏移textview的宽度，为了对齐~纵轴不偏移
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            popupWindow.showAsDropDown(view, 0, 0,position);
        }
        convert(layout);
    }

    public abstract void convert(View view);

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
    }
}
