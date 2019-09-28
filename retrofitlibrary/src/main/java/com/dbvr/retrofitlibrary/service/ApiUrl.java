package com.dbvr.retrofitlibrary.service;
import com.dbvr.baselibrary.model.GiftMo;
import com.dbvr.baselibrary.model.MusicMo;
import com.dbvr.baselibrary.model.Notice;
import com.dbvr.baselibrary.model.QiniuUploadFile;
import com.dbvr.baselibrary.model.StreamMo;
import com.dbvr.baselibrary.model.TagMo;
import com.dbvr.baselibrary.model.TaskMo;
import com.dbvr.baselibrary.model.UserMess;
import com.dbvr.retrofitlibrary.bean.Beanx;
import com.dbvr.retrofitlibrary.bean.Constans;
import com.dbvr.retrofitlibrary.bean.Demo;
import com.dbvr.retrofitlibrary.model.GoodsCategoryList;
import com.dbvr.retrofitlibrary.response.BaseResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiUrl {

    //------------------------------------------海跳跳接口---------------------------------------
    //登陆-手机号
    @POST(Constans.LOGIN)
    @FormUrlEncoded
    Observable<BaseResponse<UserMess>> loginByPhone (@Field("phone") String phone, @Field("password") String pass);

    //登陆-验证验证码
    @POST(Constans.LOGIN)
    @FormUrlEncoded
    Observable<BaseResponse<UserMess>> checkCode (@Field("code") String pass);

    //获取验证码验证码
    @POST(Constans.LOGIN)
    @FormUrlEncoded
    Observable<String> getCode (@Field("phone") String phone);

    //直播标签
    @POST(Constans.GETLIVETAG)
    @FormUrlEncoded
    Observable<BaseResponse<List<TagMo>>> getLiveTag (@Field("parentId") String parentId);

    //开播-创建推流
    @POST(Constans.CREATE_LIVE)
    @FormUrlEncoded
    Observable<BaseResponse<StreamMo>> createSteam(@FieldMap Map<String, Object> map);


    //获取七牛云基本信息
    @POST(Constans.GET_QINIU)
    Observable<BaseResponse<QiniuUploadFile>> getQiNiu();


    //获取直播间礼物列表
    @POST(Constans.GiftList)
    Observable<BaseResponse<List<GiftMo>>>getGiftList();

    //创建环信房间
    @POST(Constans.HY_CREATE_ROOM)
    Observable<BaseResponse<String>> createHyRoom();

    //任务列表
    @POST(Constans.MyTask)
    @FormUrlEncoded
    Observable<BaseResponse<List<TaskMo>>> getTaskList(@Field("timeStamp") String timeStamp);

    //预告列表
    @POST(Constans.MyNoticeList)
    Observable<BaseResponse<List<Notice>>> getNoticeList();

    //新增预告
    @POST(Constans.AddNotice)
    Observable<BaseResponse<Object>> addNotice();

    //音乐列表
    @POST(Constans.MusicList)
    Observable<BaseResponse<List<MusicMo>>> getMusicList();

















    /**
     * 有效链接
     */
    @GET(Constans.retrofit)
    Call<Beanx> getRetrofit();

    @GET(Constans.retrofit)
    Observable<BaseResponse<Demo>> getDemo();

    @GET(Constans.retrofitList)
    Observable<BaseResponse<List<Demo>>> getDemoList();


    /**
     * TODO Get请求
     */
    //第一种方式：GET不带参数
    @GET("retrofit.txt")
    Observable<BaseResponse<Demo>> getUser();
    @GET
    Observable<Demo> getUser(@Url String url);
    @GET
    Observable<Demo> getUser1(@Url String url); //简洁方式   直接获取所需数据
    //第二种方式：GET带参数
    @GET("api/data/{type}/{count}/{page}")
    Observable<Demo> getUser(@Path("type") String type, @Path("count") int count, @Path("page") int page);
    //第三种方式：GET带请求参数：https://api.github.com/users/whatever?client_id=xxxx&client_secret=yyyy
    @GET("users/whatever")
    Observable<Demo> getUser(@Query("client_id") String id, @Query("client_secret") String secret);
    @GET("users/whatever")
    Observable<Demo> getUser(@QueryMap Map<String, String> info);

    /**
     * TODO POST请求
     */
    //第一种方式：@Body
    @Headers("Accept:application/json")
    @POST("login")
    Observable<Demo> postUser(@Body RequestBody body);
    //第二种方式：@Field

    @Headers("Accept:application/json")
    @POST("auth/login")
    @FormUrlEncoded
    Observable<Demo> postUser(@Field("username") String username, @Field("password") String password);

    /*@Headers("Accept:application/json")*/
    @POST(Constans.test)
    @FormUrlEncoded
    Observable<BaseResponse<GoodsCategoryList>> getCategoryList(@Field("parentId") String parentId);



    //多个参数
    Observable<Demo> postUser(@FieldMap Map<String, String> map);

    /**
     * TODO DELETE
     */
    @DELETE("member_follow_member/{id}")
    Observable<Demo> delete(@Header("Authorization") String auth, @Path("id") int id);

    /**
     * TODO PUT
     */
    @PUT("member")
    Observable<Demo> put(@HeaderMap Map<String, String> headers,
                                   @Query("nickname") String nickname);

    /**
     * TODO 文件上传
     */
    @Multipart
    @POST("upload")
    Observable<ResponseBody> upload(@Part("description") RequestBody description, @Part MultipartBody.Part file);

    //亲测可用
    @Multipart
    @POST("member/avatar")
    Observable<Demo> uploadImage(@HeaderMap Map<String, String> headers, @Part MultipartBody.Part file);

    /**
     * 多文件上传
     */
    @Multipart
    @POST("register")
    Observable<ResponseBody> upload(@PartMap Map<String, RequestBody> params, @Part("description") RequestBody description);
    //Observable<ResponseBody> upload(@Part() List<MultipartBody.Part> parts);

    @Multipart
    @POST("member/avatar")
    Observable<Demo> uploadImage1(@HeaderMap Map<String, String> headers, @Part List<MultipartBody.Part> file);

    /**
     * 来自https://blog.csdn.net/impure/article/details/79658098
     * @Streaming 这个注解必须添加，否则文件全部写入内存，文件过大会造成内存溢出
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Header("RANGE") String start, @Url String url);
}
