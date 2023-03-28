package cn.xgl.mbg.cache;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: xgl
 * @Date: 2023/3/27 13:53
 */
public class CommArgs {
    private static List<GeneratedJavaFile> generatedJavaFiles = new ArrayList<>();

    public static List<GeneratedJavaFile> getGeneratedJavaFiles() {
        return generatedJavaFiles;
    }

    public static List<GeneratedJavaFile> getGeneratedJavaFiles(IntrospectedTable introspectedTable) {
        if(CollectionUtils.isEmpty(getGeneratedJavaFiles())){
            generatedJavaFiles.addAll(introspectedTable.getGeneratedJavaFiles());
        }
        return generatedJavaFiles;
    }

    public static void setGeneratedJavaFiles(List<GeneratedJavaFile> generatedJavaFiles) {
        CommArgs.generatedJavaFiles = generatedJavaFiles;
    }

    public static boolean addGeneratedJavaFile(GeneratedJavaFile generatedJavaFile) {
        return generatedJavaFiles.add(generatedJavaFile);
    }

    public static boolean addAllGeneratedJavaFile(List<GeneratedJavaFile> generatedJavaFiles) {
        return CommArgs.generatedJavaFiles.addAll(generatedJavaFiles);
    }


}
