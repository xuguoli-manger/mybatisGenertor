package cn.xgl.mbg.enums;

import com.sun.org.apache.bcel.internal.generic.BasicType;

/**
 * @Description:
 * @Author: xgl
 * @Date: 2023/4/3 14:41
 */
public enum BasicTypeEnum {
    //
    INT("int","java.lang.Integer"),
    LONG("long","java.lang.Long"),
    DOUBLE("double","java.lang.Double"),
    FLOAT("float","java.lang.Float"),
    CHAR("char","java.lang.Character"),
    BYTE("byte","java.lang.Byte"),
    BOOLEAN("boolean","java.lang.Boolean"),
    SHORT("short","java.lang.Short"),
    ;
    /**
     * 基础类型
     */
    private final String basicType;

    /**
     * 保证类
     */
    private final String objectType;

    BasicTypeEnum(String basicType, String objectType) {
        this.basicType = basicType;
        this.objectType = objectType;
    }

    public static String getObjectTypeByBasicType(String basicType){
        BasicTypeEnum[] values = BasicTypeEnum.values();
        for (BasicTypeEnum value : values) {
            if(value.basicType.equals(basicType)){
                return value.objectType;
            }
        }
        return null;
    }
}
