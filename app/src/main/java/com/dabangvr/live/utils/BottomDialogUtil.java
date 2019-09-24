package com.dabangvr.live.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import com.dbvr.baselibrary.utils.ToastUtil;
import com.rey.material.app.BottomSheetDialog;

public abstract class BottomDialogUtil {

    private Context mContext;
    private BottomSheetDialog dialog;
    private View view;
    private int height;
    private int LayoutId;

    private OnDismissCallBack onDismissCallBack;
    public interface OnDismissCallBack{
        void onDismiss();
    }

    public void setOnDismissCallBack(OnDismissCallBack onDismissCallBack) {
        this.onDismissCallBack = onDismissCallBack;
    }

    public BottomDialogUtil(Context mContext, int layoutId, double h) {
        this.mContext = mContext;
        LayoutId = layoutId;
        init(h);
    }
    private void init(double h) {
        dialog = new BottomSheetDialog(mContext);
        view = LayoutInflater.from(mContext).inflate(LayoutId, null);
        convert(view);
        height = (int) (Double.valueOf(ScreenUtils.getScreenHeight(mContext)) / h);
    }

    public void show(){
        dialog.contentView(view)
                .heightParam(height)
                .inDuration(200)
                .outDuration(200)
                .cancelable(true)
                .show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                onDismissCallBack.onDismiss();
            }
        });
    }

    public abstract void convert(View holder);
}