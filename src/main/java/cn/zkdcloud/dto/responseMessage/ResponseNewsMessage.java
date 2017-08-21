package cn.zkdcloud.dto.responseMessage;

import cn.zkdcloud.dto.ResponseMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * 回复图文消息
 */
@XStreamAlias("xml")
public class ResponseNewsMessage extends ResponseMessage{
    @XStreamAlias("ArticleCount")
    private Integer articleCount;

    @XStreamImplicit
    @XStreamAlias("Articles")
    private List<Artcle> artcles;

    /**添加article
     *
     * @param title title
     * @param description description
     * @param picUrl picurl
     * @param url url
     * @return articles
     */
    public List<Artcle> addArticle(String title,String description,String picUrl,String url){
        if(artcles == null){
            artcles = new ArrayList<>();
        }
        artcles.add(Artcle.getArticle(title,description,picUrl,url));
        return artcles;
    }

    public Integer getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(Integer articleCount) {
        this.articleCount = articleCount;
    }

    public List<Artcle> getArtcles() {
        return artcles;
    }

    public void setArtcles(List<Artcle> artcles) {
        this.artcles = artcles;
    }

    @XStreamAlias("item")
    static class Artcle{
        /*
        标题
         */
        @XStreamAlias("Title")
        private String title;
        /*
        描述
         */
        @XStreamAlias("Description")
        private String description;
        /*
        图片链接
         */
        @XStreamAlias("PicUrl")
        private String picUrl;
        /*
        跳转链接
         */
        @XStreamAlias("Url")
        private String url;

        public static Artcle getArticle(String title,String description,String picUrl,String url){
            Artcle ret = new Artcle();
            ret.title = title;
            ret.description = description;
            ret.picUrl = picUrl;
            ret.url = url;
            return ret;
        }
    }
}
