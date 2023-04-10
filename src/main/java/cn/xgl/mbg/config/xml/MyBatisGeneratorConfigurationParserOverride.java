package cn.xgl.mbg.config.xml;

import cn.xgl.mbg.config.ContextOverride;
import cn.xgl.mbg.config.JavaControllerGeneratorConfiguration;
import cn.xgl.mbg.config.JavaServiceGeneratorConfiguration;
import cn.xgl.mbg.util.PropertyUtils;
import org.mybatis.generator.config.*;
import org.mybatis.generator.config.xml.MyBatisGeneratorConfigurationParser;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.util.StringUtility;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Optional;
import java.util.Properties;

/**
 * @Description: 解析配置文件
 * @Author: xgl
 * @Date: 2022/10/26 10:46
 */
public class MyBatisGeneratorConfigurationParserOverride extends MyBatisGeneratorConfigurationParser {

    public MyBatisGeneratorConfigurationParserOverride(Properties extraProperties) {
        super(extraProperties);
    }

    @Override
    public Configuration parseConfiguration(Element rootNode) throws XMLParserException {
        Configuration configuration = new Configuration();
        NodeList nodeList = rootNode.getChildNodes();

        for(int i = 0; i < nodeList.getLength(); ++i) {
            Node childNode = nodeList.item(i);
            if (childNode.getNodeType() == 1) {
                if ("properties".equals(childNode.getNodeName())) {
                    this.parseProperties(childNode);
                } else if ("classPathEntry".equals(childNode.getNodeName())) {
                    this.parseClassPathEntry(configuration, childNode);
                } else if ("context".equals(childNode.getNodeName())) {
                    this.parseContext(configuration, childNode);
                }
            }
        }

        return configuration;
    }

    public void parseContext(Configuration configuration, Node node) {
        Properties attributes = this.parseAttributes(node);
        String defaultModelType = attributes.getProperty("defaultModelType");
        String targetRuntime = attributes.getProperty("targetRuntime");
        String introspectedColumnImpl = attributes.getProperty("introspectedColumnImpl");
        String id = attributes.getProperty("id");
        ModelType mt = defaultModelType == null ? null : ModelType.getModelType(defaultModelType);
        ContextOverride context = new ContextOverride(mt);
        context.setId(id);
        if (StringUtility.stringHasValue(introspectedColumnImpl)) {
            context.setIntrospectedColumnImpl(introspectedColumnImpl);
        }

        if (StringUtility.stringHasValue(targetRuntime)) {
            context.setTargetRuntime(targetRuntime);
        }

        configuration.addContext(context);
        NodeList nodeList = node.getChildNodes();

        for(int i = 0; i < nodeList.getLength(); ++i) {
            Node childNode = nodeList.item(i);
            if (childNode.getNodeType() == 1) {
                if ("property".equals(childNode.getNodeName())) {
                    this.parseProperty(context, childNode);
                } else if ("plugin".equals(childNode.getNodeName())) {
                    this.parsePlugin(context, childNode);
                } else if ("commentGenerator".equals(childNode.getNodeName())) {
                    this.parseCommentGenerator(context, childNode);
                } else if ("jdbcConnection".equals(childNode.getNodeName())) {
                    this.parseJdbcConnection(context, childNode);
                } else if ("connectionFactory".equals(childNode.getNodeName())) {
                    this.parseConnectionFactory(context, childNode);
                } else if ("javaModelGenerator".equals(childNode.getNodeName())) {
                    this.parseJavaModelGenerator(context, childNode);
                } else if ("javaTypeResolver".equals(childNode.getNodeName())) {
                    this.parseJavaTypeResolver(context, childNode);
                } else if ("sqlMapGenerator".equals(childNode.getNodeName())) {
                    this.parseSqlMapGenerator(context, childNode);
                } else if ("javaClientGenerator".equals(childNode.getNodeName())) {
                    this.parseJavaClientGenerator(context, childNode);
                } else if ("javaServiceGenerator".equals(childNode.getNodeName())) {
                    this.parseJavaServiceGenerator(context, childNode);
                } else if ("javaControllerGenerator".equals(childNode.getNodeName())) {
                    this.parseJavaControllerGenerator(context, childNode);
                } else if ("table".equals(childNode.getNodeName())) {
                    this.parseTable(context, childNode);
                }
            }
        }

    }

