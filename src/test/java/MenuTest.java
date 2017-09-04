import cn.zkdcloud.component.menu.Menu;
import cn.zkdcloud.component.menu.MenuType;
import cn.zkdcloud.component.menu.button.MaterialButton;
import cn.zkdcloud.component.menu.button.NormalButton;
import cn.zkdcloud.core.MenuComponent;
import org.junit.Before;
import org.junit.Test;

/**
 * @author zk
 * @version 2017/9/2
 */
public class MenuTest {

    private MenuComponent menuComponent;

    @Before
    public void menuTest(){
        menuComponent = MenuComponent.getInstance();
    }

    @Test
    public void setMenu(){
        Menu menu = new Menu();
        menu.addButton(NormalButton.creaetOne("菜单一").addSubButton(MaterialButton.createOne(MenuType.MEDIA_ID,"素材","VNdmGhW-DLxQXaUk7nPunurq-gMD0zn6Xh0pQlh-vyf-x-xpYRJ9xwBBGHSxwc3U")));
        menuComponent.createMenu(menu);
    }

    @Test
    public void getMenu(){
        Menu menu = menuComponent.getMenu();
        System.out.println(menu);
    }
}
