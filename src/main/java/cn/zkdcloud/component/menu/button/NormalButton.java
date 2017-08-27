package cn.zkdcloud.component.menu.button;

import cn.zkdcloud.component.menu.AbstractButton;
import cn.zkdcloud.component.menu.MenuType;

/**
 * 一般按钮
 *
 * @author zk
 * @version 2017/8/25
 */
public class NormalButton extends AbstractButton {
    /**
     * key
     */
    private String key;

    private NormalButton(){

    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 返回一个空按钮，当成一级菜单
     * @param name 目录名称
     * @return
     */
    public static NormalButton creaetOne(String name){
        NormalButton button = new NormalButton();
        button.setName(name);
        return button;
    }

    /**
     * 创建一个一般按钮
     * @return button
     */
    public static NormalButton createOne(MenuType type, String key, String name){
        NormalButton button = new NormalButton();
        button.setType(type);
        button.setKey(key);
        button.setName(name);
        return button;
    }
}
