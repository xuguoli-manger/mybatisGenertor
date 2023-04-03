package cn.xgl.mbg.enums;


import cn.xgl.mbg.util.XmlSqlIdRegister;

/**
 * @author Administrator
 */

public enum MethodRemark {
    //
    DELETE_BY_PRIMARY_KEY("deleteByPrimaryKey","通过主键删除"),
    INSERT("insert","插入全部"),
    INSERT_SELECTIVE("insertSelective","选择性插入"),
    SELECT_ALL("selectAll","查询全部"),
    SELECT_ALL_FOR_PAGE("selectAllForPage","查询全部分页数据"),
    SELECT_BY_PRIMARY_KEY("selectByPrimaryKey","通过主键查询"),
    UPDATE_BY_PRIMARY_KEY_SELECTIVE("updateByPrimaryKeySelective","通过主键修改部分数据"),
    UPDATE_BY_PRIMARY_KEY("updateByPrimaryKey","通过主键修改全部数据"),
    UPDATE_BY_PRIMARY_KEY_WITH_BLOBS("updateByPrimaryKeyWithBLOBs","通过主键修改blob数据"),
    COUNT_BY_EXAMPLE("countByExample","根据条件查询条目数"),
    DELETE_BY_EXAMPLE("deleteByExample","根据条件删除"),
    SELECT_BY_EXAMPLE("selectByExample","根据条件查询"),
    SELECT_BY_EXAMPLE_FOR_PAGE("selectByExampleForpage","根据条件查询分页数据"),
    UPDATE_BY_EXAMPLE("updateByExample","根据条件修改"),
    UPDATE_BY_EXAMPLE_SELECTIVE("updateByExampleSelective","根据条件部分修改"),
    UPDATE_BY_EXAMPLE_WITH_BLOBS("updateByExampleWithBLOBs","根据条件修改blob字段"),
    SELECT_BY_EXAMPLE_WITH_BLOBS("selectByExampleWithBLOBs","根据条件查询blob"),
    SELECT_BY_EXAMPLE_WITH_BLOBS_FOR_PAGE("selectByExampleWithBLOBsForPage","根据条件查询blob分页数据"),
    SELECT_FOR_LIST(XmlSqlIdRegister.SQL_ID_SELECT_LIST,"根据条件查询"),
    SELECT_FOR_PAGE("selectForPage","根据条件查询分页数据"),
    ;

    private String remark;

    private String methodName;

    MethodRemark(String methodName,String remark) {
        this.remark = remark;
        this.methodName = methodName;
    }

    MethodRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public String getMethodName() {
        return methodName;
    }

    public static String getRemarkByMethodName(String methodName){
        MethodRemark[] values = MethodRemark.values();
        for (MethodRemark value : values) {
            if(value.getMethodName().equals(methodName)){
                return value.getRemark();
            }
        }
        return methodName;
    }

}
