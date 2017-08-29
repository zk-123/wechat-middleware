package cn.zkdcloud.core;

import cn.zkdcloud.component.menu.*;
import cn.zkdcloud.component.menu.button.NormalButton;
import cn.zkdcloud.component.menu.button.ViewButton;
import cn.zkdcloud.util.AccessToken;
import cn.zkdcloud.util.HttpUtil;
import cn.zkdcloud.util.JsonUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

/**
 * 菜单组件
 *
 * @author zk
 * @version 2017/8/25
 */
public class MenuComponent implements Component {

    private static Logger logger = Logger.getLogger(MenuComponent.class);

    /**
     * menuComponent
     */
    private static MenuComponent menuComponent;

    /**
     * 创建菜单 URL (POST)
     */
    public static String CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + AccessToken.getAccessToken();
    /**
     * 获取菜单 URL (GET)
     */
    public static String GET = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=" + AccessToken.getAccessToken();
    /**
     * 删除菜单 URL (GET)
     */
    public static String DELETE = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + AccessToken.getAccessToken();

    /**
     * 菜单
     */
    private Menu menu;

    @Override
    public void init() {

    }

    /**
     * 如有有必要初始化菜单
     */
    public void initMenu() {
        if (menu == null) {
            menu = new Menu();
        }

        menu.addButton(NormalButton.creaetOne("菜单一")
                .addSubButton(NormalButton.createOne(MenuType.CLICK, "key1", "click1"))
                .addSubButton(ViewButton.createOne("view", "http://www.baidu.com"))
                .addSubButton(NormalButton.createOne(MenuType.PIC_SYSPHOTO, "pic", "系统拍照")))
                .addButton(NormalButton.creaetOne("菜单二")
                        .addSubButton(NormalButton.createOne(MenuType.CLICK, "key1", "click1"))
                        .addSubButton(NormalButton.createOne(MenuType.SCANCODE_PUSH, "push", "扫码推送事件"))
                        .addSubButton(NormalButton.createOne(MenuType.SCANCODE_WAITMSG, "push2", "扫码带提示")));

        JSONObject ret = menu.build();// build menu
        if (JsonUtil.isError(ret)) {
            return;
        }
    }

    /**
     * 获取菜单
     *
     * @return menuBean
     */
    public Menu getMenu() {
        String ret = HttpUtil.doGet(GET);
        if (JsonUtil.isError(ret)) {
            return null;
        }

        JSONObject menuJson = JSON.parseObject(ret).getJSONObject("menu");
        Menu menu = menuJson.toJavaObject(Menu.class);
        return menu;
    }

    /**
     * 删除菜单
     */
    public boolean deleteMenu() {
        String ret = HttpUtil.doGet(DELETE);
        return JsonUtil.isError(ret) ? false : true;
    }

    /**
     * 获取菜单组件
     *
     * @return
     */
    public static MenuComponent getInstance() {
        if (menuComponent == null) {
            menuComponent = new MenuComponent();
        }
        return menuComponent;
    }
}
