package com.utile;

import com.adb.bean.EventBean;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XmlUtile {
    public static String xmlName = "config.xml";

    //读取配置
    public static EventBean readConfig(String fileName) {
        EventBean bean = null;
        try {
            File file = new File(fileName);
            if (!file.isFile() || !file.exists()) {
                setXmlName();
            }
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(fileName);
            //获取 XML 文档中所有名为“data1”的元素节点
            NodeList bookNodes = document.getElementsByTagName("data1");
            // 遍历每个“data1”元素节点，并输出其子元素的内容,
            for (int i = 0; i < bookNodes.getLength(); i++) {
                Node bookNode = bookNodes.item(i);
                Element bookElement = (Element) bookNode;
                String dataType = bookElement.getElementsByTagName("dataType").item(0).getTextContent();
                String videoBtnXY = bookElement.getElementsByTagName("videoBtnXY").item(0).getTextContent();
                String adSlide = bookElement.getElementsByTagName("adSlide").item(0).getTextContent();
                String actBtnXY = bookElement.getElementsByTagName("actBtnXY").item(0).getTextContent();
                String actBtnXY2 = bookElement.getElementsByTagName("actBtnXY2").item(0).getTextContent();

                String taskBtnXY = bookElement.getElementsByTagName("taskBtnXY").item(0).getTextContent();
                String adBtnXY = bookElement.getElementsByTagName("adBtnXY").item(0).getTextContent();
                String adNum = bookElement.getElementsByTagName("adNum").item(0).getTextContent();
                bean = new EventBean();
                bean.dataType = stringToInt(dataType);
                //
                String[] str = videoBtnXY.split("_");
                bean.videoBtnX = stringToInt(str[0]);
                bean.videoBtnY = stringToInt(str[1]);
                //
                bean.adSlide = adSlide.replace("_"," ");
                //
                str = actBtnXY.split("_");
                bean.actBtnX = stringToInt(str[0]);
                bean.actBtnY = stringToInt(str[1]);
                //
                str = actBtnXY2.split("_");
                bean.actBtnX2 = stringToInt(str[0]);
                bean.actBtnY2 = stringToInt(str[1]);
                //
                str = taskBtnXY.split("_");
                bean.taskBtnX = stringToInt(str[0]);
                bean.taskBtnY = stringToInt(str[1]);
                //
                str = adBtnXY.split("_");
                bean.adBtnX = stringToInt(str[0]);
                bean.adBtnY = stringToInt(str[1]);
                //
                bean.adNum = Integer.parseInt(adNum);
                //
                System.out.println("dataType:" + dataType + " videoBtnXY:" + videoBtnXY + " actBtnXY:" + actBtnXY +
                        " taskBtnXY:" + taskBtnXY + " adSlide:" + adSlide + " adBtnXY:" + adBtnXY + " actBtnXY2:" + actBtnXY2 + " adNum:" + adNum);
            }


        } catch (Exception e) {
            System.out.println("解析错误：" + e.getMessage());
        }
        return bean;
    }

    private static int stringToInt(String numStr) {
        int num = -1;
        try {
            num = Integer.valueOf(numStr);
        } catch (Exception e) {
            System.out.println("转化失败" + e.getMessage());
        }
        return num;
    }

    public static void setXmlName() {
        try {
            // 创建DocumentBuilderFactory实例
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            // 通过DocumentBuilderFactory创建DocumentBuilder实例
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            // 创建Document实例
            Document document = documentBuilder.newDocument();
            // 创建根元素
            Element root = document.createElement("root");
            document.appendChild(root);
            Element data1 = document.createElement("data1");
            root.appendChild(data1);


            //dataType（数据类型）
            Element dataType = document.createElement("dataType");
            dataType.appendChild(document.createTextNode("2"));
            data1.appendChild(dataType);
            //赚钱按钮
            Element videoBtnXY = document.createElement("videoBtnXY");
            videoBtnXY.appendChild(document.createTextNode("538_2121"));
            data1.appendChild(videoBtnXY);
            //滑动
            Element adSlide = document.createElement("adSlide");
            adSlide.appendChild(document.createTextNode("250_250_250_-10"));
            data1.appendChild(adSlide);
            //活动按钮
            Element actBtnX = document.createElement("actBtnXY");
            actBtnX.appendChild(document.createTextNode("919_1592"));
            data1.appendChild(actBtnX);
            //活动按钮2
            Element actBtnXY2 = document.createElement("actBtnXY2");
            actBtnXY2.appendChild(document.createTextNode("927_1500"));
            //actBtnXY2.appendChild(document.createTextNode("917_1197"));

            data1.appendChild(actBtnXY2);
            //任务按钮
            Element taskBtnX = document.createElement("taskBtnXY");
            taskBtnX.appendChild(document.createTextNode("517_2013"));
            data1.appendChild(taskBtnX);
            //广告按钮
            Element adBtnX = document.createElement("adBtnXY");
            adBtnX.appendChild(document.createTextNode("845_1730"));
            data1.appendChild(adBtnX);

            //adNum 广告次数
            Element adNum = document.createElement("adNum");
            adNum.appendChild(document.createTextNode("20"));
            data1.appendChild(adNum);


            //创建TransformerFactory实例，并创建Transformer实例
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            // 设置输出格式，例如是否缩进等
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            // 写入文件
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlName));
            transformer.transform(domSource, streamResult);
        } catch (Exception e) {
            System.out.println("写入错误：" + e.getMessage());
        }
    }
}
