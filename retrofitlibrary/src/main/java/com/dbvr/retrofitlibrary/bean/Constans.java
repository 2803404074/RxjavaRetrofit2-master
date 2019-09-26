package com.dbvr.retrofitlibrary.bean;

public class Constans {

    //设置默认超时时间
    public static final int DEFAULT_TIME = 20;

    public static final String TOKEN = "DABANG-TOKEN";


    //public final static  String BaseUrl = "http://www.vrzbgw.com/dabang/";
    public final static  String BaseUrl = "http://192.168.0.112:8080/";

    public final static  String retrofit = "values/5";
    public final static  String retrofitList = "values";

    public final static String test = "api/index/getIndexCategoryList";

    //手机登陆
    public final static String LOGIN = "api/auth/loginByPhone";

    //直播标签
    public final static String GETLIVETAG = "api/index/getGoodsCategoryList";

    //创建直播
    public final static String CREATE_LIVE = "/api/pili/create";

    //创建直播
    public final static String GET_QINIU = "/api/config/getUploadConfigToken";

    //直播间礼物列表
    public final static String GiftList = "api/livegift/getLiveGiftList";

    //环信创建房间
    public final static String HY_CREATE_ROOM = "api/easemob/createChatRoom";

}
