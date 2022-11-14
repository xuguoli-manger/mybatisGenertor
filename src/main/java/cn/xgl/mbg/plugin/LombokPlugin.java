package cn.xgl.mbg.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class LombokPlugin extends PluginAdapter {

    public LombokPlugin() {
    }

    public boolean validate(List<String> list) {
        return true;
    }

    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if(properties.isEmpty()){
            topLevelClass.addAnnotation("@Data");
            topLevelClass.addImportedType("lombok.Data");
        }else{
            if(properties.getProperty("Data") != null && properties.getProperty("Data").equals("true")){
                properties.remove("Getter");
                properties.remove("Setter");
                properties.remove("RequiredArgsConstructor");
                properties.remove("ToString");
                properties.remove("EqualsAndHashCode");
                properties.remove("Value");
            }
            properties.forEach((key, value) -> {
                if(value.equals("true")){
                    topLevelClass.addAnnotation("@"+key);
                }
            });
            topLevelClass.addImportedType("lombok.*");
        }
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine("* Created by Mybatis Generator " + this.date2Str(new Date()));
        topLevelClass.addJavaDocLine("*/");
        return true;
    }

    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        interfaze.addJavaDocLine("/**");
        interfaze.addJavaDocLine("* Created by Mybatis Generator " + this.date2Str(new Date()));
        interfaze.addJavaDocLine("*/");
        return true;
    }

    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }



    private String date2Str(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH");
        return sdf.format(date);
    }
}


