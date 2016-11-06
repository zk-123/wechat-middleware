package Service;

import Entity.NewsArticle;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zk on 2016/10/30.
 */
@Service
public class UtilService {

    public Map<String,String> xmlToMap(HttpServletRequest request)
    {
        Map<String,String> map  = new HashMap<String, String>();
        InputStream inputStream = null;
        SAXReader reader = new SAXReader();
        Document doc = null;

        try {
            inputStream = request.getInputStream();
            doc = reader.read(inputStream);

            Element root = doc.getRootElement();
            List<Element> list  = root.elements();
            for(Element e: list)
            {
                map.put(e.getName(),e.getText());
            }
            inputStream.close();

            return map;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String ObjToXml(Object object)
    {
        XStream xStream = new XStream();
        xStream.alias("xml",object.getClass());
        xStream.alias("item", NewsArticle.class);
        return xStream.toXML(object);
    }
}
