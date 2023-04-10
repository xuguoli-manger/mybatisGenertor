package cn.xgl.mbg.plugin;

import cn.xgl.mbg.enums.PageArgsEnum;
import cn.xgl.mbg.util.PropertyUtils;
import cn.xgl.mbg.util.SwaggerApiUtils;
import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.*;

/**
 * @Description:
 * @Author: xgl
 * @Date: 2023/3/29 16:14
 */
public class DtoModelPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        List<GeneratedJavaFile> list = new ArrayList<>();
        if(!StringUtility.isTrue(introspectedTable.getTableConfiguration().getProperty(PropertyUtils.ADD_DTO_MODEL))){
            return list;
        }
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = context.getJavaModelGeneratorConfiguration();
        String targetPackagePrefix = context.getProperties().getProperty(PropertyUtils.TARGET_PACKAGE_PREFIX);
        String targetPackage = targetPackagePrefix+"."+context.getProperties().getProperty(PropertyUtils.DTO_MODEL_TARGET_PACKAGE);
        String targetProject = javaModelGeneratorConfiguration.getTargetProject();

        //设置swagger 开关
        SwaggerApiUtils.setSwagger(introspectedTable);
        //设置字段注释
        SwaggerApiUtils.setRemarks(introspectedTable);

        //增加model dto
        CompilationUnit addModelClazz = addModelClazz(introspectedTable, targetPackage);

        //生成model Java文件
        GeneratedJavaFile gjfModelClazz = new GeneratedJavaFile(addModelClazz, targetProject,
                this.context.getProperty("javaFileEncoding"), this.context.getJavaFormatter());

        list.add(gjfModelClazz);
        return list;
    }

    private CompilationUnit addModelClazz(IntrospectedTable introspectedTable, String targetPackage) {
        CommentGenerator commentGenerator = context.getCommentGenerator();
        String domainObjectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(targetPackage+"."+domainObjectName+"Dto");
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);
        FullyQualifiedJavaType superClass = this.getSuperClass(introspectedTable);
        if (superClass != null) {
            topLevelClass.setSuperClass(superClass);
            topLevelClass.addImportedType(superClass);
        }
        commentGenerator.addModelClassComment(topLevelClass, introspectedTable);
        List<IntrospectedColumn> allColumns = introspectedTable.getAllColumns();
        for (IntrospectedColumn column : allColumns) {
            Field field = JavaBeansUtil.getJavaBeansField(column, this.context, introspectedTable);
            topLevelClass.addField(field);
            topLevelClass.addImportedType(field.getType());
            SwaggerApiUtils.setApiModelProperty(field,topLevelClass,column);
        }
        for (PageArgsEnum value : PageArgsEnum.values()) {
            IntrospectedColumn column = new IntrospectedColumn();
            column.setRemarks(value.getArgRemark());
            column.setFullyQualifiedJavaType(value.getArgType());
            column.setJavaProperty(value.getArgName());
            column.setRemarks(value.getArgRemark());
            Field field = JavaBeansUtil.getJavaBeansField(column, this.context, introspectedTable);
            topLevelClass.addField(field);
            topLevelClass.addImportedType(field.getType());
            SwaggerApiUtils.setApiModelProperty(field,topLevelClass,column);
        }
        SwaggerApiUtils.setApiModel(topLevelClass,introspectedTable);
        topLevelClass.addAnnotation("@Data");
        topLevelClass.addImportedType("lombok.Data");
        return topLevelClass;
    }

    private FullyQualifiedJavaType getSuperClass(IntrospectedTable introspectedTable) {
        String rootClass = this.getRootClass(introspectedTable);
        FullyQualifiedJavaType superClass;
        if (rootClass != null) {
            superClass = new FullyQualifiedJavaType(rootClass);
        } else {
            superClass = null;
        }

        return superClass;
    }

    public String getRootClass(IntrospectedTable introspectedTable) {
        String rootClass = introspectedTable.getTableConfigurationProperty("rootClass");
        if (rootClass == null) {
            Properties properties = this.context.getJavaModelGeneratorConfiguration().getProperties();
            rootClass = properties.getProperty("rootClass");
        }

        return rootClass;
    }


}
