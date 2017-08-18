package cn.zkdcloud.dto;

import cn.zkdcloud.util.Const;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zk on 2016/11/5.
 */
public class NewsMessage extends BaseMessage{


    private int ArticleCount;
    private List<NewsArticle> Articles = null;

    public NewsMessage(){

    }
    public NewsMessage(BaseMessage baseMessage){
        this.setToUserName(baseMessage.getToUserName());
        this.setFromUserName(baseMessage.getFromUserName());
        this.setCreateTime(baseMessage.getCreateTime());
        this.setMsgType(Const.MSGType_NEWS);
    }

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<NewsArticle> getArticles() {
        return Articles;
    }

    public void setArticles(List<NewsArticle> articles) {
        Articles = articles;
    }

    public void addArticle(String Title,String Description,String PicUrl,String Url) {
        NewsArticle newsArticle = new NewsArticle(Title,Description,PicUrl,Url);

        if(Articles == null)  Articles  = new ArrayList<NewsArticle>();

        Articles.add(newsArticle);
    }
}
