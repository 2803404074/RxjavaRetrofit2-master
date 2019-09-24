package com.dbvr.retrofitlibrary.observer;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
import com.dbvr.retrofitlibrary.utils.LoadingUtils;
import io.reactivex.disposables.Disposable;

/**
 * Observer加入加载框
 * @param <T>
 */
public abstract class MyObserver<T> extends BaseObserver<T> {
    private Context mContext;
    private LoadingUtils mLoaddingUtils;
    public void setLoaddingView(boolean isLoadding) {
        if (mLoaddingUtils == null) {
            mLoaddingUtils = new LoadingUtils(mContext);
        }
        if (isLoadding) {
            mLoaddingUtils.show();
        } else {
            mLoaddingUtils.dismiss();
        }
    }

    public MyObserver(Context context) {
        this.mContext = context;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (!isConnected(mContext)) {
            Toast.makeText(mContext,"未连接网络",Toast.LENGTH_SHORT).show();
            setLoaddingView(false);
        } else {
            setLoaddingView(true);
        }
    }
    @Override
    public void onError(Throwable e) {
        setLoaddingView(false);
        super.onError(e);
    }

    @Override
    public void onComplete() {
        setLoaddingView(false);
        super.onComplete();
    }
    /**
     * 是否有网络连接，不管是wifi还是数据流量
     * @param context
     * @return
     */
    public static boolean isConnected(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null)
        {
            return false;
        }
        boolean available = info.isAvailable();
        return available;
    }
}

