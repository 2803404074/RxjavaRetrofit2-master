package com.dbvr.baselibrary.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人中心菜单
 */
public class ServerMo {
    private String id;
    private String name;
    private int resources;

    public ServerMo(String id, String name, int resources) {
        this.id = id;
        this.name = name;
        this.resources = resources;
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

    public int getResources() {
        return resources;
    }

    public void setResources(int resources) {
        this.resources = resources;
    }
}
