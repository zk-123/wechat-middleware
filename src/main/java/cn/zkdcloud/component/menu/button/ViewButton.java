package cn.zkdcloud.component.menu.button;

import cn.zkdcloud.component.menu.AbstractButton;
import cn.zkdcloud.component.menu.MenuType;

/**
 * 链接按钮
 *
 * @author zk
 * @version 2017/8/25
 */
public class ViewButton extends AbstractButton {

    /**
     * view按钮中用到的url
     */
    protected String url;

    protected ViewButton() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 创建 viewButton
     *
     * @param name name
     * @param url  urlLink
     * @return button
     */
    public static ViewButton createOne(String name, String url) {
        ViewButton viewButton = new ViewButton();
        viewButton.setType(MenuType.VIEW);
        viewButton.setName(name);
        viewButton.setUrl(url);
        return viewButton;
    }
}
