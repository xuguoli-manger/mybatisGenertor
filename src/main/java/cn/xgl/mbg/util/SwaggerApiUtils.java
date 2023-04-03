package cn.xgl.mbg.util;

import cn.xgl.mbg.config.ContextOverride;
import cn.xgl.mbg.config.JavaControllerGeneratorConfiguration;
import cn.xgl.mbg.enums.MethodRemark;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @Description:
 * @Author: xgl
 * @Date: 2023/3/29 16:45
 */
public class SwaggerApiUtils {

    /**
     * 存放 表注释 列 注释 主键是表和列的Java名称
     */
    private static final Map<String,Object> REMARKS = new HashMap<>();

    private static Boolean SWAGGER_API_SWITCH = false;


    public static void setApiModel(TopLevelClass topLevelClass, IntrospectedTable introspectedTable){
        if(SWAGGER_API_SWITCH){
            topLevelClass.addAnnotation("@ApiModel(value =\""+Optional.ofNullable(introspectedTable.getRemarks()).orElse(introspectedTable.getFullyQualifiedTable().getDomainObjectName())+"\")");
            topLevelClass.addImportedType(new FullyQualifiedJavaType("io.swagger.annotations.ApiModel"));
        }
    }

    public static void setApi(TopLevelClass topLevelClass, IntrospectedTable introspectedTable){
        if(SWAGGER_API_SWITCH){
            topLevelClass.addAnnotation("@Api(tags =\""+introspectedTable.getRemarks()+"\")");
            topLevelClass.addImportedType(new FullyQualifiedJavaType("io.swagger.annotations.Api"));
        }
    }

    public static void setApiModelProperty(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn) {
        if(SWAGGER_API_SWITCH){
            field.addAnnotation("@ApiModelProperty(value =\""+ Optional.ofNullable(introspectedColumn.getRemarks()).orElse(introspectedColumn.getJavaProperty())+"\")");
            topLevelClass.addImportedType(new FullyQualifiedJavaType("io.swagger.annotations.ApiModelProperty"));
        }
    }

    public static void setApiOperation(CompilationUnit compilation, Method method) {
        if(SWAGGER_API_SWITCH){
            method.addAnnotation("@ApiOperation(value =\""+ MethodRemark.getRemarkByMethodName(method.getName())+"\")");
            compilation.addImportedType(new FullyQualifiedJavaType("io.swagger.annotations.ApiOperation"));
        }
    }

    public static void setApiParam(CompilationUnit compilation, Parameter parameter) {
        if(SWAGGER_API_SWITCH){
            compilation.addImportedType(new FullyQualifiedJavaType("io.swagger.annotations.ApiParam"));
            parameter.addAnnotation("@ApiParam(name=\""+parameter.getName()+"\"," +
                    "value=\""+ Optional.ofNullable(REMARKS.get(parameter.getName())).orElse(parameter.getName())+"\")");
        }
    }

    public static void setSwagger(IntrospectedTable introspectedTable) {
        //获取content 属性
        ContextOverride context = (ContextOverride) introspectedTable.getContext();
        JavaControllerGeneratorConfiguration controllerGeneratorConfiguration = context.getJavaControllerGeneratorConfiguration();
        SWAGGER_API_SWITCH = StringUtility.isTrue(controllerGeneratorConfiguration.getProperties().getProperty(PropertyUtils.SWAGGER_OPEN));
    }

    public static void setRemarks(IntrospectedTable introspectedTable){
        if(SWAGGER_API_SWITCH){
            String name = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
            //存储表注释
            REMARKS.put(Character.toLowerCase(name.charAt(0))+name.substring(1),introspectedTable.getRemarks());
            //存储列注释
            introspectedTable.getPrimaryKeyColumns().forEach(introspectedColumn -> {
                REMARKS.put(introspectedColumn.getJavaProperty(),introspectedColumn.getRemarks());
            });
        }
    }

    public static Map<String, Object> getRemarks() {
        return REMARKS;
    }

    public static Boolean getSwaggerApiSwitch() {
        return SWAGGER_API_SWITCH;
    }
}
