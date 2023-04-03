package cn.xgl.mbg.enums;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

/**
 * @Description:
 * @Author: xgl
 * @Date: 2023/3/31 17:08
 */
public enum  FullyQualifiedJavaTypeEnum {
    //
    PAGE_RETURN_TYPE("PageInfo","com.github.pagehelper.PageInfo"),
    COMM_RESULT_TYPE("CommResult","cn.fnii.cloud.model.CommResult"),
    RESULT_CODE_UTIL_TYPE("ResultCodeUtil","cn.fnii.cloud.utils.ResultCodeUtil"),
    ;

    private String javaTypeName;

    private String javaType;

    FullyQualifiedJavaTypeEnum(String javaTypeName, String javaType) {
        this.javaTypeName = javaTypeName;
        this.javaType = javaType;
    }

    public String getJavaTypeName() {
        return javaTypeName;
    }

    public String getJavaType() {
        return javaType;
    }
}
