package com.soubw.bean;

import java.io.Serializable;

/**
 * author：WX_JIN
 * email:wangxiaojin@soubw.com
 */
public class JNoticeBean implements Serializable {

    private String name;
    private String text;
    private String time;
    private String url;
    private int count;

    public JNoticeBean(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
