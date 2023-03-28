package cn.xgl.mbg.enums;


public enum MethodRemark {
    deleteByPrimaryKey("通过主键删除"),
    insert("插入全部"),
    insertSelective("选择性插入"),
    selectAll("查询全部"),
    selectAll4page("查询全部分页数据"),
    selectByPrimaryKey("通过主键查询"),
    updateByPrimaryKeySelective("通过主键修改部分数据"),
    updateByPrimaryKey("通过主键修改全部数据"),
    updateByPrimaryKeyWithBLOBs("通过主键修改blob数据"),
    countByExample("根据条件查询条目数"),
    deleteByExample("根据条件删除"),
    selectByExample("根据条件查询"),
    selectByExample4page("根据条件查询分页数据"),
    updateByExample("根据条件修改"),
    updateByExampleSelective("根据条件部分修改"),
    updateByExampleWithBLOBs("根据条件修改blob字段"),
    selectByExampleWithBLOBs("根据条件查询blob"),
    selectByExampleWithBLOBs4page("根据条件查询blob分页数据"),
    ;

    private String remark;

    MethodRemark( String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public static String getRemarkByMethodName(String methodName){
        try {
            return MethodRemark.valueOf(methodName).remark;
        } catch (IllegalArgumentException e) {
            return methodName;
        }
    }

}
