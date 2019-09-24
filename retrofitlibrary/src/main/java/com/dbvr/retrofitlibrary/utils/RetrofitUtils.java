package com.dbvr.retrofitlibrary.utils;


import android.content.Context;

import androidx.annotation.NonNull;
import com.dbvr.retrofitlibrary.bean.Constans;
import com.dbvr.retrofitlibrary.service.ApiUrl;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Retrofit封装
 */
public class RetrofitUtils {
    private static final String TAG = "RetrofitUtils";
    private static ApiUrl mApiUrl;
    private Context mContext;
    /**
     * 单例模式
     */
    public static ApiUrl getApiUrl(Context mContext) {
        if (mApiUrl == null) {
            synchronized (RetrofitUtils.class) {
                if (mApiUrl == null) {
                    mApiUrl = new RetrofitUtils(mContext).getRetrofit();
                }
            }
        }
        return mApiUrl;
    }
    private RetrofitUtils(Context context){
        this.mContext = context;
    }

    public ApiUrl getRetrofit() {
        // 初始化Retrofit
        ApiUrl apiUrl = initRetrofit(initOkHttp()) .create(ApiUrl.class);
        return apiUrl;
    }

    /**
     * 初始化Retrofit
     */
    @NonNull
    private Retrofit initRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                    .client(client)
                    .baseUrl(Constans.BaseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
    }

    /**
     * 初始化okhttp
     */
    @NonNull
    private OkHttpClient initOkHttp() {
        return new OkHttpClient().newBuilder()
                    .readTimeout(Constans.DEFAULT_TIME, TimeUnit.SECONDS)//设置读取超时时间
                    .connectTimeout(Constans.DEFAULT_TIME, TimeUnit.SECONDS)//设置请求超时时间
                    .writeTimeout(Constans.DEFAULT_TIME,TimeUnit.SECONDS)//设置写入超时时间
                    .addInterceptor(new LogInterceptor(mContext))//添加打印拦截器
                    .retryOnConnectionFailure(true)//设置出现错误进行重新连接。
                    .build();
    }
}

