package cn.zkdcloud.component.material;

/**
 * 一般素材(图片，语音，视频)
 *
 * @author zk
 * @version 2017/9/2
 */
public class Material {
    /**
     * 素材的media_id
     */
    private String media_id;

    /**
     * name
     */
    private String name;

    /**
     * 这篇素材的最后更新时间
     */
    private String update_time;

    /**
     * 素材Url
     */
    private String url;

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
