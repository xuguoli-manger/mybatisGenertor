package cn.xgl.mbg.config;

import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.ModelType;

import java.util.List;

/**
 * @Description:
 * @Author: xgl
 * @Date: 2022/10/26 10:49
 */
public class ContextOverride extends Context {

    private JavaServiceGeneratorConfiguration javaServiceGeneratorConfiguration;

    public ContextOverride(ModelType defaultModelType) {
        super(defaultModelType);
    }

    public JavaServiceGeneratorConfiguration getJavaServiceGeneratorConfiguration() {
        return javaServiceGeneratorConfiguration;
    }

    public void setJavaServiceGeneratorConfiguration(JavaServiceGeneratorConfiguration javaServiceGeneratorConfiguration) {
        this.javaServiceGeneratorConfiguration = javaServiceGeneratorConfiguration;
    }

    @Override
    public void validate(List<String> errors) {
        if (this.javaServiceGeneratorConfiguration != null) {
            this.javaServiceGeneratorConfiguration.validate(errors, this.getId());
        }
        super.validate(errors);
    }

}
