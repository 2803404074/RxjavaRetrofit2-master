package com.dbvr.retrofitlibrary.model;

import java.util.List;

public class GoodsCategoryList {

    private List<TypeMo> goodsCategoryList;

    @Override
    public String toString() {
        return "GoodsCategoryList{" +
                "goodsCategoryList=" + goodsCategoryList +
                '}';
    }

    public GoodsCategoryList() {
    }

    public List<TypeMo> getGoodsCategoryList() {
        return goodsCategoryList;
    }

    public void setGoodsCategoryList(List<TypeMo> goodsCategoryList) {
        this.goodsCategoryList = goodsCategoryList;
    }

    public static class TypeMo{
        private String id;
        private String name;
        private String jumpUrl;
        private String categoryImg;
        private int showIndex;//0不显示，1不显示

        public TypeMo() {
        }

        @Override
        public String toString() {
            return "TypeMo{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", jumpUrl='" + jumpUrl + '\'' +
                    ", categoryImg='" + categoryImg + '\'' +
                    ", showIndex=" + showIndex +
                    '}';
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

        public String getCategoryImg() {
            return categoryImg;
        }

        public void setCategoryImg(String categoryImg) {
            this.categoryImg = categoryImg;
        }

        public int getShowIndex() {
            return showIndex;
        }

        public void setShowIndex(int showIndex) {
            this.showIndex = showIndex;
        }
    }
}
