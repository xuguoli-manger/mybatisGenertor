package cn.xgl.mbg.plugin;

import cn.xgl.mbg.config.ContextOverride;
import cn.xgl.mbg.util.PropertyUtils;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.IntrospectedColumn;
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
 * @Date: 2023/3/28 16:57
 */
@Slf4j
public class WhereCasePlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> list) {
        return StringUtility.isTrue(context.getProperties().getProperty(PropertyUtils.ADD_WHERE_SQL));
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        boolean existDtoModel = StringUtility.isTrue(introspectedTable.getTableConfigurationProperty(PropertyUtils.ADD_DTO_MODEL));
        //获取content 属性
        ContextOverride context = (ContextOverride) introspectedTable.getContext();
        XmlElement sql = new XmlElement("sql");
        sql.addAttribute(new Attribute("id","whereSql"));
        context.getCommentGenerator().addComment(sql);
        XmlElement whereClause = new XmlElement("where");
        sql.addElement(whereClause);
        List<IntrospectedColumn> allColumns = introspectedTable.getAllColumns();
        String paramName = "";
        if(!existDtoModel){
            String domainObjectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
            paramName = Character.toLowerCase(domainObjectName.charAt(0))+domainObjectName.substring(1)+".";
        }
        for (IntrospectedColumn column : allColumns) {
            XmlElement ifClause = new XmlElement("if");
            whereClause.addElement(ifClause);
            String javaProperty = column.getJavaProperty();
            ifClause.addAttribute(new Attribute("test", paramName+javaProperty +" != null"));
            TextElement textElement = new TextElement("AND " + column.getActualColumnName() + " = " + "#{" + paramName+javaProperty + ",jdbcType=" + column.getJdbcTypeName() + "}");
            ifClause.addElement(textElement);
        }
        document.getRootElement().addElement(sql);
        return true;
    }

}
