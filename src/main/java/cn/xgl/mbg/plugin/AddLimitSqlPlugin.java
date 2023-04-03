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
public class AddLimitSqlPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> list) {
        return StringUtility.isTrue(context.getProperties().getProperty(PropertyUtils.ADD_LIMIT_SQL));
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        XmlElement limitSql = new XmlElement("sql");
        limitSql.addAttribute(new Attribute("id", XmlSqlIdRegister.SQL_ID_LIMIT_SQL));
        XmlElement ifSql = new XmlElement("if");
        limitSql.addElement(ifSql);
        String offset = PageArgsEnum.OFFSET.getArgName();
        String rows = PageArgsEnum.ROW_COUNT.getArgName();
        ifSql.addAttribute(new Attribute("test",
                offset+" != null and " + rows+" != null"));
        ifSql.addElement(new TextElement("Limit "+ "#{"
                +offset+",jdbcType="+PageArgsEnum.OFFSET.getJdbcType()+"},#{"
                +rows+",jdbcType="+ PageArgsEnum.ROW_COUNT.getJdbcType() +"}"));
        document.getRootElement().addElement(limitSql);
        return true;
    }
}
