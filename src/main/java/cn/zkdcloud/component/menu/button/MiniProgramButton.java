package cn.zkdcloud.component.menu.button;

import cn.zkdcloud.component.menu.MenuType;

/**
 * 微信小程序链接
 *
 * @author zk
 * @version 2017/8/25
 */
public class MiniProgramButton extends ViewButton {

    /**
     * 小程序的appid（仅认证公众号可配置）
     */
    private String appid;

    /**
     * 小程序的页面路径
     */
    private String pagepath;

    private MiniProgramButton() {

    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPagepath() {
        return pagepath;
    }

    public void setPagepath(String pagepath) {
        this.pagepath = pagepath;
    }

    /**
     * 返回小程序按钮
     *
     * @param pagepath page path
     * @param name     button name
     * @return button
     */
    public static MiniProgramButton createOne(String name, String pagepath, String url) {
        MiniProgramButton button = new MiniProgramButton();
        button.setType(MenuType.MINIPROGRAM);
        button.setPagepath(pagepath);
        button.setName(name);
        button.setUrl(url);
        //appid 从外部文件引入
        return button;
    }
}
