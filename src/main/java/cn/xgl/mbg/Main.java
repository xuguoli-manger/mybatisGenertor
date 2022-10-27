package cn.xgl.mbg;

import cn.xgl.mbg.config.xml.ConfigurationParserOverride;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: xgl
 * @Date: 2022/10/26 17:03
 */
public class Main {

    public static InputStream getResourceAsStream(String path) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }

    public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        //指定逆向工程配置文件
        ConfigurationParser cp = new ConfigurationParserOverride(warnings);
        Configuration config =
                cp.parseConfiguration(getResourceAsStream("mapper-generatorconfig.xml"));
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        //show();

    }

    private static void show(){
        //1.创建解析器工厂
        //2.创建解析器
        //3.获得dom对象
        try {
            DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbd = fac.newDocumentBuilder();
            Document doc = dbd.parse(getResourceAsStream("mapper-generatorconfig.xml"));

            NodeList books = doc.getElementsByTagName("generatorConfiguration");
            //	    System.out.println(books.getLength());
            for (int i = 0; i < books.getLength(); i++) {
                Node book = books.item(i);
                NodeList fields = book.getChildNodes();
                //		System.out.println(fields.getLength());
                for (int j = 0; j < fields.getLength(); j++) {
                    Node field = fields.item(j);
                    //判断node是否是标题或者作者
                    if("title".equals(field.getNodeName()) || "author".equals(field.getNodeName())) {
                        //标题或作者
                        System.out.print(field.getTextContent()+" ");
                    }
                }
                System.out.println("");
            }
            System.out.println("操作完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
