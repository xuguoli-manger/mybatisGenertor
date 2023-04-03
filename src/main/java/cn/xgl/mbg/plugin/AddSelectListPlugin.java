package cn.xgl.mbg.plugin;

import cn.xgl.mbg.enums.PageArgsEnum;
import cn.xgl.mbg.util.XmlSqlIdRegister;
import cn.xgl.mbg.util.PropertyUtils;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @Description:
 * @Author: xgl
 * @Date: 2023/3/29 9:45
 */
public class AddSelectListPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> list) {
        return StringUtility.isTrue(context.getProperties().getProperty(PropertyUtils.ADD_SELECT_LIST_METHOD))
                && StringUtility.isTrue(context.getProperties().getProperty(PropertyUtils.ADD_WHERE_SQL));
    }

    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        Method method = addMethod(interfaze, introspectedTable);
        interfaze.addMethod(method);
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        XmlElement selectForList = new XmlElement("select");
        selectForList.addAttribute(new Attribute("id", XmlSqlIdRegister.SQL_ID_SELECT_LIST));
        String targetPackage = context.getJavaModelGeneratorConfiguration().getTargetPackage();
        String domainObjectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        String addDtoModel = introspectedTable.getTableConfiguration().getProperty(PropertyUtils.ADD_DTO_MODEL);
        if(StringUtility.isTrue(addDtoModel)){
            targetPackage = targetPackage + ".dto";
            domainObjectName = domainObjectName + "Dto";
        }
        String parameterType = targetPackage + "."+ domainObjectName;
        selectForList.addAttribute(new Attribute("parameterType",parameterType));
        selectForList.addAttribute(new Attribute("resultMap","BaseResultMap"));
        selectForList.addElement(new TextElement("SELECT"));
        XmlElement includeBaseColumnList = new XmlElement("include");
        selectForList.addElement(includeBaseColumnList);
        includeBaseColumnList.addAttribute(new Attribute("refid","Base_Column_List"));
        String tableName = introspectedTable.getFullyQualifiedTable().getIntrospectedTableName();
        selectForList.addElement(new TextElement("FROM "+tableName));
        XmlElement includeWhereSql = new XmlElement("include");
        includeWhereSql.addAttribute(new Attribute("refid",XmlSqlIdRegister.SQL_ID_WHERE_SQL));
        selectForList.addElement(includeWhereSql);
        Properties properties = context.getProperties();
        if(StringUtility.isTrue(properties.getProperty(PropertyUtils.ADD_ORDER_BY_SQL))){
            XmlElement includeOrderBySql = new XmlElement("include");
            includeOrderBySql.addAttribute(new Attribute("refid",XmlSqlIdRegister.SQL_ID_ORDER_BY_SQL));
            selectForList.addElement(includeOrderBySql);
        }
        //不需要分页
        if(!StringUtility.isTrue(properties.getProperty(PropertyUtils.ADD_PAGE_METHOD)) &&
                StringUtility.isTrue(properties.getProperty(PropertyUtils.ADD_LIMIT_SQL))){
            XmlElement includeLimitSql = new XmlElement("include");
            includeLimitSql.addAttribute(new Attribute("refid",XmlSqlIdRegister.SQL_ID_LIMIT_SQL));
            selectForList.addElement(includeLimitSql);
        }
        document.getRootElement().addElement(selectForList);
        return true;
    }

    private Method addMethod(Interface interfaze,IntrospectedTable introspectedTable) {
        Method method = new Method(XmlSqlIdRegister.SQL_ID_SELECT_LIST);
        method.setAbstract(true);
        FullyQualifiedJavaType paramJavaType = introspectedTable.getRules().calculateAllFieldsClass();
        String name = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        String existDtoModel = introspectedTable.getTableConfiguration().getProperty(PropertyUtils.ADD_DTO_MODEL);
        String existPageMethod = context.getProperties().getProperty(PropertyUtils.ADD_PAGE_METHOD);
        List<Parameter> parameters = new ArrayList<>();
        if(StringUtility.isTrue(existDtoModel)){
            name = name+"Dto";
            paramJavaType = new FullyQualifiedJavaType(context.getJavaModelGeneratorConfiguration().getTargetPackage()+".dto."+name);
        }else if(StringUtility.isTrue(existPageMethod)){
            boolean existOrderBySql = StringUtility.isTrue(context.getProperties().getProperty(PropertyUtils.ADD_ORDER_BY_SQL));
            if(existOrderBySql){
                parameters.addAll(Arrays.asList(PageArgsEnum.getOrderByArgs()));

            }
            boolean existLimitBySql = StringUtility.isTrue(context.getProperties().getProperty(PropertyUtils.ADD_LIMIT_SQL));
            if(existLimitBySql && !StringUtility.isTrue(context.getProperties().getProperty(PropertyUtils.ADD_PAGE_METHOD))){
                parameters.addAll(Arrays.asList(PageArgsEnum.getLimitArgs()));
            }
        }
        name = Character.toLowerCase(name.charAt(0))+name.substring(1);
        parameters.add(0,new Parameter(paramJavaType,name));
        parameters.forEach(method::addParameter);
        interfaze.addImportedType(paramJavaType);
        //方法设置返回值
        FullyQualifiedJavaType returnJavaType = new FullyQualifiedJavaType("java.util.List");
        returnJavaType.addTypeArgument(introspectedTable.getRules().calculateAllFieldsClass());
        method.setReturnType(returnJavaType);
        interfaze.addImportedType(returnJavaType);
        return method;
    }
}
