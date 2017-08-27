import cn.zkdcloud.core.CustomComponent;
import cn.zkdcloud.util.AccessToken;
import org.junit.Test;

/**
 * 客服测试
 * @author zk
 * @version 2017/8/26
 */
public class CustomerTest {

    @Test
    public void addCustomer(){
        CustomComponent.getInstance().add("oGCUGwLIsuHjdD2g1novVyit2S5M@gh_0e8abd0c496c",
                "kefu1",null);
    }

    @Test
    public void tockenTest(){
        System.out.println(AccessToken.getAccessToken());
        System.out.println(AccessToken.getAccessToken());
        System.out.println(AccessToken.getAccessToken());
        System.out.println(AccessToken.getAccessToken());
        System.out.println(AccessToken.getAccessToken());
        System.out.println(AccessToken.getAccessToken());
        System.out.println(AccessToken.getAccessToken());

    }
}
