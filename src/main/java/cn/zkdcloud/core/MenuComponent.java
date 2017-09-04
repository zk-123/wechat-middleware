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

    public static MenuComponent menuComponent;

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

    @Override
    public void init() {
    }

    private MenuComponent(){

    }
    /**
     * 创建菜单
     *
     * @param menu menu
     * @return is or not success
     */
    public boolean createMenu(Menu menu) {
        String data = JSON.toJSONString(menu).toLowerCase();//notice should be lowerCase otherwise will return ' not utf-8' error
        String ret = HttpUtil.doPost(MenuComponent.CREATE, data);
        if (JsonUtil.isError(ret)) {
            return false;
        }
        logger.info("create menu success");
        return true;
    }

    /**
     * 获取菜单
     *
     * @return menuBean
     */
    public Menu getMenu() {
        String ret = HttpUtil.doGet(GET);
        if (JsonUtil.isError(ret)) {
            logger.info("get menu fail");
            return null;
        }

        JSONObject menuJson = JSON.parseObject(ret).getJSONObject("menu");
        return Menu.buildMenu(menuJson);
    }

    /**
     * 删除菜单
     */
    public boolean deleteMenu() {
        String ret = HttpUtil.doGet(DELETE);
        if (JsonUtil.isError(ret)) {
            return false;
        }
        logger.info("delete menu success");
        return true;
    }

    /**
     * 获取MenuComponent instance
     * @return menuComponentBean
     */
    public static MenuComponent getInstance(){
        if(null == menuComponent){
            menuComponent = new MenuComponent();
        }
        return menuComponent;
    }
}
