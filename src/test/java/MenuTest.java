import cn.zkdcloud.component.menu.AbstractButton;
import cn.zkdcloud.component.menu.Menu;
import cn.zkdcloud.component.menu.button.NormalButton;
import cn.zkdcloud.core.MenuComponent;
import com.alibaba.fastjson.JSONObject;
import org.junit.Before;
import org.junit.Test;

/**
 * @author zk
 * @version 2017/8/26
 */
public class MenuTest {

    MenuComponent menuComponent;

    @Before
    public void initMenu(){
        menuComponent = MenuComponent.getInstance();
    }

    @Test
    public void menuInitTest(){
        menuComponent.init();
    }

    @Test
    public void getMenuTest(){
        System.out.println(1);
    }

    @Test
    public void menuTest(){
        String jsonStr = "{\"menu\":{\"button\":[{\"name\":\"菜单一\",\"sub_button\":[{\"type\":\"click\",\"name\":\"click1\",\"key\":\"key1\",\"sub_button\":[]},{\"type\":\"view\",\"name\":\"view\",\"url\":\"http:\\/\\/www.baidu.com\",\"sub_button\":[]},{\"type\":\"pic_sysphoto\",\"name\":\"系统拍照\",\"key\":\"pic\",\"sub_button\":[]}]},{\"name\":\"菜单二\",\"sub_button\":[{\"type\":\"click\",\"name\":\"click1\",\"key\":\"key1\",\"sub_button\":[]},{\"type\":\"scancode_push\",\"name\":\"扫码推送事件\",\"key\":\"push\",\"sub_button\":[]},{\"type\":\"scancode_waitmsg\",\"name\":\"扫码带提示\",\"key\":\"push2\",\"sub_button\":[]}]}]}}";
        JSONObject menuJson = JSONObject.parseObject(jsonStr);
        Menu menu = Menu.buildMenu(menuJson.getJSONObject("menu"));
        System.out.println(menu);
    }

}
