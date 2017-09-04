import cn.zkdcloud.component.material.Material;
import cn.zkdcloud.component.material.MaterialArticle;
import cn.zkdcloud.component.material.MaterialType;
import cn.zkdcloud.core.MaterialComponent;
import com.alibaba.fastjson.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zk
 * @version 2017/8/31
 */
public class MaterialTest {
    private MaterialComponent materialComponent;

    @Before
    public void prepera(){
        materialComponent = MaterialComponent.getInstance();
    }

    @Test
    public void addMaterial(){
        File file = new File("C:\\Users\\zk\\Pictures\\Saved Pictures\\billiard.png");
        System.out.println(materialComponent.uploadPersistentImage(file));
    }

    @Test
    public void downloadMaterial(){
        String media_id = "sPNKRzZ-9eSpSAkt53Ffo2ipA8mqufvNMdOLMEt8OZejebmwYnrE-b9OlbUFmWHH";
        File file = materialComponent.downloadTempMaterial(media_id,"C:\\upload\\abc.png");
        System.out.println(file.getAbsolutePath());
    }

    @Test
    public void uploadPersistentMaterial(){
        File file = new File("C:\\Users\\zk\\Pictures\\Saved Pictures\\billiard.png");
        String ret = materialComponent.uploadTempMaterial(file, MaterialType.IMAGE);
        System.out.println(ret);
    }

    @Test
    public void uploadAndDownloadMaterial(){
        File file = new File("C:\\Users\\zk\\Pictures\\Saved Pictures\\billiard.png");
        String thumbRet = materialComponent.uploadPersistentMaterial(file,MaterialType.THUMB,"thumb","thumb");
        if(null != thumbRet){
            String thumbMediaId = JSONObject.parseObject(thumbRet).getString("media_id");
            String sourceUrl = "https://mp.weixin.qq.com/s?__biz=MzAwNTEyOTE3NQ==&mid=2650446261&idx=1&sn=c04e9ccc0fe78faf717d6a3ee383cb2b&chksm=832f6f0db458e61b099315393a38ad7439d649bad9c4f730388055d10bddd45243683e497e42&scene=0#rd";
            List<MaterialArticle> articles = new ArrayList<>();
            articles.add(new MaterialArticle("title1",thumbMediaId,"zk","zkd",
                    1,"content",sourceUrl));
            System.out.println(materialComponent.uploadPersistentMaterial(articles));
        }
    }

    @Test
    public void getPersistentNews(){
        List<MaterialArticle> articles = materialComponent.downloadPersistentNews("zjdtsgqU7w3nen--evLhEgORUjxT017A6xI8U-pXyMk");
        System.out.println(articles.get(0));
    }

    @Test
    public void getCountMaterials(){
        System.out.println(materialComponent.countPersistentMaterial());
    }

    @Test
    public void getListMaterials(){
        List<Material> materials = materialComponent.getListPersistentMaterial(MaterialType.IMAGE,0,20);
        System.out.println("1");
    }

}
