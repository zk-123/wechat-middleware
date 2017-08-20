import cn.zkdcloud.dto.acceptMessage.TextMessage;
import cn.zkdcloud.util.StreamUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zk on 2016/10/30.
 */
public class xmlAndMapTest {


    public void xmlToMap()
    {
        Map<String,String> map  = new HashMap<String, String>();
        InputStream inputStream = null;
        SAXReader reader = new SAXReader();
        Document doc = null;

        File file = new File("D:\\xmlToMap.xml");
        try {
            inputStream = new FileInputStream(file);
            doc = reader.read(inputStream);

            Element root = doc.getRootElement();
            List<Element> list  = root.elements();
            for(Element e: list)
            {
                map.put(e.getName(),e.getText());
                System.out.println(e.getName()+","+e.getText());
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("你好");
    }

    @Test
    public void Xstream() {
        TextMessage textMessage = new TextMessage();
        System.out.println(StreamUtil.ObjToXml(textMessage));
    }
}
