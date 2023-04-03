package cn.xgl.mbg.enums;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.PrimitiveTypeWrapper;

/**
 * @Description: 用于生成在dtoModel中
 * @Author: xgl
 * @Date: 2023/3/30 9:23
 */
public enum  PageArgsEnum {
    //
    PAGE_NUM("pageNum", PrimitiveTypeWrapper.getIntegerInstance(),"INTEGER","分页页码")  ,
    PAGE_SIZE("pageSize",PrimitiveTypeWrapper.getIntegerInstance(),"INTEGER","分页每页条目数")  ,
    OFFSET("offset",PrimitiveTypeWrapper.getIntegerInstance(),"INTEGER","自定义limit,偏移量") ,
    ROW_COUNT("rowCount ",PrimitiveTypeWrapper.getIntegerInstance(),"INTEGER","自定义limit,查询最大行数")  ,
    ORDER_BY("orderBy",PrimitiveTypeWrapper.getStringInstance(),"VARCHAR","orderBy排序")  ;
    /**
     * 参数名称
     */
    private final String argName;
    /**
     * 参数类型
     */
    private final FullyQualifiedJavaType argType;
    /**
     * 参数类型
     */
    private final String jdbcType;
    /**
     * 参数注释
     */
    private final String argRemark;

    PageArgsEnum(String argName, FullyQualifiedJavaType argType, String jdbcType, String argRemark) {
        this.argName = argName;
        this.argType = argType;
        this.jdbcType = jdbcType;
        this.argRemark = argRemark;
    }

    public static Parameter[] getPageArgs(){
        Parameter[] parameters = new Parameter[2];
        parameters[0] = new Parameter(PageArgsEnum.PAGE_NUM.getArgType(), PageArgsEnum.PAGE_NUM.getArgName());
        parameters[1] = new Parameter(PageArgsEnum.PAGE_SIZE.getArgType(), PageArgsEnum.PAGE_SIZE.getArgName());
        return parameters;
    }

    public static Parameter[] getOrderByArgs(){
        Parameter[] parameters = new Parameter[1];
        parameters[0] = new Parameter(PageArgsEnum.ORDER_BY.getArgType(), PageArgsEnum.ORDER_BY.getArgName());
        return parameters;
    }

    public static Parameter[] getLimitArgs(){
        Parameter[] parameters = new Parameter[2];
        parameters[0] = new Parameter(PageArgsEnum.OFFSET.getArgType(), PageArgsEnum.OFFSET.getArgName());
        parameters[1] = new Parameter(PageArgsEnum.ROW_COUNT.getArgType(), PageArgsEnum.ROW_COUNT.getArgName());
        return parameters;
    }

    public String getArgName() {
        return argName;
    }

    public FullyQualifiedJavaType getArgType() {
        return argType;
    }

    public String getArgRemark() {
        return argRemark;
    }

    public String getJdbcType() {
        return jdbcType;
    }
}
