package cn.xgl.mbg.config.xml;

import cn.xgl.mbg.config.ContextOverride;
import cn.xgl.mbg.config.JavaServiceGeneratorConfiguration;
import org.mybatis.generator.config.*;
import org.mybatis.generator.config.xml.MyBatisGeneratorConfigurationParser;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.util.StringUtility;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Properties;

/**
 * @Description:
 * @Author: xgl
 * @Date: 2022/10/26 10:46
 */
public class MyBatisGeneratorConfigurationParserOverride extends MyBatisGeneratorConfigurationParser {

    public MyBatisGeneratorConfigurationParserOverride(Properties extraProperties) {
        super(extraProperties);
    }

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
        String targetPackage = attributes.getProperty("targetPackage");
        String targetProject = attributes.getProperty("targetProject");
        String implementationPackage = attributes.getProperty("implementationPackage");
        javaServiceGeneratorConfiguration.setTargetPackage(targetPackage);
        javaServiceGeneratorConfiguration.setTargetProject(targetProject);
        javaServiceGeneratorConfiguration.setImplementationPackage(implementationPackage);
        NodeList nodeList = node.getChildNodes();

        for(int i = 0; i < nodeList.getLength(); ++i) {
            Node childNode = nodeList.item(i);
            if (childNode.getNodeType() == 1 && "property".equals(childNode.getNodeName())) {
                this.parseProperty(javaServiceGeneratorConfiguration, childNode);
            }
        }

    }

    private void parseJavaClientGenerator(Context context, Node node) {
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);
        Properties attributes = this.parseAttributes(node);
        String type = attributes.getProperty("type");
        String targetPackage = attributes.getProperty("targetPackage");
        String targetProject = attributes.getProperty("targetProject");
        javaClientGeneratorConfiguration.setConfigurationType(type);
        javaClientGeneratorConfiguration.setTargetPackage(targetPackage);
        javaClientGeneratorConfiguration.setTargetProject(targetProject);
        NodeList nodeList = node.getChildNodes();

        for(int i = 0; i < nodeList.getLength(); ++i) {
            Node childNode = nodeList.item(i);
            if (childNode.getNodeType() == 1 && "property".equals(childNode.getNodeName())) {
                this.parseProperty(javaClientGeneratorConfiguration, childNode);
            }
        }

    }
}