    private void parsePlugin(Context context, Node node) {
        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        context.addPluginConfiguration(pluginConfiguration);
        Properties attributes = this.parseAttributes(node);
        String type = attributes.getProperty("type");
        pluginConfiguration.setConfigurationType(type);
        NodeList nodeList = node.getChildNodes();

        for(int i = 0; i < nodeList.getLength(); ++i) {
            Node childNode = nodeList.item(i);
            if (childNode.getNodeType() == 1 && "property".equals(childNode.getNodeName())) {
                this.parseProperty(pluginConfiguration, childNode);
            }
        }

    }

    public void parseJavaServiceGenerator(Context context, Node node) {
        ContextOverride contextOverride = (ContextOverride) context; ////替换Context
        JavaServiceGeneratorConfiguration javaServiceGeneratorConfiguration = new JavaServiceGeneratorConfiguration();
        contextOverride.setJavaServiceGeneratorConfiguration(javaServiceGeneratorConfiguration);
        Properties attributes = this.parseAttributes(node);
        Properties properties = contextOverride.getProperties();//获取公共属性
        Object targetProjectPath = Optional.ofNullable(properties.get(PropertyUtils.TARGET_PROJECT_PATH)).orElse("");
        Object targetPackagePrefix = properties.get(PropertyUtils.TARGET_PACKAGE_PREFIX);
        if(StringUtils.isEmpty(targetPackagePrefix)){
            targetPackagePrefix = "";
        }else{
            targetPackagePrefix = targetPackagePrefix.toString().endsWith(".") ? targetPackagePrefix : targetPackagePrefix + ".";
        }
        String targetPackage = attributes.getProperty("targetPackage");
        String targetProject = attributes.getProperty("targetProject");
        String implementationPackage = attributes.getProperty("implementationPackage");
        if(StringUtils.isEmpty(implementationPackage)){
            implementationPackage = targetPackage + ".impl";
        }
        javaServiceGeneratorConfiguration.setTargetPackage(targetPackagePrefix+targetPackage);
        javaServiceGeneratorConfiguration.setTargetProject(targetProjectPath+targetProject);
        javaServiceGeneratorConfiguration.setImplementationPackage(targetPackagePrefix+implementationPackage);
        NodeList nodeList = node.getChildNodes();

        for(int i = 0; i < nodeList.getLength(); ++i) {
            Node childNode = nodeList.item(i);
            if (childNode.getNodeType() == 1 && "property".equals(childNode.getNodeName())) {
                this.parseProperty(javaServiceGeneratorConfiguration, childNode);
            }
        }

    }

    public void parseJavaControllerGenerator(Context context, Node node) {
        ContextOverride contextOverride = (ContextOverride) context; ////替换Context
        JavaControllerGeneratorConfiguration javaControllerGeneratorConfiguration = new JavaControllerGeneratorConfiguration();
        contextOverride.setJavaControllerGeneratorConfiguration(javaControllerGeneratorConfiguration);
        Properties attributes = this.parseAttributes(node);
        Properties properties = contextOverride.getProperties();
        Object targetProjectPath = Optional.ofNullable(properties.get(PropertyUtils.TARGET_PROJECT_PATH)).orElse("");
        Object targetPackagePrefix = properties.get(PropertyUtils.TARGET_PACKAGE_PREFIX);
        if(StringUtils.isEmpty(targetPackagePrefix)){
            targetPackagePrefix = "";
        }else{
            targetPackagePrefix = targetPackagePrefix.toString().endsWith(".") ? targetPackagePrefix : targetPackagePrefix + ".";
        }
        String targetPackage = attributes.getProperty("targetPackage");
        String targetProject = attributes.getProperty("targetProject");
        javaControllerGeneratorConfiguration.setTargetPackage(targetPackagePrefix+targetPackage);
        javaControllerGeneratorConfiguration.setTargetProject(targetProjectPath+targetProject);
        NodeList nodeList = node.getChildNodes();

        for(int i = 0; i < nodeList.getLength(); ++i) {
            Node childNode = nodeList.item(i);
            if (childNode.getNodeType() == 1 && "property".equals(childNode.getNodeName())) {
                this.parseProperty(javaControllerGeneratorConfiguration, childNode);
            }
        }

    }

