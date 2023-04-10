package cn.xgl.mbg.plugin;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;


import java.text.SimpleDateFormat;
import java.util.*;

import cn.xgl.mbg.config.comment.CommentGeneratorOverride;
import cn.xgl.mbg.enums.MethodRemark;
import cn.xgl.mbg.util.PropertyUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * @Description:  mybatis generator 自定义comment生成器. 基于MBG 1.4.1
 * @author xgl
 * @Date 2023-03-28
 * @version V1.0
 */
public class MyCommentGenerator implements CommentGeneratorOverride {
    private static Map<String,String> columnRemark = new HashMap<>();

    /**
     * properties配置文件
     */
    private Properties properties;
    /**
     * properties配置文件
     */
    private Properties systemPro;

    /*
     * 父类时间
     */
    private boolean suppressDate;

    /**
     * 父类所有注释
     */
    private boolean suppressAllComments;

    /**
     *
     */
    private boolean addRemarkComments;

    /**
     * 当前时间
     */
    private String currentDateStr;

    /**
     * 作者
     */
    private String author;

    /*
    * 项目版本号
    */
    private String version;

    /**
     * controller 所有注释
     */
    private Boolean controllerAllComments;

    /**
     * controller 方法注释
     */
    private Boolean controllerMethodComments;

    /**
     * service 所有注释
     */
    private Boolean serviceAllComments;

    /**
     * service 方法注释
     */
    private Boolean serviceMethodComments;

    public MyCommentGenerator() {
        super();
        properties = new Properties();
        systemPro = System.getProperties();
        suppressDate = false;
        suppressAllComments = false;
        addRemarkComments = false;
        currentDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
        version = "0.0.1-SNAPSHOT";
        author = systemPro.getProperty("user.name");
        controllerAllComments = false;
        controllerMethodComments = false;
        serviceAllComments = false;
        serviceMethodComments = false;
    }

