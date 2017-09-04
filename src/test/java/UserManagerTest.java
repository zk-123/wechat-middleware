import cn.zkdcloud.component.user.UserInfo;
import cn.zkdcloud.core.UserManagerComponent;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author zk
 * @version 2017/9/3
 */
public class UserManagerTest {

    private UserManagerComponent userManagerComponent;

    @Before
    public void before(){
        userManagerComponent = UserManagerComponent.getInstance();
    }

    @Test
    public void getUserInfoTest(){
        List<UserInfo> userInfoList = userManagerComponent.getBatchUserInfo(Arrays.asList("oGCUGwLIsuHjdD2g1novVyit2S5M"));
        System.out.println(userInfoList);
    }

    @Test
    public void remarkUser(){
        userManagerComponent.remarkUser("oGCUGwLIsuHjdD2g1novVyit2S5M","zk123");
    }

    @Test
    public void getOpenIdsTest(){
        System.out.println(userManagerComponent.getUserOpenids());
    }

    @Test
    public void blckUser(){
        System.out.println(userManagerComponent.batchUnblackUsers(Arrays.asList("oGCUGwLIsuHjdD2g1novVyit2S5M")));
    }
}
