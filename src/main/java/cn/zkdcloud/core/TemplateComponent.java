package cn.zkdcloud.core;

import cn.zkdcloud.util.AccessToken;
import cn.zkdcloud.util.HttpUtil;
import cn.zkdcloud.util.JsonUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 模板组件
 *
 * @author zk
 * @version 2017/8/29
 */
public class TemplateComponent implements Component {

    private static Logger logger = Logger.getLogger(TemplateComponent.class);

    /**
     * template实例
     */
    public static TemplateComponent templateComponent;

    /**
     * 本地模板库
     */
    private List<TemplateMessage> templateList;

    /**
     * 设置行业模板URL(POST设置行业还可在微信公众平台后台完成，每月可修改行业1次)
     */
    public static String SET_INDUSTRY = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=" + AccessToken.getAccessToken();

    /**
     * 获取行业信息(GET)
     */
    public static String GET_INDUSTRY = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=" + AccessToken.getAccessToken();

    /**
     * 获得模板ID(POST从行业模板库选择模板到帐号后台，获得模板ID的过程可在微信公众平台后台完成。为方便第三方开发者，提供通过接口调用的方式来获取模板ID)
     */
    public static String GET_TEMPLATE_ID = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=" + AccessToken.getAccessToken();

    /**
     * 获取所有模板列表(GET)
     */
    public static String GET_ALL_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=" + AccessToken.getAccessToken();

    /**
     * 发送模板消息(POST)
     */
    public static String SEND_TEMPLATE_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + AccessToken.getAccessToken();

    /**
     * 删除指定的模板消息(POST)
     */
    public static String DELETE_TEMPLATE_MESSAGE = "https://api.weixin.qq.com/cgi-bin/template/del_private_template?access_token=" + AccessToken.getAccessToken();

    /**
     * 初始化，可以初始化行业(慎重，建议在公账号中执行)
     */
    @Override
    public void init() {
        getListTemplateMessage(true);//初始化本地模板库
    }

    private TemplateComponent() {

    }

    /**
     * 设置行业模板(具体行业列表 @see https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277)
     *
     * @param industry_id1 行业1
     * @param industry_id2 行业2
     */
    public void setTemplateIndustry(Integer industry_id1, Integer industry_id2) {
        JSONObject industry = new JSONObject();
        industry.put("industry_id1", String.valueOf(industry_id1));
        industry.put("industry_id2", String.valueOf(industry_id2));

        String ret = HttpUtil.doPost(SET_INDUSTRY, industry.toJSONString());
        if (JsonUtil.isError(ret)) {
            logger.info("set industry is error");
            return;
        }
        logger.info("set industry success");
    }

    /**
     * 获取行业信息
     *
     * @return @return retJson example：
     * {
     * "primary_industry":{"first_class":"运输与仓储","second_class":"快递"},
     * "secondary_industry":{"first_class":"IT科技","second_class":"互联网|电子商务"}
     * }
     */
    public String getAllIndustry() {
        return HttpUtil.doGet(GET_INDUSTRY);
    }

    /**
     * 获取所有的模板列表
     *
     * @param refresh 是否刷新模板消息的列表
     */
    public List<TemplateMessage> getListTemplateMessage(boolean refresh) {
        if (true == refresh) {
            String ret = HttpUtil.doGet(GET_ALL_TEMPLATE);
            if (null == templateList) {
                templateList = new ArrayList<TemplateMessage>();
            }
            templateList = JSON.parseArray(JSONObject.parseObject(ret).getString("template_list"), TemplateMessage.class);
            logger.info("init get templateList success");
        }
        return templateList;
    }

    /**
     * 获取所有模板列表(默认不刷新)
     *
     * @return listTemplateMessage
     */
    public List<TemplateMessage> getListTemplateMessage() {
        return getListTemplateMessage(false);
    }

    /**
     * 根据模板名获取模板(会自动查找本地缓存，如没有更新缓存再查找)
     *
     * @param name 模板名
     * @return templateMessage
     */
    public TemplateMessage getTemplateByName(String name) {
        TemplateMessage templateMessage;
        templateMessage = getLocalTemplateByName(name);
        if (null == templateMessage) {
            getListTemplateMessage(true);//refresh local templates
            templateMessage = getLocalTemplateByName(name);
        }
        return templateMessage;
    }

    /**
     * 根据模板名获取本地缓存的模板
     *
     * @param name 模板名
     * @return Template
     */
    public TemplateMessage getLocalTemplateByName(String name) {
        if (null != templateList) {
            for (TemplateMessage template : templateList) {
                if (template.getTitle().equals(name)) {
                    return template;
                }
            }
        }
        return null;
    }

