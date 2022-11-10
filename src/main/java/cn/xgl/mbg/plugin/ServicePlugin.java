package cn.xgl.mbg.plugin;

import cn.xgl.mbg.config.ContextOverride;
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
 * @Description:
 * @Author: xgl
 * @Date: 2022/10/26 10:14
 */
public class ServicePlugin extends PluginAdapter {
    public boolean validate(List<String> list) {
        return true;
    }

    /**
     * 生成额外java文件
     */
    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {

        ContextOverride context = (ContextOverride) introspectedTable.getContext();//获取content 属性

        JavaServiceGeneratorConfiguration serviceGeneratorConfiguration;//service 层 属性

        if ((serviceGeneratorConfiguration = context.getJavaServiceGeneratorConfiguration()) == null)
            return null;

        String targetPackage = serviceGeneratorConfiguration.getTargetPackage();//service 包
        String targetProject = serviceGeneratorConfiguration.getTargetProject();//service 项目地址
        String implementationPackage = serviceGeneratorConfiguration.getImplementationPackage();//service 实现类 包

        //增加 service 接口
        CompilationUnit addServiceInterface = addServiceInterface(introspectedTable, targetPackage);
        //增加service 实现类
        CompilationUnit addServiceImplClazz = addServiceImplClazz(introspectedTable, targetPackage,
                implementationPackage);

        //生成service 接口 Java文件
        GeneratedJavaFile gjfServiceInterface = new GeneratedJavaFile(addServiceInterface, targetProject,
                this.context.getProperty("javaFileEncoding"), this.context.getJavaFormatter());
        //生成service 实现类 Java文件
        GeneratedJavaFile gjfServiceImplClazz = new GeneratedJavaFile(addServiceImplClazz, targetProject,
                this.context.getProperty("javaFileEncoding"), this.context.getJavaFormatter());

        List<GeneratedJavaFile> list = new ArrayList<>();
        list.add(gjfServiceInterface);
        list.add(gjfServiceImplClazz);
        return list;
    }

    /**
     * 生成service 接口 编译单元
     * @param introspectedTable 存储表信息
     * @param targetPackage 目标包
     * @return service 层接口
     */
    protected CompilationUnit addServiceInterface(IntrospectedTable introspectedTable, String targetPackage) {

        String entityClazzType = introspectedTable.getBaseRecordType();//获取表 对应得 Java 类型 即Java对象名

        String entityExampleClazzType = introspectedTable.getExampleType();//获取mybatis 提供的 xxxExample对象名
        String domainObjectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();//获取表名

        /*JavaTypeResolver javaTypeResolver = new JavaTypeResolverDefaultImpl();

        FullyQualifiedJavaType calculateJavaType = javaTypeResolver
                .calculateJavaType(introspectedTable.getPrimaryKeyColumns().get(0));*/

        //生成service 接口名称
        StringBuilder builder = new StringBuilder();

        Interface serviceInterface = new Interface(
                builder.delete(0, builder.length())
                        .append(targetPackage)
                        .append(".")
                        .append("I")
                        .append(domainObjectName)
                        .append("Service")
                        .toString()
        );

        serviceInterface.setVisibility(JavaVisibility.PUBLIC);

        //导入mode层对象 这里已舍弃 直接在 method 层导入
        //FullyQualifiedJavaType baseServiceInstance = FullyQualifiedJavaTypeProxyFactory.getBaseServiceInstance();
        FullyQualifiedJavaType modelJavaType = new FullyQualifiedJavaType(entityClazzType);
        FullyQualifiedJavaType exampleJavaType = new FullyQualifiedJavaType(entityExampleClazzType);
        //serviceInterface.addImportedType(baseServiceInstance);
        //serviceInterface.addImportedType(modelJavaType);
        //serviceInterface.addImportedType(exampleJavaType);
        //serviceInterface.addFileCommentLine("/*** copyright (c) 2019 Marvis  ***/");

        //获取mapper 名称
        String mapperName = builder.delete(0, builder.length())
                .append(Character.toLowerCase(domainObjectName.charAt(0)))
                .append(domainObjectName.substring(1))
                .append("Mapper")
                .toString();


        this.additionalServiceMethods(introspectedTable, serviceInterface,mapperName);//生成service 层方法
        return serviceInterface;
    }

