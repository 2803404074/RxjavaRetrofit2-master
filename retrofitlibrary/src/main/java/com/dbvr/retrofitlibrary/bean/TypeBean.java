package com.dbvr.retrofitlibrary.bean;

/**
 * 分类图片
 */
public class TypeBean {
    private String id;
    private String name;
    private String jumpUrl;
    private String categoryImg;
    private int showIndex;//0不显示，1不显示

    public TypeBean() {
    }

    @Override
    public String toString() {
        return "TypeBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", jumpUrl='" + jumpUrl + '\'' +
                ", categoryImg='" + categoryImg + '\'' +
                ", showIndex=" + showIndex +
                '}';
    }

    public int getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(int showIndex) {
        this.showIndex = showIndex;
    }

    public TypeBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(String categoryImg) {
        this.categoryImg = categoryImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }
}
