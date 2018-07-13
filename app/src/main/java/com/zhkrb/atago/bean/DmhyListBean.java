package com.zhkrb.atago.bean;

public class DmhyListBean {

    private String magnet;
    private String title;
    private String time;
    private String team_id;
    private String size;
    private String sort;


    public DmhyListBean(String magnet, String title, String time,String sort, String team_id, String size) {
        this.magnet = magnet;
        this.title = title;
        this.time = time;
        this.team_id = team_id;
        this.size = size;
        this.sort = sort;
    }

    public String getMagnet() {
        return magnet;
    }

    public void setMagnet(String magnet) {
        this.magnet = magnet;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
