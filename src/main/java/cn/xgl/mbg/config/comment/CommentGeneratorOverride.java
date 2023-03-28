package cn.xgl.mbg.config.comment;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * @Description:
 * @Author: xgl
 * @Date: 2023/3/28 11:21
 */
public interface CommentGeneratorOverride extends CommentGenerator {

    /**
     * Java Service实现类类的类注释
     * @param topLevelClass 类
     * @param introspectedTable
     */
    void addServiceClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable);

    /**
     * Java Controller类的类注释
     * @param topLevelClass
     * @param introspectedTable
     */
    void addControllerClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable);

    /**
     * java service 方法注释
     * @param method 方法
     * @param introspectedTable
     */
    void addGeneralServiceMethodComment(Method method, IntrospectedTable introspectedTable);

    /**
     * java controller 方法注释
     * @param method 方法
     * @param introspectedTable
     */
    void addGeneralControllerMethodComment(Method method, IntrospectedTable introspectedTable);

}