    /**
     * 获得模板ID
     *
     * @param template_id_short 模板库中模板的编号
     * @return templateId
     */
    public String getTemplateId(String template_id_short) {
        String ret = HttpUtil.doPost(GET_TEMPLATE_ID, template_id_short);
        if (!JsonUtil.isError(ret)) {
            return JSONObject.parseObject(ret).getString("template_id");
        }
        return null;
    }

    /**
     * 给指定人发送模本消息
     *
     * @param templateMessage 模板消息
     */
    public void sendTemplateMessage(TemplateMessage templateMessage) {
        String ret = HttpUtil.doPost(SEND_TEMPLATE_MESSAGE, JSON.toJSONString(templateMessage));
        if (JsonUtil.isError(ret)) {
            logger.info("send " + templateMessage.getTemplate_id() + " template message to" + templateMessage.getTouser() + "is fail");
        }
        logger.info("send template success");
    }

    /**
     * 根据模板名删除模板
     *
     * @param name 模板消息名
     */
    public void deleteTemplateMessageByName(String name) {
        TemplateMessage template = getTemplateByName(name);
        if (null != template) {
            deleteTemplateMessageById(template.getTemplate_id());
            return;
        }
    }

    /**
     * 根据模板id删除模板
     *
     * @param template_id 模板id
     */
    public void deleteTemplateMessageById(String template_id) {
        JSONObject data = new JSONObject();
        data.put("template_id", template_id);
        String ret = HttpUtil.doPost(DELETE_TEMPLATE_MESSAGE, data.toJSONString());
        if (JsonUtil.isError(ret)) {
            logger.info("delete template message fail");
            return;
        }
        logger.info("delete template message success");
    }

    public static TemplateComponent getInstance() {
        if (null == templateComponent) {
            templateComponent = new TemplateComponent();
        }
        return templateComponent;
    }

    /**
     * 模板消息
     */
    public static class TemplateMessage {
        /**
         * 发送人(OPEN ID)
         */
        private String touser;

        /**
         * 模板消息题目
         */
        private String title;

        /**
         * 模板Id
         */
        private String template_id;

        /**
         * 链接url
         */
        private String url;

        /**
         * 主产业
         */
        private String primary_industry;

        /**
         * 副产业
         */
        private String deputy_industry;

        /**
         * 模板内容
         */
        private String content;

        /**
         * 模板例子
         */
        private String example;
        /**
         * 小程序链接
         * {
         * "appid":"xiaochengxuappid12345",
         * "pagepath":"index?foo=bar"
         * }
         */
        private JSONObject miniprogram;

        /**
         * 填充的数据
         */
        private JSONObject data;

        private TemplateMessage() {

        }

        public TemplateMessage(String touser, String template_id, String url) {
            this.touser = touser;
            this.template_id = template_id;
            this.url = url;
        }

        public void setTouserAndUrl(String touser, String url) {
            this.touser = touser;
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPrimary_industry() {
            return primary_industry;
        }

        public void setPrimary_industry(String primary_industry) {
            this.primary_industry = primary_industry;
        }

        public String getDeputy_industry() {
            return deputy_industry;
        }

        public void setDeputy_industry(String deputy_industry) {
            this.deputy_industry = deputy_industry;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getExample() {
            return example;
        }

        public void setExample(String example) {
            this.example = example;
        }

        public void setMiniprogram(JSONObject miniprogram) {
            this.miniprogram = miniprogram;
        }

        public String getTouser() {
            return touser;
        }

        public void setTouser(String touser) {
            this.touser = touser;
        }

        public String getTemplate_id() {
            return template_id;
        }

        public void setTemplate_id(String template_id) {
            this.template_id = template_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public JSONObject getMiniprogram() {
            return miniprogram;
        }

        /**
         * 如果有必要添加小程序链接
         *
         * @param appid    小程序appid
         * @param pagepath 路径
         */
        public void setMiniprogram(String appid, String pagepath) {
            JSONObject mini = new JSONObject();
            mini.put("appid", appid);
            mini.put("pagepath", pagepath);
            this.miniprogram = mini;
        }

        public JSONObject getData() {
            return data;
        }

        public void setData(JSONObject data) {
            this.data = data;
        }

        /**
         * 自定义颜色填充数据
         *
         * @param dataName dataName
         * @param value    value
         * @param color    color
         * @return TemplateMessageBean
         */
        public TemplateMessage addData(String dataName, String value, String color) {
            if (this.data == null) {
                this.data = new JSONObject();
            }

            JSONObject dataOne = new JSONObject();
            dataOne.put("value", value);
            dataOne.put("color", color);
            this.data.put(dataName, dataOne);
            return this;
        }

        /**
         * 默认填充数据
         *
         * @param dataName dataName
         * @param value    value
         * @return TemplateMessageBean
         */
        public TemplateMessage addData(String dataName, String value) {
            return addData(dataName, value, "#000");
        }
    }
}
