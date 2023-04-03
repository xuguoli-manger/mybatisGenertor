package cn.xgl.mbg.enums;

/**
 * @Description: 分页枚举类
 * @Author: xgl
 * @Date: 2023/3/24 17:43
 */
public enum PageMethodEnum {
    //全部查询的分页方法
    SELECT_ALL(MethodRemark.SELECT_ALL.getMethodName(),MethodRemark.SELECT_ALL_FOR_PAGE.getMethodName()),
    //条件查询的分页方法
    SELECT_BY_EXAMPLE(MethodRemark.SELECT_BY_EXAMPLE.getMethodName(),MethodRemark.SELECT_BY_EXAMPLE_FOR_PAGE.getMethodName()),
    //通过条件查询blob字段的分页方法
    SELECT_BY_EXAMPLE_WITH_BLOBS(MethodRemark.SELECT_BY_EXAMPLE_WITH_BLOBS.getMethodName(),MethodRemark.SELECT_BY_EXAMPLE_WITH_BLOBS_FOR_PAGE.getMethodName()),

    SELECT_FOR_LIST(MethodRemark.SELECT_FOR_LIST.getMethodName(),MethodRemark.SELECT_FOR_PAGE.getMethodName(),true),
    ;

    private String sourceMethodName;

    //分页方法名
    private String pageMethodName;

    /**
     * 参数是 do dto
     */
    private Boolean argModel = false;

    PageMethodEnum(String sourceMethodName, String pageMethodName) {
        this.sourceMethodName = sourceMethodName;
        this.pageMethodName = pageMethodName;
    }

    PageMethodEnum(String sourceMethodName, String pageMethodName, Boolean argModel) {
        this.sourceMethodName = sourceMethodName;
        this.pageMethodName = pageMethodName;
        this.argModel = argModel;
    }

    /**
     * 通过原方法获取枚举类 原方法 是 分页方法的蓝本
     * @param methodName 原方法名
     * @return 枚举类
     */
    public static PageMethodEnum getPageMethodEnumByMethodName(String methodName){
        PageMethodEnum[] values = PageMethodEnum.values();
        for (PageMethodEnum value : values) {
            if(value.getSourceMethodName().equals(methodName)){
                return value;
            }
        }
        return null;
    }

    public String getSourceMethodName() {
        return sourceMethodName;
    }

    public String getPageMethodName() {
        return pageMethodName;
    }

    public Boolean getArgModel() {
        return argModel;
    }
}
