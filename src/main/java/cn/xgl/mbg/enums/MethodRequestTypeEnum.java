package cn.xgl.mbg.enums;

import cn.xgl.mbg.util.XmlSqlIdRegister;

/**
 * @Description: controller 层 获取方法的请求类型 注解名称
 * @Author: xgl
 * @Date: 2023/3/24 17:43
 */
public enum MethodRequestTypeEnum {
    //
    DELETE_BY_PRIMARY_KEY("deleteByPrimaryKey","@GetMapping"),
    INSERT("insert","@PostMapping"),
    INSERT_SELECTIVE("insertSelective","@PostMapping"),
    SELECT_ALL("selectAll","@PostMapping"),
    SELECT_ALL_FOR_PAGE("selectAllForPage","@PostMapping"),
    SELECT_BY_PRIMARY_KEY("selectByPrimaryKey","@GetMapping"),
    UPDATE_BY_PRIMARY_KEY_SELECTIVE("updateByPrimaryKeySelective","@PostMapping"),
    UPDATE_BY_PRIMARY_KEY("updateByPrimaryKey","@PostMapping"),
    UPDATE_BY_PRIMARY_KEY_WITH_BLOBS("updateByPrimaryKeyWithBLOBs","@RequestMapping"),
    COUNT_BY_EXAMPLE("countByExample","@RequestMapping"),
    DELETE_BY_EXAMPLE("deleteByExample","@RequestMapping"),
    SELECT_BY_EXAMPLE("selectByExample","@RequestMapping"),
    SELECT_BY_EXAMPLE_FOR_PAGE("selectByExampleForpage","@RequestMapping"),
    UPDATE_BY_EXAMPLE("updateByExample","@RequestMapping"),
    UPDATE_BY_EXAMPLE_SELECTIVE("updateByExampleSelective","@RequestMapping"),
    UPDATE_BY_EXAMPLE_WITH_BLOBS("updateByExampleWithBLOBs","@RequestMapping"),
    SELECT_BY_EXAMPLE_WITH_BLOBS("selectByExampleWithBLOBs","@RequestMapping"),
    SELECT_BY_EXAMPLE_WITH_BLOBS_FOR_PAGE("selectByExampleWithBLOBsForPage","@RequestMapping"),
    SELECT_FOR_LIST(XmlSqlIdRegister.SQL_ID_SELECT_LIST,"@PostMapping"),
    SELECT_FOR_PAGE("selectForPage","@PostMapping"),
    ;
    /**
     * 方法名
     */
    private final String methodName;

    /**
     * 请求类型注解名
     */
    private final String requestTypeAnnotation;

    MethodRequestTypeEnum(String methodName, String requestTypeAnnotation) {
        this.methodName = methodName;
        this.requestTypeAnnotation = requestTypeAnnotation;
    }

    /**
     * 通过方法名获取请求类型
     * @param methodName 方法名
     * @return 注解
     */
    public static String getRequestTypeAnnotationByMethodName(String methodName){
        MethodRequestTypeEnum[] values = MethodRequestTypeEnum.values();
        for (MethodRequestTypeEnum value : values) {
           if(value.getMethodName().equals(methodName)){
               return value.getRequestTypeAnnotation();
           }
        }
        return "@RequestMapping";
    }

    public String getMethodName() {
        return methodName;
    }

    public String getRequestTypeAnnotation() {
        return requestTypeAnnotation;
    }
}
