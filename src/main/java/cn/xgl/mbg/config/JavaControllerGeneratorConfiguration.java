package cn.xgl.mbg.config;

import org.mybatis.generator.config.TypedPropertyHolder;

import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * @Description: controller 层 属性配置
 * @Author: xgl
 * @Date: 2022/10/26 10:34
 */
public class JavaControllerGeneratorConfiguration extends TypedPropertyHolder {
    private String targetPackage;
    private String targetProject;

    public JavaControllerGeneratorConfiguration() {
        super();
    }

    public String getTargetProject() {
        return targetProject;
    }

    public void setTargetProject(String targetProject) {
        this.targetProject = targetProject;
    }

    public String getTargetPackage() {
        return targetPackage;
    }

    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
    }

    public void validate(List<String> errors, String contextId) {
        if (!stringHasValue(targetProject)) {
            errors.add(getString("ValidationError.202", contextId)); //$NON-NLS-1$
        }

        if (!stringHasValue(targetPackage)) {
            errors.add(getString("ValidationError.212", //$NON-NLS-1$
                    "javaServiceGenerator", contextId)); //$NON-NLS-1$
        }
    }
}
