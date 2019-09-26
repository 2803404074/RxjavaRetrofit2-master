package com.dbvr.baselibrary.base;

public class Contents {
    public static String USER = "user";

    //激光推送的sequence 用来标识一次操作的唯一性(退出登录时根据此参数删除别名)
    public static final int SEQUENCE = 200;

    //环信消息类型
    //内部消息
    public final static String appLogo = "http://image.vrzbgw.com/upload/20190522/174149610e4144.png";
    public final static int HY_SERVER = 100;//连接状态的消息
    public final static int HY_JOIN = 200;//用户进房的消息

    //外部消息
    public final static int HY_COMMENT = 0;//评论
    public final static int HY_DM = 1;//弹幕
    public final static int HY_DS = 2;//打赏
    public final static int HY_ORDER = 3;//下单
}
