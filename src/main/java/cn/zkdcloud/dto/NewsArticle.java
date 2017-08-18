package cn.zkdcloud.dto;

/**
 * Created by zk on 2016/11/5.
 */
public class NewsArticle {

    private String title;
    private String description;
    private String picUrl;
    private String url;

    public NewsArticle(){

    }

    public NewsArticle(String title,String description,String picUrl,String url){
        this.title = title;
        this.description = description;
        this.picUrl = picUrl;
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
