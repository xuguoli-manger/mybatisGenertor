package cn.xgl.mbg.enums;


/**
 * @Description:
 * @Author: xgl
 * @Date: 2023/3/31 17:08
 */
public enum  FullyQualifiedJavaTypeEnum {
    //
    PAGE_RETURN_TYPE("PageInfo","com.github.pagehelper.PageInfo"),
    COMM_RESULT_TYPE("ResponseResult","cn.fnii.idcsa.model.ResponseResult"),
    RESULT_CODE_UTIL_TYPE("ResponseResultUtils","cn.fnii.idcsa.utils.ResponseResultUtils"),
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