    private void parseJavaClientGenerator(Context context, Node node) {
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);
        Properties properties = context.getProperties();
        Object targetProjectPath = Optional.ofNullable(properties.get(PropertyUtils.TARGET_PROJECT_PATH)).orElse("");
        Object targetPackagePrefix = properties.get(PropertyUtils.TARGET_PACKAGE_PREFIX);
        if(StringUtils.isEmpty(targetPackagePrefix)){
            targetPackagePrefix = "";
        }else{
            targetPackagePrefix = targetPackagePrefix.toString().endsWith(".") ? targetPackagePrefix : targetPackagePrefix + ".";
        }
        Properties attributes = this.parseAttributes(node);
        String type = attributes.getProperty("type");
        String targetPackage = attributes.getProperty("targetPackage");
        String targetProject = attributes.getProperty("targetProject");
        javaClientGeneratorConfiguration.setConfigurationType(type);
        javaClientGeneratorConfiguration.setTargetPackage(targetPackagePrefix+targetPackage);
        javaClientGeneratorConfiguration.setTargetProject(targetProjectPath+targetProject);
        NodeList nodeList = node.getChildNodes();

        for(int i = 0; i < nodeList.getLength(); ++i) {
            Node childNode = nodeList.item(i);
            if (childNode.getNodeType() == 1 && "property".equals(childNode.getNodeName())) {
                this.parseProperty(javaClientGeneratorConfiguration, childNode);
            }
        }

    }

    @Override
    protected void parseSqlMapGenerator(Context context, Node node) {
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);
        Properties properties = context.getProperties();
        Object targetProjectPath = Optional.ofNullable(properties.get(PropertyUtils.TARGET_PROJECT_PATH)).orElse("");
        Properties attributes = this.parseAttributes(node);
        String targetPackage = attributes.getProperty("targetPackage");
        String targetProject = attributes.getProperty("targetProject");
        sqlMapGeneratorConfiguration.setTargetPackage(targetPackage);
        sqlMapGeneratorConfiguration.setTargetProject(targetProjectPath+targetProject);
        NodeList nodeList = node.getChildNodes();

        for(int i = 0; i < nodeList.getLength(); ++i) {
            Node childNode = nodeList.item(i);
            if (childNode.getNodeType() == 1 && "property".equals(childNode.getNodeName())) {
                this.parseProperty(sqlMapGeneratorConfiguration, childNode);
            }
        }

    }

    @Override
    protected void parseJavaModelGenerator(Context context, Node node) {
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);
        Properties properties = context.getProperties();
        Object targetProjectPath = Optional.ofNullable(properties.get(PropertyUtils.TARGET_PROJECT_PATH)).orElse("");
        Object targetPackagePrefix = properties.get(PropertyUtils.TARGET_PACKAGE_PREFIX);
        if(StringUtils.isEmpty(targetPackagePrefix)){
            targetPackagePrefix = "";
        }else{
            targetPackagePrefix = targetPackagePrefix.toString().endsWith(".") ? targetPackagePrefix : targetPackagePrefix + ".";
        }
        Properties attributes = this.parseAttributes(node);
        String targetPackage = attributes.getProperty("targetPackage");
        String targetProject = attributes.getProperty("targetProject");
        javaModelGeneratorConfiguration.setTargetPackage(targetPackagePrefix+targetPackage);
        javaModelGeneratorConfiguration.setTargetProject(targetProjectPath+targetProject);
        NodeList nodeList = node.getChildNodes();

        for(int i = 0; i < nodeList.getLength(); ++i) {
            Node childNode = nodeList.item(i);
            if (childNode.getNodeType() == 1 && "property".equals(childNode.getNodeName())) {
                this.parseProperty(javaModelGeneratorConfiguration, childNode);
            }
        }

    }
}
