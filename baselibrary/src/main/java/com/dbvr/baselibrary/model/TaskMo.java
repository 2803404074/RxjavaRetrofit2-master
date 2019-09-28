package com.dbvr.baselibrary.model;

import java.util.List;

public class TaskMo {
    private String id;
    private String taskName;
    private String taskTime;
    private String taskContent;
    private List<GoodsList> goodsList;

    public TaskMo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public List<GoodsList> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsList> goodsList) {
        this.goodsList = goodsList;
    }

    public static class GoodsList{
        private String name;
        private String title;
        private String listUrl;
        private String sellingPrice;
        private String deptName;
        private String deptLogo;

        public GoodsList() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getListUrl() {
            return listUrl;
        }

        public void setListUrl(String listUrl) {
            this.listUrl = listUrl;
        }

        public String getSellingPrice() {
            return sellingPrice;
        }

        public void setSellingPrice(String sellingPrice) {
            this.sellingPrice = sellingPrice;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public String getDeptLogo() {
            return deptLogo;
        }

        public void setDeptLogo(String deptLogo) {
            this.deptLogo = deptLogo;
        }
    }



}
