package cn.xgl.mbg.config;

import org.mybatis.generator.config.TypedPropertyHolder;

import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * @Description:
 * @Author: xgl
 * @Date: 2022/10/26 10:34
 */
public class JavaServiceGeneratorConfiguration extends TypedPropertyHolder {
    private String targetPackage;
    private String targetProject;
    private String implementationPackage;

    public JavaServiceGeneratorConfiguration() {
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

    public String getImplementationPackage() {
        return implementationPackage;
    }

    public void setImplementationPackage(String implementationPackage) {
        this.implementationPackage = implementationPackage;
    }

    public void validate(List<String> errors, String contextId) {
        if (!stringHasValue(targetProject)) {
            errors.add(getString("ValidationError.102", contextId)); //$NON-NLS-1$
        }

        if (!stringHasValue(targetPackage)) {
            errors.add(getString("ValidationError.112", //$NON-NLS-1$
                    "javaServiceGenerator", contextId)); //$NON-NLS-1$
        }

        if (!stringHasValue(implementationPackage)) {
            errors.add(getString("ValidationError.120", contextId)); //$NON-NLS-1$
        }
    }
}
