package cn.xgl.mbg.enums;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Parameter;

import java.util.List;

/**
 * @Description:
 * @Author: xgl
 * @Date: 2023/3/24 17:43
 */
public enum  PageMethodEnum {
    //全部查询的分页方法
    SELECTALL("selectAll","selectAll4page",new Parameter[]{new Parameter(new FullyQualifiedJavaType("java.lang.Integer"),"pageNo"),new Parameter(new FullyQualifiedJavaType("java.lang.Integer"),"pageSize")}),
    //条件查询的分页方法
    SELECTBYEXAMPLE("selectByExample","selectByExample4page",new Parameter[]{new Parameter(new FullyQualifiedJavaType("java.lang.Integer"),"pageNo"),new Parameter(new FullyQualifiedJavaType("java.lang.Integer"),"pageSize")}),
    //通过条件查询blob字段的分页方法
    SELECTBYEXAMPLEWITHBLOBS("selectByExampleWithBLOBs","selectByExampleWithBLOBs4page",new Parameter[]{new Parameter(new FullyQualifiedJavaType("java.lang.Integer"),"pageNo"),new Parameter(new FullyQualifiedJavaType("java.lang.Integer"),"pageSize")}),
    ;

    private String sourceMethodName;

    //分页方法名
    private String pageMethodName;

    private Parameter[] pageArgs;

    PageMethodEnum(String sourceMethodName, String pageMethodName, Parameter[] pageArgs) {
        this.sourceMethodName = sourceMethodName;
        this.pageMethodName = pageMethodName;
        this.pageArgs = pageArgs;
    }

    public static Parameter[] getPageArgByMethodName(String methodName){
        try {
            return PageMethodEnum.valueOf(methodName).pageArgs;
        } catch (IllegalArgumentException e) {
            return new Parameter[]{};
        }
    }

    public String getSourceMethodName() {
        return sourceMethodName;
    }

    public String getPageMethodName() {
        return pageMethodName;
    }

    public Parameter[] getPageArgs() {
        return pageArgs;
    }
}
