package cn.zkdcloud.util;

import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 关于输入/输出/流的操作
 */
public class StreamUtil {

    private static Logger logger = Logger.getLogger(StreamUtil.class);

    private static XStream xStream;

    static {
        xStream = new XStream();
        xStream.autodetectAnnotations(true);
    }

    /**
     * request输入流中xml to json(除去数组-.-)
     *
     * @param request request
     * @return json
     */
    public static String xmlToJson(HttpServletRequest request) {
        InputStream inputStream = null;
        SAXReader reader = new SAXReader();
        Document doc = null;
        StringBuffer jsonBuffer = new StringBuffer("");
        try {
            inputStream = request.getInputStream();
            doc = reader.read(inputStream);

            Element root = doc.getRootElement();
            jsonBuffer.append(getElementValue(root));
            inputStream.close();

            return jsonBuffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 递归返回xml子列表json字符串
     *
     * @param element e
     * @return json
     */
    public static String getElementValue(Element element) {
        List<Element> childElements = element.elements();

        if (AssertUtil.isNullOrEmpty(childElements)) {
            return "\"" + element.getText() + "\"";
        }

        StringBuffer jsonBuffer = new StringBuffer("{");
        Iterator<Element> iterator = childElements.iterator();
        while (iterator.hasNext()) {
            Element e = iterator.next();
            jsonBuffer.append("\"" + e.getName() + "\" : ");
            jsonBuffer.append(getElementValue(e));

            if (iterator.hasNext()) {
                jsonBuffer.append(",");
            }
        }

        jsonBuffer.append("}");
        return jsonBuffer.toString();
    }

    /**
     * bean 转化成xml格式
     *
     * @param object
     * @return
     */
    public static String ObjToXml(Object object) {
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
