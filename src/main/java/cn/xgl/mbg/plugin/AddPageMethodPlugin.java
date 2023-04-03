package cn.xgl.mbg.plugin;

import cn.xgl.mbg.enums.PageArgsEnum;
import cn.xgl.mbg.enums.PageMethodEnum;
import cn.xgl.mbg.util.PropertyUtils;
import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.*;

/**
 * @Description:
 * @Author: xgl
 * @Date: 2023/3/29 9:13
 */
public class AddPageMethodPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return StringUtility.isTrue(context.getProperties().getProperty(PropertyUtils.ADD_PAGE_METHOD));
    }

    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        //是否开器addDtoModel 如果开启了addDtoModel 参数写入dto中 无需从新生成方法
        boolean addDtoModel = StringUtility.isTrue(introspectedTable.getTableConfigurationProperty(PropertyUtils.ADD_DTO_MODEL));
        if(addDtoModel){
            return true;
        }
        CommentGenerator commentGenerator = context.getCommentGenerator();
        List<Method> methods = interfaze.getMethods();
        List<Method> newMethods = new ArrayList<>(methods);
        PageMethodEnum[] pageMethods = PageMethodEnum.values();
        for (PageMethodEnum pageMethod : pageMethods) {
            for (Method method : newMethods) {
                if (method.getName().equalsIgnoreCase(pageMethod.getSourceMethodName())) {
                    Method mapperMethod = this.additionalMapperLayerMethod(interfaze, method);
                    commentGenerator.addGeneralMethodComment(method,introspectedTable);
                    if(mapperMethod == null){
                        continue;
                    }
                    commentGenerator.addGeneralMethodComment(mapperMethod,introspectedTable);
                    interfaze.addMethod(mapperMethod);
                }
            }
        }
        return true;
    }

    private Method additionalMapperLayerMethod(Interface interfaze, Method m) {

        PageMethodEnum pageMethodEnum = PageMethodEnum.getPageMethodEnumByMethodName(m.getName());
        if(pageMethodEnum == null){
            return null;
        }
        Method method = new Method(pageMethodEnum.getSourceMethodName());
        method.setAbstract(m.isAbstract());
        //设置参数
        List<Parameter> parameters = new ArrayList<>(Optional.ofNullable(m.getParameters()).orElse(new ArrayList<>()));
        //设置分页参数
        parameters.addAll(Arrays.asList(PageArgsEnum.getPageArgs()));
        for (Parameter parameter : parameters) {
            parameter.addAnnotation("@Param(\""+parameter.getName()+"\")");
            method.addParameter(parameter);
        }
        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param"));
        //方法设置返回值
        method.setReturnType(m.getReturnType().orElse(null));
        return method;
    }
}
