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

        ContextOverride context = (ContextOverride) introspectedTable.getContext();

        JavaServiceGeneratorConfiguration serviceGeneratorConfiguration;

        if ((serviceGeneratorConfiguration = context.getJavaServiceGeneratorConfiguration()) == null)
            return null;

        String targetPackage = serviceGeneratorConfiguration.getTargetPackage();
        String targetProject = serviceGeneratorConfiguration.getTargetProject();
        String implementationPackage = serviceGeneratorConfiguration.getImplementationPackage();

        CompilationUnit addServiceInterface = addServiceInterface(introspectedTable, targetPackage);
        CompilationUnit addServiceImplClazz = addServiceImplClazz(introspectedTable, targetPackage,
                implementationPackage);

        GeneratedJavaFile gjfServiceInterface = new GeneratedJavaFile(addServiceInterface, targetProject,
                this.context.getProperty("javaFileEncoding"), this.context.getJavaFormatter());
        GeneratedJavaFile gjfServiceImplClazz = new GeneratedJavaFile(addServiceImplClazz, targetProject,
                this.context.getProperty("javaFileEncoding"), this.context.getJavaFormatter());

        List<GeneratedJavaFile> list = new ArrayList<>();
        list.add(gjfServiceInterface);
        list.add(gjfServiceImplClazz);
        return list;
    }

    protected CompilationUnit addServiceInterface(IntrospectedTable introspectedTable, String targetPackage) {

        String entityClazzType = introspectedTable.getBaseRecordType();

        String entityExampleClazzType = introspectedTable.getExampleType();
        String domainObjectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();

        /*JavaTypeResolver javaTypeResolver = new JavaTypeResolverDefaultImpl();

        FullyQualifiedJavaType calculateJavaType = javaTypeResolver
                .calculateJavaType(introspectedTable.getPrimaryKeyColumns().get(0));*/

        StringBuilder builder = new StringBuilder();

        /*FullyQualifiedJavaType superInterfaceType = new FullyQualifiedJavaType(

                builder.append("BaseService<")
                        .append(entityClazzType)
                        .append(",")
                        .append(entityExampleClazzType)
                        .append(",")
                        .append(calculateJavaType.getShortName()).append(">").toString());*/

        Interface serviceInterface = new Interface(
                builder.delete(0, builder.length())
                        .append(targetPackage)
                        .append(".")
                        .append(domainObjectName)
                        .append("Service")
                        .toString()
        );

        //serviceInterface.addSuperInterface(superInterfaceType);
        serviceInterface.setVisibility(JavaVisibility.PUBLIC);

        //FullyQualifiedJavaType baseServiceInstance = FullyQualifiedJavaTypeProxyFactory.getBaseServiceInstance();
        FullyQualifiedJavaType modelJavaType = new FullyQualifiedJavaType(entityClazzType);
        FullyQualifiedJavaType exampleJavaType = new FullyQualifiedJavaType(entityExampleClazzType);
        //serviceInterface.addImportedType(baseServiceInstance);
        //serviceInterface.addImportedType(modelJavaType);
        //serviceInterface.addImportedType(exampleJavaType);
        //serviceInterface.addFileCommentLine("/*** copyright (c) 2019 Marvis  ***/");

        String mapperName = builder.delete(0, builder.length())
                .append(Character.toLowerCase(domainObjectName.charAt(0)))
                .append(domainObjectName.substring(1))
                .append("Mapper")
                .toString();


        this.additionalServiceMethods(introspectedTable, serviceInterface,mapperName);
        return serviceInterface;
    }

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

       /* FullyQualifiedJavaType superClazzType = new FullyQualifiedJavaType(

                builder.append("BaseServiceImpl<")
                        .append(entityClazzType)
                        .append(",")
                        .append(entityExampleClazzType)
                        .append(",")
                        .append(calculateJavaType.getShortName()).append(">")
                        .toString()
        );*/

        FullyQualifiedJavaType implInterfaceType = new FullyQualifiedJavaType(

                builder.delete(0, builder.length())
                        .append(targetPackage)
                        .append(".")
                        .append(domainObjectName)
                        .append("Service")
                        .toString()
        );

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

        FullyQualifiedJavaType modelJavaType = new FullyQualifiedJavaType(entityClazzType);
        FullyQualifiedJavaType exampleJavaType = new FullyQualifiedJavaType(entityExampleClazzType);
        serviceImplClazz
                .addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
        serviceImplClazz.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Service"));
        //serviceImplClazz.addImportedType(baseServiceInstance);
        //serviceImplClazz.addImportedType(modelJavaType);
        //serviceImplClazz.addImportedType(exampleJavaType);
        serviceImplClazz.addImportedType(implInterfaceType);

        String mapperName = builder.delete(0, builder.length())
                .append(Character.toLowerCase(domainObjectName.charAt(0)))
                .append(domainObjectName.substring(1))
                .append("Mapper")
                .toString();

      /*  introspectedTable.getGeneratedJavaFiles().stream().filter(file -> file.getCompilationUnit().getType().getShortName().equalsIgnoreCase(
                mapperName)).map(GeneratedJavaFile::getCompilationUnit).forEach(
                compilationUnit -> ((Interface) compilationUnit).getImportedTypes().forEach(fullyQualifiedJavaType -> {
                    fullyQualifiedJavaType.
                }));*/

        FullyQualifiedJavaType JavaMapperType = new FullyQualifiedJavaType(javaMapperType);

        Field mapperField = new Field(mapperName,JavaMapperType);
        mapperField.setVisibility(JavaVisibility.PUBLIC);
        /*mapperField.setType(JavaMapperType);// Mapper.java
        mapperField.setName(mapperName);*/
        mapperField.addAnnotation("@Autowired");
        serviceImplClazz.addField(mapperField);
        serviceImplClazz.addImportedType(JavaMapperType);

        /*Method mapperMethod = new Method("setMapper");
        mapperMethod.setVisibility(JavaVisibility.PUBLIC);
        //mapperMethod.setName("setMapper");
        mapperMethod.addBodyLine("super.setMapper(" + mapperName + ");");
        mapperMethod.addAnnotation("@Autowired");

        serviceImplClazz.addMethod(mapperMethod);*/
        //serviceImplClazz.addFileCommentLine("/*** copyright (c) 2019 Marvis  ***/");

        serviceImplClazz
                .addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));

        this.additionalServiceImplMethods(introspectedTable, serviceImplClazz, mapperName);

        return serviceImplClazz;
    }

    protected void additionalServiceMethods(IntrospectedTable introspectedTable, Interface serviceInterface,
                                            String mapperName) {

        /*if (this.notHasBLOBColumns(introspectedTable))
            return;*/

        introspectedTable.getGeneratedJavaFiles().stream().filter(file -> file.getCompilationUnit().getType().getShortName().equalsIgnoreCase(
                mapperName)).map(GeneratedJavaFile::getCompilationUnit).forEach(
                compilationUnit -> ((Interface) compilationUnit).getMethods().forEach(
                        m -> {
                            m.setAbstract(true);
                            serviceInterface.addMethod(this.additionalServiceLayerMethod(serviceInterface, m));
                        }));
    }

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

    private Method additionalServiceLayerMethod(CompilationUnit compilation, Method m) {

        Method method = new Method(m.getName());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setAbstract(m.isAbstract());

        List<Parameter> parameters = m.getParameters();

        method.getParameters().addAll(parameters.stream().peek(param -> param.getAnnotations().clear()).collect(Collectors.toList()));

        compilation.addImportedTypes(parameters.stream().peek(param -> param.getAnnotations().clear()).flatMap(
                parameter -> Stream.of(new FullyQualifiedJavaType(parameter.getType().getFullyQualifiedNameWithoutTypeParameters()))).
                collect(Collectors.toSet()));

        method.setReturnType(m.getReturnType().orElse(null));
        if(m.getReturnType().isPresent()){
            compilation.addImportedType(
                    new FullyQualifiedJavaType(m.getReturnType().get().getFullyQualifiedNameWithoutTypeParameters()));
        }
        return method;
    }

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
