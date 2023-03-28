package cn.xgl.mbg.plugin;

import cn.xgl.mbg.cache.CommArgs;
import cn.xgl.mbg.config.ContextOverride;
import cn.xgl.mbg.config.JavaControllerGeneratorConfiguration;
import cn.xgl.mbg.config.JavaServiceGeneratorConfiguration;
import cn.xgl.mbg.enums.MethodRemark;
import cn.xgl.mbg.util.PropertyUtils;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.internal.util.StringUtility;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description: 生成 controller层 接口
 * @Author: xgl
 * @Date: 2022/10/26 10:14
 */
public class ControllerPlugin extends PluginAdapter {

    Map<String,Object> remarks = new HashMap<>();//存放 表注释 列 注释 主键是表和列的Java名称

    private static Boolean SWAGGER_API_SWITCH = false;

    @Override
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
        if ((serviceGeneratorConfiguration = context.getJavaServiceGeneratorConfiguration()) == null || (controllerGeneratorConfiguration = context.getJavaControllerGeneratorConfiguration()) == null) {
            return null;
        }

        //设置swagger 开关
        setSwagger(introspectedTable);
        //设置字段注释
        setRemarks(introspectedTable);

        String targetPackage = controllerGeneratorConfiguration.getTargetPackage();//controller 包
        String targetProject = controllerGeneratorConfiguration.getTargetProject();//controller 项目地址
        String serviceTargetPackage = serviceGeneratorConfiguration.getTargetPackage();//service 包

        //增加Controller
        CompilationUnit addControllerClazz = addControllerClazz(introspectedTable, targetPackage, serviceTargetPackage);

        //生成Controller Java文件
        GeneratedJavaFile gjfControllerClazz = new GeneratedJavaFile(addControllerClazz, targetProject,
                this.context.getProperty("javaFileEncoding"), this.context.getJavaFormatter());

