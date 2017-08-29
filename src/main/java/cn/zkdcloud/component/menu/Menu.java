package cn.zkdcloud.component.menu;

import cn.zkdcloud.component.menu.button.MaterialButton;
import cn.zkdcloud.component.menu.button.MiniProgramButton;
import cn.zkdcloud.component.menu.button.NormalButton;
import cn.zkdcloud.component.menu.button.ViewButton;
import cn.zkdcloud.core.MenuComponent;
import cn.zkdcloud.util.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单
 *
 * @author zk
 * @version 2017/8/25
 */
public class Menu {

    private List<AbstractButton> button = new ArrayList<>();

    /**
     * 为菜单添加一个子按钮
     *
     * @param button button
     * @return menu
     */
    public Menu addButton(AbstractButton button) {
        getButton().add(button);
        return this;
    }

    /**
     * 根据json构建菜单
     *
     * @param menuJson menuJson
     * @return menuBean
     */
    public static Menu buildMenu(JSONObject menuJson) {
        Menu menu = new Menu();
        menu.setButton(buildSubMenu(menuJson.getJSONArray("button")));
        return menu;
    }

    /**
     * 根据子按钮的JsonArray构建ListButton
     *
     * @param subButtonArray ButtonArray
     * @return subButton
     */
    public static List<AbstractButton> buildSubMenu(JSONArray subButtonArray) {
        if (null == subButtonArray || subButtonArray.size() == 0) {
            return null;
        }

        List<AbstractButton> ret = new ArrayList<>();
        for (int i = 0; i < subButtonArray.size(); i++) {
            AbstractButton button;
            JSONObject subButton = subButtonArray.getJSONObject(i);

            if (null == subButton.getString("type")) { //如果子按钮是菜单
                button = NormalButton.creaetOne(subButton.getString("name"));
                button.setSub_button(buildSubMenu(subButton.getJSONArray("sub_button")));
                ret.add(button);
                continue;
            }
            button = parseButton(subButton);//如果是按钮则转化
            ret.add(button);
        }
        return ret;
    }

    /**
     * 根据按钮类型，将jsonObject 转化成对应的对象
     *
     * @param jsonButton jsonButton
     * @return buttonBean
     */
    public static AbstractButton parseButton(JSONObject jsonButton) {
        AbstractButton button;
        jsonButton.put("type", jsonButton.getString("type").toUpperCase());

        switch (MenuType.valueOf(jsonButton.getString("type"))) {
            case VIEW:
                button = jsonButton.toJavaObject(ViewButton.class);
                break;
            case MINIPROGRAM:
                button = jsonButton.toJavaObject(MiniProgramButton.class);
                break;
            case MEDIA_ID:
                button = jsonButton.toJavaObject(MaterialButton.class);
                break;
            case VIEW_LIMITED:
                button = jsonButton.toJavaObject(MaterialButton.class);
                break;
            default:
                button = jsonButton.toJavaObject(NormalButton.class);
        }
        return button;
    }

    public List<AbstractButton> getButton() {
        return button;
    }

    public void setButton(List<AbstractButton> button) {
        this.button = button;
    }
}
