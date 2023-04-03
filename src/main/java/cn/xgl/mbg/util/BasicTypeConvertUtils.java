package cn.xgl.mbg.util;

import cn.xgl.mbg.enums.BasicTypeEnum;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

/**
 * @Description:
 * @Author: xgl
 * @Date: 2023/4/3 14:38
 */
public class BasicTypeConvertUtils {

    public static FullyQualifiedJavaType getJavaType(FullyQualifiedJavaType javaType){
        String objectType = BasicTypeEnum.getObjectTypeByBasicType(javaType.getShortName());
        if(objectType != null){
            return new FullyQualifiedJavaType(objectType);
        }
        return javaType;
    }
}