    /**
     * 生成service 实现类 编译单元
     * @param introspectedTable 存储表信息
     * @param targetPackage 目标包
     * @param implementationPackage 实现类包
     * @return service 层实现类
     */
    protected CompilationUnit addServiceImplClazz(IntrospectedTable introspectedTable, String targetPackage,
                                                  String implementationPackage) {

        String entityClazzType = introspectedTable.getBaseRecordType();
        String entityExampleClazzType = introspectedTable.getExampleType();

        String javaMapperType = introspectedTable.getMyBatis3JavaMapperType();

        String domainObjectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();

        /*JavaTypeResolver javaTypeResolver = new JavaTypeResolverDefaultImpl();
        FullyQualifiedJavaType calculateJavaType = javaTypeResolver
                .calculateJavaType(introspectedTable.getPrimaryKeyColumns().get(0));
*/
        StringBuilder builder = new StringBuilder();

        //service 接口 类型
        FullyQualifiedJavaType implInterfaceType = new FullyQualifiedJavaType(
                builder.delete(0, builder.length())
                        .append(targetPackage)
                        .append(".")
                        .append("I")
                        .append(domainObjectName)
                        .append("Service")
                        .toString()
        );

        //service 实现类
        TopLevelClass serviceImplClazz = new TopLevelClass(
                builder.delete(0, builder.length())
                        .append(implementationPackage)
                        .append(".")
                        .append(domainObjectName)
                        .append("ServiceImpl")
                        .toString()
        );

        serviceImplClazz.addSuperInterface(implInterfaceType);
        //serviceImplClazz.setSuperClass(superClazzType);
        serviceImplClazz.setVisibility(JavaVisibility.PUBLIC);
        serviceImplClazz.addAnnotation("@Service");

        //FullyQualifiedJavaType baseServiceInstance = FullyQualifiedJavaTypeProxyFactory.getBaseServiceImplInstance();

        //FullyQualifiedJavaType modelJavaType = new FullyQualifiedJavaType(entityClazzType);
        //FullyQualifiedJavaType exampleJavaType = new FullyQualifiedJavaType(entityExampleClazzType);
        //serviceImplClazz.addImportedType(baseServiceInstance);
        //serviceImplClazz.addImportedType(modelJavaType);
        //serviceImplClazz.addImportedType(exampleJavaType);
        serviceImplClazz
                .addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
        serviceImplClazz.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Service"));
        serviceImplClazz.addImportedType(implInterfaceType);

        //mapper 名称
        String mapperName = builder.delete(0, builder.length())
                .append(Character.toLowerCase(domainObjectName.charAt(0)))
                .append(domainObjectName.substring(1))
                .append("Mapper")
                .toString();

        //mapper 类型
        FullyQualifiedJavaType JavaMapperType = new FullyQualifiedJavaType(javaMapperType);

        //引入mapper bean
        Field mapperField = new Field(mapperName,JavaMapperType);
        mapperField.setVisibility(JavaVisibility.PUBLIC);
        /*mapperField.setType(JavaMapperType);// Mapper.java
        mapperField.setName(mapperName);*/
        mapperField.addAnnotation("@Autowired");
        serviceImplClazz.addField(mapperField);
        serviceImplClazz.addImportedType(JavaMapperType);
        serviceImplClazz
                .addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));

        //生成mapper 实现类方法
        this.additionalServiceImplMethods(introspectedTable, serviceImplClazz, mapperName);

        return serviceImplClazz;
    }

    /**
     * 生成mapper 接口方法
     * @param introspectedTable 表信息
     * @param serviceInterface service 接口
     * @param mapperName mapper 名称
     */
    protected void additionalServiceMethods(IntrospectedTable introspectedTable, Interface serviceInterface,
                                            String mapperName) {

        /*if (this.notHasBLOBColumns(introspectedTable))
            return;*/

        introspectedTable.getGeneratedJavaFiles().stream().filter(file -> file.getCompilationUnit().getType().getShortName().equalsIgnoreCase(
                mapperName)).map(GeneratedJavaFile::getCompilationUnit).forEach(
                compilationUnit -> ((Interface) compilationUnit).getMethods().forEach(
                        m -> {
                            m.setAbstract(true);//抽象类
                            serviceInterface.addMethod(this.additionalServiceLayerMethod(serviceInterface, m));
                        }));
    }

    /**
     * service 实现类 方法
     * @param introspectedTable 表信息
     * @param clazz service 实现类
     * @param mapperName mapper 名称
     */
    protected void additionalServiceImplMethods(IntrospectedTable introspectedTable, TopLevelClass clazz,
                                                String mapperName) {

        /*if (this.notHasBLOBColumns(introspectedTable))
            return;*/

        introspectedTable.getGeneratedJavaFiles().stream().filter(file -> file.getCompilationUnit().getType().getShortName().equalsIgnoreCase(
                mapperName)).map(GeneratedJavaFile::getCompilationUnit).forEach(
                compilationUnit -> ((Interface) compilationUnit).getMethods().forEach(m -> {
                    m.setAbstract(false);
                    Method serviceImplMethod = this.additionalServiceLayerMethod(clazz, m);
                    serviceImplMethod.addAnnotation("@Override");
                    serviceImplMethod.addBodyLine(this.generateBodyForServiceImplMethod(mapperName, m));

                    clazz.addMethod(serviceImplMethod);
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
    private Method additionalServiceLayerMethod(CompilationUnit compilation, Method m) {

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
    private String generateBodyForServiceImplMethod(String mapperName, Method m) {
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
