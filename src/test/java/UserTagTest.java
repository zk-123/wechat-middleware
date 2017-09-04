import cn.zkdcloud.core.UserTagComponent;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * 用户标签测试
 *
 * @author zk
 * @version 2017/9/2
 */
public class UserTagTest {

    private UserTagComponent userTagComponent;

    @Before
    public void userTag(){
        userTagComponent = UserTagComponent.getInstance();
    }

    @Test
    public void createTag(){
        System.out.println(userTagComponent.createTag("管理员"));
    }

    @Test
    public void getTags(){
        System.out.println(userTagComponent.getTags());
    }

    @Test
    public void updateTag(){
        System.out.println(userTagComponent.updateTag(100,"管理员1"));
    }

    @Test
    public void getListUserTag(){
        System.out.println(userTagComponent.getUserByTagId(100,null));
    }

    @Test
    public void batchTag(){
        System.out.println(userTagComponent.batchTags(Arrays.asList("oGCUGwLIsuHjdD2g1novVyit2S5M"),100));
    }

    @Test
    public void getTagsByUser(){
        System.out.println(userTagComponent.getTagsByUser("oGCUGwLIsuHjdD2g1novVyit2S5M"));
    }
}
