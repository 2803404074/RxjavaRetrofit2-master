package com.dbvr.baselibrary.model;

/**
 * 预告列表
 */
public class Notice {
    private String id;
    private String noticeTitle;//标题
    private String coverUrl;//封面
    private String noticeDescribe;//描述
    private String noticeTime;//预告时间

    public Notice() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getNoticeDescribe() {
        return noticeDescribe;
    }

    public void setNoticeDescribe(String noticeDescribe) {
        this.noticeDescribe = noticeDescribe;
    }

    public String getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(String noticeTime) {
        this.noticeTime = noticeTime;
    }
}