    /**
     * 从该配置中的任何属性添加此实例的属性CommentGenerator配置。 这个方法将在任何其他方法之前被调用。
     * @param properties
     */
    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);
        suppressDate = StringUtility.isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));
        suppressAllComments = StringUtility.isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
        this.addRemarkComments = StringUtility.isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS));
        String dateFormatString = properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_DATE_FORMAT);
        if (StringUtility.stringHasValue(dateFormatString)) {
            this.currentDateStr = new SimpleDateFormat(dateFormatString).format(new Date());
        }
        this.author = Optional.ofNullable(properties.getProperty(PropertyUtils.COMMENT_AUTHOR)).orElse(this.author);
        this.version = Optional.ofNullable(properties.getProperty(PropertyUtils.COMMENT_VERSION)).orElse(this.version);
        this.serviceAllComments = StringUtility.isTrue(properties.getProperty(PropertyUtils.COMMENT_SERVICE_ALL_COMMENTS));
        this.controllerAllComments = StringUtility.isTrue(properties.getProperty(PropertyUtils.COMMENT_CONTROLLER_ALL_COMMENTS));
        this.controllerMethodComments = StringUtility.isTrue(Optional
                .ofNullable(properties.getProperty(PropertyUtils.COMMENT_CONTROLLER_METHOD_COMMENTS))
                .orElse(this.controllerAllComments.toString()));
        this.serviceMethodComments = StringUtility.isTrue(Optional
                .ofNullable(properties.getProperty(PropertyUtils.COMMENT_SERVICE_METHOD_COMMENTS))
                .orElse(this.controllerAllComments.toString()));
    }

    /**
     * Java类的类注释
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        innerClass.addJavaDocLine("/**");
        innerClass.addJavaDocLine(" * @author : "+author);
        innerClass.addJavaDocLine(" * @version : "+version);
        innerClass.addJavaDocLine(" * @description : " + introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(" * @create : " + this.getDateString());
        innerClass.addJavaDocLine(" */");
    }

    /**
     * 为类添加注释
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        if (suppressAllComments) {
            return;
        }
        innerClass.addJavaDocLine("/**");
        innerClass.addJavaDocLine(" * @author : "+author);
        innerClass.addJavaDocLine(" * @version : "+version);
        innerClass.addJavaDocLine(" * @description : " + introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(" * @create : " + this.getDateString());
        innerClass.addJavaDocLine(" */");
    }


    /**
     * Mybatis的Mapper.xml文件里面的注释
     */
    @Override
    public void addComment(XmlElement xmlElement) {

    }

    /**
     * 为枚举添加注释
     */
    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        innerEnum.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerEnum.addJavaDocLine(sb.toString().replace("\n", " "));
        innerEnum.addJavaDocLine(" */");
    }

    /**
     * Java属性注释
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        field.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        field.addJavaDocLine(sb.toString().replace("\n", " "));
        field.addJavaDocLine(" */");
    }

    /**
     * 为字段添加注释
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        field.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedColumn.getRemarks());
        field.addJavaDocLine(sb.toString().replace("\n", " "));
        field.addJavaDocLine(" */");
        String domainObjectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        columnRemark.put(domainObjectName,introspectedTable.getRemarks());
        columnRemark.put(domainObjectName+field.getName(),introspectedColumn.getRemarks());
    }

    /**
     * xxxMapper接口和xxxExample类方法注解
     *
     * @param method
     * @param introspectedTable
     */
    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        if (!this.suppressAllComments) {
            String methodComment = MethodRemark.getRemarkByMethodName(method.getName());
            method.addJavaDocLine("/**");
            method.addJavaDocLine(" * " + methodComment);
            List<Parameter> parameters = method.getParameters();
            parameters.forEach(parameter -> method.addJavaDocLine(setParam(parameter,introspectedTable)));
            // 如果有返回类型，添加@return
            if (method.getReturnType().isPresent()) {
                method.addJavaDocLine(" * @return ");
            }
            method.addJavaDocLine(" */");
        }

    }

    private String setParam(Parameter parameter, IntrospectedTable introspectedTable) {
        String param = " * @param " + parameter.getName();
        String domainObjectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        if("row".equals(parameter.getName())){
            param = param + " " + columnRemark.get(domainObjectName);
        }else if(parameter.getName().endsWith("Dto")){
            param = param + " " + columnRemark.get(domainObjectName);
        }else {
            param = param + " " +columnRemark.get(domainObjectName+parameter.getName());
        }
        return param;
    }


    /**
     * 给getter方法加注释
     */
    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable,IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        method.addJavaDocLine("/**");
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString().replace("\n", " "));
        sb.setLength(0);
        sb.append(" * @return ");
        sb.append(introspectedColumn.getActualColumnName());
        sb.append(" ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString().replace("\n", " "));
        method.addJavaDocLine(" */");
    }

    /**
     * 给Java文件加注释，这个注释是在文件的顶部，也就是package上面。
     */
    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        if (suppressAllComments) {
            return;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        compilationUnit.addFileCommentLine("/*");
        compilationUnit.addFileCommentLine("*");
        compilationUnit.addFileCommentLine("* "+compilationUnit.getType().getShortName()+".java");
        compilationUnit.addFileCommentLine("* Copyright(C) 2023-2027 fnii");
        compilationUnit.addFileCommentLine("* @date "+sdf.format(new Date())+"");
        compilationUnit.addFileCommentLine("*/");
    }

    /**
     * 创建的数据表对应的类添加的注释
     *
     * @param topLevelClass
     * @param introspectedTable
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (!this.suppressAllComments) {

            topLevelClass.addJavaDocLine("/**");
            topLevelClass.addJavaDocLine(" * @author : "+author);
            topLevelClass.addJavaDocLine(" * @version : "+version);
            topLevelClass.addJavaDocLine(" * @description : " + introspectedTable.getFullyQualifiedTable());
            topLevelClass.addJavaDocLine(" * @create : " + this.getDateString());
            topLevelClass.addJavaDocLine(" */");
        }
    }


    /**
     * 为调用此方法作为根元素的第一个子节点添加注释。
     */
    @Override
    public void addRootComment(XmlElement arg0) {

    }


    /**
     * 给setter方法加注释
     */
    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable,IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        method.addJavaDocLine("/**");
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString().replace("\n", " "));
        Parameter parm = method.getParameters().get(0);
        sb.setLength(0);
        sb.append(" * @param ");
        sb.append(parm.getName());
        sb.append(" ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString().replace("\n", " "));
        method.addJavaDocLine(" */");
    }

    @Override
    public void addServiceClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if(this.suppressAllComments || this.serviceAllComments){
            return;
        }
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * @author : "+author);
        topLevelClass.addJavaDocLine(" * @version : "+version);
        topLevelClass.addJavaDocLine(" * @description : " + introspectedTable.getFullyQualifiedTable());
        topLevelClass.addJavaDocLine(" * @create : " + this.getDateString());
        topLevelClass.addJavaDocLine(" */");
    }

    @Override
    public void addControllerClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if(this.suppressAllComments || this.controllerAllComments){
            return;
        }
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * @author : "+author);
        topLevelClass.addJavaDocLine(" * @version : "+version);
        topLevelClass.addJavaDocLine(" * @description : " + introspectedTable.getFullyQualifiedTable());
        topLevelClass.addJavaDocLine(" * @create : " + this.getDateString());
        topLevelClass.addJavaDocLine(" */");
    }

    @Override
    public void addGeneralServiceMethodComment(Method method, IntrospectedTable introspectedTable) {
        if(this.suppressAllComments || this.serviceMethodComments){
            return;
        }
        String methodComment = MethodRemark.getRemarkByMethodName(method.getName());
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * " + methodComment);
        List<Parameter> parameters = method.getParameters();
        parameters.forEach(parameter -> method.addJavaDocLine(setParam(parameter,introspectedTable)));
        // 如果有返回类型，添加@return
        if (method.getReturnType().isPresent()) {
            method.addJavaDocLine(" * @return ");
        }
        method.addJavaDocLine(" */");
    }

    @Override
    public void addGeneralControllerMethodComment(Method method, IntrospectedTable introspectedTable) {
        if(this.suppressAllComments || this.controllerMethodComments){
            return;
        }
        String methodComment = MethodRemark.getRemarkByMethodName(method.getName());
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * " + methodComment);
        List<Parameter> parameters = method.getParameters();
        parameters.forEach(parameter -> method.addJavaDocLine(setParam(parameter,introspectedTable)));
        // 如果有返回类型，添加@return
        if (method.getReturnType().isPresent()) {
            method.addJavaDocLine(" * @return ");
        }
        method.addJavaDocLine(" */");
    }

    /**
     * 此方法返回格式化的日期字符串以包含在Javadoc标记中和XML注释。 如果您不想要日期，则可以返回null在这些文档元素中。
     * @return
     */
    protected String getDateString() {
        String result = null;
        if (!suppressDate) {
            result = currentDateStr;
        }
        return result;
    }

    /**
     *  此方法为其添加了自定义javadoc标签。
     */
    protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete) {
        javaElement.addJavaDocLine(" *");
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        if (markAsDoNotDelete) {
            sb.append(" do_not_delete_during_merge");
        }
        String s = getDateString();
        if (s != null) {
            sb.append(' ');
            sb.append(s);
        }
        javaElement.addJavaDocLine(sb.toString());
    }
}