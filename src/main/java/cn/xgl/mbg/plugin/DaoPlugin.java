package cn.xgl.mbg.plugin;

import cn.xgl.mbg.cache.CommArgs;
import cn.xgl.mbg.config.ContextOverride;
import cn.xgl.mbg.enums.PageMethodEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.ArrayUtil;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @Description:
 * @Author: xgl
 * @Date: 2023/3/23 17:06
 */
@Slf4j
public class DaoPlugin extends PluginAdapter {

    private static final String PAGE_METHOD = "pageMethod";

    private static final String TRUE = "true";

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        log.info("执行mapper开始》》》》》》》》》》》》》》》》》》》》》》》");
        //获取content 属性
        ContextOverride context = (ContextOverride) introspectedTable.getContext();
        JavaClientGeneratorConfiguration configuration;

        if ((configuration = context.getJavaClientGeneratorConfiguration()) == null) {
            return null;
        }
        //dao 包
        String targetPackage = configuration.getTargetPackage();
        //dao 项目地址
        String targetProject = configuration.getTargetProject();

        //增加 mapper 接口
        CompilationUnit mapperInterface = addMapperInterface(introspectedTable, targetPackage);

        //生成mapper 接口 Java文件
        GeneratedJavaFile gjfServiceInterface = new GeneratedJavaFile(mapperInterface, targetProject,
                this.context.getProperty("javaFileEncoding"), this.context.getJavaFormatter());

        List<GeneratedJavaFile> list = new ArrayList<>();
        list.add(gjfServiceInterface);
        CommArgs.addAllGeneratedJavaFile(list);
        log.info("执行mapper结束《《《《《《《《《《《《《《《《《《《《《《《");
        return list;
    }

    private CompilationUnit addMapperInterface(IntrospectedTable introspectedTable, String targetPackage) {
        //获取表名
        String domainObjectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();

        //生成mapper 接口名称
        StringBuilder builder = new StringBuilder();

        //获取mapper 名称
        String mapperName = builder.delete(0, builder.length())
                .append(Character.toLowerCase(domainObjectName.charAt(0)))
                .append(domainObjectName.substring(1))
                .append("Mapper")
                .toString();

        CompilationUnit compilationUnit = introspectedTable.getGeneratedJavaFiles()
                .stream()
                .filter(file -> file.getCompilationUnit().getType().getShortName().equalsIgnoreCase(mapperName))
                .map(GeneratedJavaFile::getCompilationUnit)
                .findFirst().get();

        Interface mapperInterface = (Interface) compilationUnit;
        if(properties.get(PAGE_METHOD) != null && TRUE.equals(properties.get(PAGE_METHOD))){
            List<Method> methods = mapperInterface.getMethods();
            List<Method> newMethods = new ArrayList<>(methods);
            PageMethodEnum[] pageMethods = PageMethodEnum.values();
            for (PageMethodEnum pageMethod : pageMethods) {
                for (Method method : newMethods) {
                    if (method.getName().equalsIgnoreCase(pageMethod.getSourceMethodName())) {
                        mapperInterface.addMethod(this.additionalMapperLayerMethod(mapperInterface, method));
                    }
                }
            }
        }
        return mapperInterface;
    }

    private Method additionalMapperLayerMethod(CompilationUnit compilation, Method m) {
        PageMethodEnum pageMethodEnum = PageMethodEnum.valueOf(m.getName().toUpperCase());
        Method method = new Method(pageMethodEnum.getPageMethodName());
        method.setAbstract(m.isAbstract());
        //设置原来的参数
        List<Parameter> parameters = m.getParameters();
        if(!CollectionUtils.isEmpty(parameters)){
            for (Parameter parameter : parameters) {
                method.addParameter(parameter);
            }
        }
        //设置分页参数
        Parameter[] pageArgs = pageMethodEnum.getPageArgs();
        if(pageArgs.length > 0){
            for (Parameter pageArg : pageArgs) {
                method.addParameter(new Parameter(pageArg.getType(),pageArg.getName()));
            }
        }
        //方法设置返回值
        method.setReturnType(m.getReturnType().orElse(null));
        return method;
    }
}
