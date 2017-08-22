package cn.zkdcloud.dto.responseMessage;

import cn.zkdcloud.dto.ResponseMessage;
import cn.zkdcloud.entity.MsgType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * 回复图文消息
 */
@XStreamAlias("xml")
public class ResponseNewsMessage extends ResponseMessage {
    /**
     * 文章个数
     */
    @XStreamAlias("ArticleCount")
    private Integer articleCount;
    /**
     * 由于微信的需要，需要建立一个这样的包装list的类
     */
    @XStreamAlias("Articles")
    private Articles articles;

    public ResponseNewsMessage() {

    }

    public ResponseNewsMessage(String toUserName, String fromUserName) {
        super(toUserName, fromUserName);
        this.msgType = MsgType.NEWS;
    }

    /**
     * 添加article
     *
     * @param title       title
     * @param description description
     * @param picUrl      picurl
     * @param url         url
     * @return articles
     */
    public ResponseNewsMessage addArticle(String title, String description, String picUrl, String url) {
        if (articles == null) {
            articles = new Articles();
        }
        articles.articleList.add(Article.getArticle(title, description, picUrl, url));
        this.articleCount = articles.articleList.size();
        return this;
    }

    /**
     * 集合article
     */
    public static class Articles {

        @XStreamImplicit
        private List<Article> articleList = new ArrayList<>();
    }

    /**
     * 单个article
     */
    @XStreamAlias("item")
    public static class Article {
        /**
         * 标题
         */
        @XStreamAlias("Title")
        private String title;
        /**
         * 描述
         */
        @XStreamAlias("Description")
        private String description;
        /**
         * 图片链接
         */
        @XStreamAlias("PicUrl")
        private String picUrl;
        /**
         * 跳转链接
         */
        @XStreamAlias("Url")
        private String url;

        public static Article getArticle(String title, String description, String picUrl, String url) {
            Article ret = new Article();
            ret.title = title;
            ret.description = description;
            ret.picUrl = picUrl;
            ret.url = url;
            return ret;
        }
    }
}
