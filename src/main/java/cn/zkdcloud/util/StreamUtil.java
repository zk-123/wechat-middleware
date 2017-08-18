package cn.zkdcloud.util;

import cn.zkdcloud.dto.NewsArticle;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 关于输入/输出/流的操作
 */
public class StreamUtil {

    /**
     * request输入流中xml to map
     *
     * @param request request
     * @return retmap
     */
    public static Map<String, String> xmlToMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        InputStream inputStream = null;
        SAXReader reader = new SAXReader();
        Document doc = null;

        try {
            inputStream = request.getInputStream();
            doc = reader.read(inputStream);

            Element root = doc.getRootElement();
            List<Element> list = root.elements();
            for (Element e : list) {
                map.put(e.getName(), e.getText());
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

    /**
     * bean 转化成xml格式
     *
     * @param object
     * @return
     */
    public static String ObjToXml(Object object) {
        XStream xStream = new XStream();
        xStream.alias("xml", object.getClass());
        xStream.alias("item", NewsArticle.class);
        return xStream.toXML(object);
    }

    /**
     * inputStream to str(输入流转字符串)
     *
     * @param inputStream
     * @return
     */
    public static String inputStreamToStr(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer sb = new StringBuffer("");
        String len;

        try {
            while ((len = reader.readLine()) != null) {
                sb.append(len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
