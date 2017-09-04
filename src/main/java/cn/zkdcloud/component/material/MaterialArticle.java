package cn.zkdcloud.component.material;

import cn.zkdcloud.core.MaterialComponent;

import java.io.File;

/**
 * 图文素材(上传时)
 *
 * @author zk
 * @version 2017/9/1
 */
public class MaterialArticle {

    /**
     * 标题
     */
    private String title;

    /**
     * 缩略图id
     */
    private String thumb_media_id;

    /**
     * 作者(选填)
     */
    private String author;

    /**
     * 摘要(选填)
     */
    private String digest;

    /**
     * 是否显示封面(1是/0否)
     */
    private Integer show_cover_pic;

    /**
     * 图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，
     * 且此处会去除JS,涉及图片url必须来源"上传图文消息内的图片获取URL"接口获取。外部图片url将被过滤。
     */
    private String content;

    /**
     * 图文消息的原文地址，即点击“阅读原文”后的URL
     */
    private String content_source_url;

    public MaterialArticle(){

    }
    public MaterialArticle(String title,String thumb_media_id,String author,
                           String digest,Integer show_cover_pic,String content,String content_source_url){
        this.title = title;
        this.thumb_media_id = thumb_media_id;
        this.author = author;
        this.digest = digest;
        this.show_cover_pic = show_cover_pic;
        this.content = content;
        this.content_source_url = content_source_url;
    }

    public MaterialArticle(String title, File thumb, String author,
                           String digest, Integer show_cover_pic, String content, String content_source_url){
        this.title = title;
        this.thumb_media_id = MaterialComponent.getInstance().uploadPersistentMaterial(thumb,MaterialType.THUMB,"","");
        this.author = author;
        this.digest = digest;
        this.show_cover_pic = show_cover_pic;
        this.content = content;
        this.content_source_url = content_source_url;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb_media_id() {
        return thumb_media_id;
    }

    public void setThumb_media_id(String thumb_media_id) {
        this.thumb_media_id = thumb_media_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public Integer getShow_cover_pic() {
        return show_cover_pic;
    }

    public void setShow_cover_pic(Integer show_cover_pic) {
        this.show_cover_pic = show_cover_pic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent_source_url() {
        return content_source_url;
    }

    public void setContent_source_url(String content_source_url) {
        this.content_source_url = content_source_url;
    }
}
