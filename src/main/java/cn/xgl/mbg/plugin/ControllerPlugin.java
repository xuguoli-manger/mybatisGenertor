package cn.xgl.mbg.plugin;

import cn.xgl.mbg.config.ContextOverride;
import cn.xgl.mbg.config.JavaControllerGeneratorConfiguration;
import cn.xgl.mbg.config.JavaServiceGeneratorConfiguration;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description: 生成 controller层 接口
 * @Author: xgl
 * @Date: 2022/10/26 10:14
 */
public class ControllerPlugin extends PluginAdapter {
    public boolean validate(List<String> list) {
        return true;
    }

    /**
     * 生成额外java文件
     */
    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {

        ContextOverride context = (ContextOverride) introspectedTable.getContext();//获取content 属性

        JavaControllerGeneratorConfiguration controllerGeneratorConfiguration;//controller 层 属性

        JavaServiceGeneratorConfiguration serviceGeneratorConfiguration;//service 层 属性

        //controller 层 必须在service 层 存在才生成
        if ((serviceGeneratorConfiguration = context.getJavaServiceGeneratorConfiguration()) == null || (controllerGeneratorConfiguration = context.getJavaControllerGeneratorConfiguration()) == null)
            return null;

        String targetPackage = controllerGeneratorConfiguration.getTargetPackage();//controller 包
        String targetProject = controllerGeneratorConfiguration.getTargetProject();//controller 项目地址
        String serviceTargetPackage = serviceGeneratorConfiguration.getTargetPackage();//service 包

        //增加service 实现类
        CompilationUnit addControllerClazz = addControllerClazz(introspectedTable, targetPackage, serviceTargetPackage);

        //生成Controller Java文件
        GeneratedJavaFile gjfControllerClazz = new GeneratedJavaFile(addControllerClazz, targetProject,
                this.context.getProperty("javaFileEncoding"), this.context.getJavaFormatter());

        List<GeneratedJavaFile> list = new ArrayList<>();
        list.add(gjfControllerClazz);
        return list;
    }



    /**
     * 生成controller 编译单元
     * @param introspectedTable 存储表信息
     * @param targetPackage 目标包
     * @param serviceTargetPackage service层目标包
     * @return controller 层实现类
     */
    protected CompilationUnit addControllerClazz(IntrospectedTable introspectedTable, String targetPackage, String serviceTargetPackage) {

        String domainObjectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();

        StringBuilder builder = new StringBuilder();

        //controller 实现类
        TopLevelClass controllerClazz = new TopLevelClass(
                builder.delete(0, builder.length())
                        .append(targetPackage)
                        .append(".")
                        .append(domainObjectName)
                        .append("Controller")
                        .toString()
        );

        controllerClazz.setVisibility(JavaVisibility.PUBLIC);
        controllerClazz.addAnnotation("@RestController");
        controllerClazz.addAnnotation("@RequestMapping(\"/"+Character.toLowerCase(domainObjectName.charAt(0))+domainObjectName.substring(1)+"\")");

        controllerClazz
                .addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
        controllerClazz.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RestController"));
        controllerClazz.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RequestMapping"));

        //mapper 接口 名称
        String mapperName = builder.delete(0, builder.length())
                .append(domainObjectName)
                .append("Mapper")
                .toString();

        //service 接口 名称
        String serviceInterfaceName = builder.delete(0, builder.length())
                .append(Character.toLowerCase(domainObjectName.charAt(0)))
                .append(domainObjectName.substring(1))
                .append(domainObjectName)
                .append("Service")
                .toString();

        //service 接口 类型
        FullyQualifiedJavaType JavaServiceInterfaceType = new FullyQualifiedJavaType(
                builder.delete(0, builder.length())
                .append(serviceTargetPackage)
                .append(".")
                .append("I")
                .append(domainObjectName)
                .append("Service")
                .toString());

        //引入service bean
        Field mapperField = new Field(serviceInterfaceName,JavaServiceInterfaceType);
        mapperField.setVisibility(JavaVisibility.PRIVATE);
        mapperField.addAnnotation("@Autowired");
        controllerClazz.addField(mapperField);
        controllerClazz.addImportedType(JavaServiceInterfaceType);
        controllerClazz
                .addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));

        //生成controller 方法
        this.additionalControllerMethods(introspectedTable, controllerClazz, serviceInterfaceName, mapperName);

        return controllerClazz;
    }

    /**
     * controller 方法
     * @param introspectedTable 表信息
     * @param clazz service 实现类
     * @param serviceInterfaceName mapper 名称
     */
    protected void additionalControllerMethods(IntrospectedTable introspectedTable, TopLevelClass clazz,
                                                String serviceInterfaceName,String mapperName) {

        /*if (this.notHasBLOBColumns(introspectedTable))
            return;*/

        introspectedTable.getGeneratedJavaFiles().stream().filter(file -> file.getCompilationUnit().getType().getShortName().equalsIgnoreCase(
                mapperName)).map(GeneratedJavaFile::getCompilationUnit).forEach(
                compilationUnit -> ((Interface) compilationUnit).getMethods().forEach(m -> {
                    m.setAbstract(false);
                    Method controllerMethod = this.additionalControllerLayerMethod(clazz, m);
                    controllerMethod.addAnnotation("@RequestMapping(\"/"+m.getName()+"\")");
                    controllerMethod.addBodyLine(this.generateBodyForControllerMethod(serviceInterfaceName, m));

                    clazz.addMethod(controllerMethod);
                }));
    }


    private boolean notHasBLOBColumns(IntrospectedTable introspectedTable) {
        return !introspectedTable.hasBLOBColumns();
    }

    /**
     * 生成 方法
     * @param compilation 类信息 编译单元
     * @param m mapper方法
     * @return 方法
     */
    private Method additionalControllerLayerMethod(CompilationUnit compilation, Method m) {

        Method method = new Method(m.getName());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setAbstract(m.isAbstract());

        List<Parameter> parameters = m.getParameters();

        //设置方法 参数 去除注解
        method.getParameters().addAll(parameters.stream().peek(param -> param.getAnnotations().clear()).collect(Collectors.toList()));

        //导入 参数 类型
        compilation.addImportedTypes(parameters.stream().peek(param -> param.getAnnotations().clear()).flatMap(
                parameter -> Stream.of(new FullyQualifiedJavaType(parameter.getType().getFullyQualifiedNameWithoutTypeParameters()))).
                collect(Collectors.toSet()));

        //方法设置返回值
        method.setReturnType(m.getReturnType().orElse(null));
        //导入返回值类型
        if(m.getReturnType().isPresent()){
            compilation.addImportedType(
                    new FullyQualifiedJavaType(m.getReturnType().get().getFullyQualifiedNameWithoutTypeParameters()));
        }
        return method;
    }

    /**
     * 生成方法 body 方法体
     * @param mapperName mapper 名称
     * @param m mapper 方法
     * @return 方法体
     */
    private String generateBodyForControllerMethod(String mapperName, Method m) {
        StringBuilder sbf = new StringBuilder("return ");
        sbf.append(mapperName).append(".").append(m.getName()).append("(");

        boolean singleParam = true;
        for (Parameter parameter : m.getParameters()) {

            if (singleParam)
                singleParam = false;
            else
                sbf.append(", ");
            sbf.append(parameter.getName());

        }

        sbf.append(");");
        return sbf.toString();
    }
}