        List<GeneratedJavaFile> list = new ArrayList<>();
        list.add(gjfControllerClazz);
        CommArgs.addAllGeneratedJavaFile(list);
        return list;
    }

    //model设置swagger
    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        if(validate(topLevelClass,introspectedTable)){
            return true;
        }
        setSwagger(introspectedTable);
        setApiModelProperty(field, topLevelClass, introspectedColumn);
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if(validate(topLevelClass,introspectedTable)){
            return true;
        }
        setSwagger(introspectedTable);
        setApiModel(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if(validate(topLevelClass,introspectedTable)){
            return true;
        }
        setSwagger(introspectedTable);
        setApiModel(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if(validate(topLevelClass,introspectedTable)){
            return true;
        }
        setSwagger(introspectedTable);
        setApiModel(topLevelClass, introspectedTable);
        return true;
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

        //设置swagger api
        setApi(controllerClazz,introspectedTable);

        //service 接口 名称
        String serviceInterfaceName = builder.delete(0, builder.length())
                .append("I")
                .append(domainObjectName)
                .append("Service")
                .toString();

        //service 接口 引用名称 reference
        String serviceInterfaceReferenceName = builder.delete(0, builder.length())
                .append(Character.toLowerCase(domainObjectName.charAt(0)))
                .append(domainObjectName.substring(1))
                .append("Service")
                .toString();

        //service 接口 类型
        FullyQualifiedJavaType javaServiceInterfaceType = new FullyQualifiedJavaType(
                builder.delete(0, builder.length())
                .append(serviceTargetPackage)
                .append(".")
                .append("I")
                .append(domainObjectName)
                .append("Service")
                .toString());

        //引入service bean
        Field mapperField = new Field(serviceInterfaceReferenceName,javaServiceInterfaceType);
        mapperField.setVisibility(JavaVisibility.PRIVATE);
        mapperField.addAnnotation("@Autowired");
        controllerClazz.addField(mapperField);
        controllerClazz.addImportedType(javaServiceInterfaceType);
        controllerClazz
                .addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));

        //生成controller 方法
        this.additionalControllerMethods(introspectedTable, controllerClazz, serviceInterfaceReferenceName, serviceInterfaceName);

        return controllerClazz;
    }

    /**
     * controller 方法
     * @param introspectedTable 表信息
     * @param clazz service 实现类
     * @param serviceInterfaceReferenceName service接口 引用名称
     * @param serviceInterfaceName service接口 名称
     */
    protected void additionalControllerMethods(IntrospectedTable introspectedTable, TopLevelClass clazz,
                                                String serviceInterfaceReferenceName,String serviceInterfaceName) {

        /*if (this.notHasBLOBColumns(introspectedTable))
            return;*/

        CommArgs.getGeneratedJavaFiles(introspectedTable).stream().filter(file -> file.getCompilationUnit().getType().getShortName().equalsIgnoreCase(
                serviceInterfaceName)).map(GeneratedJavaFile::getCompilationUnit).forEach(
                compilationUnit -> ((Interface) compilationUnit).getMethods().forEach(m -> {
                    Method controllerMethod = this.additionalControllerLayerMethod(clazz, m, false);
                    controllerMethod.addAnnotation("@RequestMapping(\"/"+m.getName()+"\")");
                    controllerMethod.addBodyLine(this.generateBodyForControllerMethod(serviceInterfaceReferenceName, controllerMethod));

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
    private Method additionalControllerLayerMethod(CompilationUnit compilation, Method m, boolean isAbstract) {

        Method method = new Method(m.getName());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setAbstract(isAbstract);

        List<Parameter> parameters = m.getParameters();

        List<Parameter> params = new ArrayList<>();
        parameters.forEach(parameter -> {
            if(parameter.getName().equals("row")){//row 在mybatis generator 生成参数中 代表生成的对象 参数名  这里替换为首字母小写 eg User row  User user
                String name = parameter.getType().getShortName();
                params.add(new Parameter(parameter.getType(),Character.toLowerCase(name.charAt(0))+name.substring(1)));
            }else{
                params.add(parameter);
            }
        });

        //导入 参数 类型
        compilation.addImportedTypes(params.stream().flatMap(
                param -> Stream.of(new FullyQualifiedJavaType(param.getType().getFullyQualifiedNameWithoutTypeParameters()))).
                collect(Collectors.toSet()));

        //设置方法 参数
        method.getParameters().addAll(params.stream().peek(param ->
                setApiParam(compilation,param)).collect(Collectors.toList()));

        //方法设置返回值
        method.setReturnType(m.getReturnType().orElse(null));
        //导入返回值类型
        if(m.getReturnType().isPresent()){
            compilation.addImportedType(
                    new FullyQualifiedJavaType(m.getReturnType().get().getFullyQualifiedNameWithoutTypeParameters()));
        }
        setApiOperation(compilation,method);
        return method;
    }

    /**
     * 生成方法 body 方法体
     * @param serviceInterfaceName mapper 名称
     * @param m mapper 方法
     * @return 方法体
     */
    private String generateBodyForControllerMethod(String serviceInterfaceName, Method m) {
        StringBuilder sbf = new StringBuilder("return ");
        sbf.append(serviceInterfaceName).append(".").append(m.getName()).append("(");

        boolean singleParam = true;
        for (Parameter parameter : m.getParameters()) {

            if (singleParam) {
                singleParam = false;
            } else {
                sbf.append(", ");
            }
            sbf.append(parameter.getName());

        }

        sbf.append(");");
        return sbf.toString();
    }

    private boolean validate(TopLevelClass topLevelClass, IntrospectedTable introspectedTable){
        ContextOverride context = (ContextOverride) introspectedTable.getContext();//获取content 属性
        //controller 层 必须在service 层 存在才生成
        return context.getJavaControllerGeneratorConfiguration() == null;
    }

    private void setApiModel(TopLevelClass topLevelClass, IntrospectedTable introspectedTable){
        if(SWAGGER_API_SWITCH){
            topLevelClass.addAnnotation("@ApiModel(value =\""+Optional.ofNullable(introspectedTable.getRemarks()).orElse(introspectedTable.getFullyQualifiedTable().getDomainObjectName())+"\")");
            topLevelClass.addImportedType(new FullyQualifiedJavaType("io.swagger.annotations.ApiModel"));
        }
    }

    private void setApi(TopLevelClass topLevelClass, IntrospectedTable introspectedTable){
        if(SWAGGER_API_SWITCH){
            topLevelClass.addAnnotation("@Api(tags =\""+introspectedTable.getRemarks()+"\")");
            topLevelClass.addImportedType(new FullyQualifiedJavaType("io.swagger.annotations.Api"));
        }
    }

    private void setApiModelProperty(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn) {
        if(SWAGGER_API_SWITCH){
            field.addAnnotation("@ApiModelProperty(value =\""+Optional.ofNullable(introspectedColumn.getRemarks()).orElse(introspectedColumn.getJavaProperty())+"\")");
            topLevelClass.addImportedType(new FullyQualifiedJavaType("io.swagger.annotations.ApiModelProperty"));
        }
    }

    private void setApiOperation(CompilationUnit compilation, Method method) {
        if(SWAGGER_API_SWITCH){
            method.addAnnotation("@ApiOperation(value =\""+ MethodRemark.getRemarkByMethodName(method.getName())+"\")");
            compilation.addImportedType(new FullyQualifiedJavaType("io.swagger.annotations.ApiOperation"));
        }
    }

    private void setApiParam(CompilationUnit compilation, Parameter parameter) {
        if(SWAGGER_API_SWITCH){
            compilation.addImportedType(new FullyQualifiedJavaType("io.swagger.annotations.ApiParam"));
            parameter.addAnnotation("@ApiParam(name=\""+parameter.getName()+"\"," +
                    "value=\""+ Optional.ofNullable(remarks.get(parameter.getName())).orElse(parameter.getName())+"\")");
        }
    }

    private void setSwagger(IntrospectedTable introspectedTable) {
        ContextOverride context = (ContextOverride) introspectedTable.getContext();//获取content 属性
        JavaControllerGeneratorConfiguration controllerGeneratorConfiguration = context.getJavaControllerGeneratorConfiguration();
        SWAGGER_API_SWITCH = StringUtility.isTrue(controllerGeneratorConfiguration.getProperties().getProperty(PropertyUtils.SWAGGER_OPEN));
    }

    private void setRemarks(IntrospectedTable introspectedTable){
        if(SWAGGER_API_SWITCH){
            String name = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
            remarks.put(Character.toLowerCase(name.charAt(0))+name.substring(1),introspectedTable.getRemarks());//存储表注释
            introspectedTable.getPrimaryKeyColumns().forEach(introspectedColumn -> {//存储列注释
                remarks.put(introspectedColumn.getJavaProperty(),introspectedColumn.getRemarks());
            });
        }
    }

}
