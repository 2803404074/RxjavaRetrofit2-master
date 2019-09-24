package com.dbvr.retrofitlibrary.utils;

import android.content.Context;

import com.dbvr.baselibrary.model.QiniuUploadFile;
import com.dbvr.baselibrary.model.StreamMo;
import com.dbvr.baselibrary.model.TagMo;
import com.dbvr.baselibrary.model.UserMess;
import com.dbvr.retrofitlibrary.bean.Demo;
import com.dbvr.retrofitlibrary.model.GoodsCategoryList;
import com.dbvr.retrofitlibrary.observer.MyObserver;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.reactivex.Observer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 提交参数方式
 */
public class RequestUtils {

    //登陆
    public static void loginByPhone(Context context, String phone,String pass,MyObserver<UserMess> observer){
        RetrofitUtils.getApiUrl(context)
                .loginByPhone(phone,pass)
                .compose(RxHelper.observableIO2Main(context))
                .subscribe(observer);
    }

    //直播标签
    public static void getLiveTag(Context context,MyObserver<List<TagMo>> observer){
        RetrofitUtils.getApiUrl(context)
                .getLiveTag("4")
                .compose(RxHelper.observableIO2Main(context))
                .subscribe(observer);
    }

    //开播-创建流
    public static void createSteam(Context context,Map<String,Object> map,MyObserver<StreamMo> observer){
        RetrofitUtils.getApiUrl(context)
                .createSteam(map)
                .compose(RxHelper.observableIO2Main(context))
                .subscribe(observer);

    }

    //获取七牛云基本信息
    public static void getQinNiu(Context context,MyObserver<QiniuUploadFile> observer) {
        RetrofitUtils.getApiUrl(context)
                .getQiNiu()
                .compose(RxHelper.observableIO2Main(context))
                .subscribe(observer);
    }











        /**
         * Get 请求demo
         * @param context
         * @param observer
         *
         *
         */
    public static void getDemo(Context context, MyObserver<Demo> observer){
        RetrofitUtils.getApiUrl(context)
                .getDemo()
                .compose(RxHelper.observableIO2Main(context))
                .subscribe(observer);
    }

    /**
     * Get 请求demo
     * @param context
     * @param observer
     */
    public static void getDemoList(Context context, MyObserver<List<Demo>> observer){
        RetrofitUtils.getApiUrl(context)
                .getDemoList().compose(RxHelper.observableIO2Main(context))
                .subscribe(observer);
    }
    public static void getCategoryList(Context context, MyObserver<GoodsCategoryList> observer){
        RetrofitUtils.getApiUrl(context)
                .getCategoryList("1").compose(RxHelper.observableIO2Main(context))
                .subscribe(observer);
    }


    /**
     * Post 请求demo
     * @param context
     * @param consumer
     */
    public static void postDemo(Context context, String name, String password, Observer<Demo> consumer){
        RetrofitUtils.getApiUrl(context)
                .postUser(name,password).compose(RxHelper.observableIO2Main(context))
                .subscribe(consumer);
    }
    /**
     * Put 请求demo
     * @param context
     * @param consumer
     */
    public static void putDemo(Context context, String access_token,Observer<Demo> consumer){
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization",access_token);
        RetrofitUtils.getApiUrl(context)
                .put(headers,"厦门").compose(RxHelper.observableIO2Main(context))
                .subscribe(consumer);
    }
    /**
     * Delete 请求demo
     * @param context
     * @param consumer
     */
    public static void deleteDemo(Context context, String access_token,Observer<Demo> consumer){
        RetrofitUtils.getApiUrl(context)
                .delete(access_token,1).compose(RxHelper.observableIO2Main(context))
                .subscribe(consumer);
    }

    /**
     * 上传图片
     * @param context
     * @param observer
     */
    public static void upImagView(Context context, String  access_token,String str, Observer<Demo>  observer){
        File file = new File(str);
//        File file = new File(imgPath);
        Map<String,String> header = new HashMap<String, String>();
        header.put("Accept","application/json");
        header.put("Authorization",access_token);
//        File file =new File(filePath);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
//        RequestBody requestFile =
//                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), reqFile);
        RetrofitUtils.getApiUrl(context).uploadImage(header,body).compose(RxHelper.observableIO2Main(context))
                .subscribe(observer);
    }

    /**
     * 上传多张图片
     * @param files
     */
    public static void upLoadImg(Context context,String access_token,List<File> files, Observer<Demo>  observer1){
        Map<String,String> header = new HashMap<String, String>();
        header.put("Accept","application/json");
        header.put("Authorization",access_token);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);//表单类型
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
            builder.addFormDataPart("file", file.getName(), photoRequestBody);
        }
        List<MultipartBody.Part> parts = builder.build().parts();
        RetrofitUtils.getApiUrl(context).uploadImage1(header,parts).compose(RxHelper.observableIO2Main(context))
                .subscribe(observer1);
    }
}

