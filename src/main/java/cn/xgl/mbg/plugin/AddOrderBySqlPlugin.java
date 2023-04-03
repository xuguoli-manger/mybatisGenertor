package cn.xgl.mbg.plugin;

import cn.xgl.mbg.enums.PageArgsEnum;
import cn.xgl.mbg.util.XmlSqlIdRegister;
import cn.xgl.mbg.util.PropertyUtils;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.List;

/**
 * @Description:
 * @Author: xgl
 * @Date: 2023/3/29 10:24
 */
public class AddOrderBySqlPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> list) {
        return StringUtility.isTrue(context.getProperties().getProperty(PropertyUtils.ADD_ORDER_BY_SQL));
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        XmlElement orderBySql = new XmlElement("sql");
        orderBySql.addAttribute(new Attribute("id", XmlSqlIdRegister.SQL_ID_ORDER_BY_SQL));
        XmlElement ifSql = new XmlElement("if");
        orderBySql.addElement(ifSql);
        String orderBy = PageArgsEnum.ORDER_BY.getArgName();
        ifSql.addAttribute(new Attribute("test", orderBy +" != null"));
        ifSql.addElement(new TextElement("ORDER BY ${"+orderBy+"}"));
        document.getRootElement().addElement(orderBySql);
        return true;
    }
}
