package com.dbvr.retrofitlibrary.utils;

import android.content.Context;
import android.util.Log;

import com.dbvr.baselibrary.utils.SPUtils;
import com.dbvr.retrofitlibrary.bean.Constans;

import java.io.IOException;
import java.util.Locale;
import okhttp3.Interceptor;
import okhttp3.Request;

/**
 *  TODO Log拦截器代码
 */
public class LogInterceptor implements Interceptor{
    private String TAG = "okhttp";
    private Context mContext;

    public LogInterceptor(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        String token = (String) SPUtils.instance(mContext).getkey("token","");
        Request.Builder requestBuilde = request.newBuilder()
        .header(Constans.TOKEN, token);

        Log.e(TAG,"request:" + token);

        Request requestx = requestBuilde.build();
        return chain.proceed(requestx);
//        return response.newBuilder()
//                .body(okhttp3.ResponseBody.create(mediaType, content))
//                .build();
    }
}
