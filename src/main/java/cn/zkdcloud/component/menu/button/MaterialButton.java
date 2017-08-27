package cn.zkdcloud.component.menu.button;

import cn.zkdcloud.component.menu.AbstractButton;
import cn.zkdcloud.component.menu.MenuType;

/**
 * 素材按钮（点击返回上传好的素材）
 *
 * @author zk
 * @version 2017/8/25
 */
public class MaterialButton extends AbstractButton {

    /**
     * 调用新增永久素材接口返回的合法media_id
     */
    private String media_id;

    private MaterialButton() {

    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    /**
     * 创建一个素材按钮
     *
     * @param type     type
     * @param name     name
     * @param media_id media_id
     * @return button
     */
    public static MaterialButton createOne(MenuType type, String name, String media_id) {
        MaterialButton button = new MaterialButton();
        button.setMedia_id(media_id);
        button.setName(name);
        button.setType(type);
        return button;
    }
}
