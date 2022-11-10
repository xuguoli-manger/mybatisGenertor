package cn.xgl.mbg.config;

import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.ModelType;

import java.util.List;

/**
 * @Description: 上下文属性配置
 * @Author: xgl
 * @Date: 2022/10/26 10:49
 */
public class ContextOverride extends Context {

    //service 层 属性配置
    private JavaServiceGeneratorConfiguration javaServiceGeneratorConfiguration;

    //controller 层 属性配置
    private JavaControllerGeneratorConfiguration javaControllerGeneratorConfiguration;

    public ContextOverride(ModelType defaultModelType) {
        super(defaultModelType);
    }

    public JavaServiceGeneratorConfiguration getJavaServiceGeneratorConfiguration() {
        return javaServiceGeneratorConfiguration;
    }

    //设置service 层 属性
    public void setJavaServiceGeneratorConfiguration(JavaServiceGeneratorConfiguration javaServiceGeneratorConfiguration) {
        this.javaServiceGeneratorConfiguration = javaServiceGeneratorConfiguration;
    }

    public JavaControllerGeneratorConfiguration getJavaControllerGeneratorConfiguration() {
        return javaControllerGeneratorConfiguration;
    }

    //设置controller 层 属性
    public void setJavaControllerGeneratorConfiguration(JavaControllerGeneratorConfiguration javaControllerGeneratorConfiguration) {
        this.javaControllerGeneratorConfiguration = javaControllerGeneratorConfiguration;
    }

    @Override
    public void validate(List<String> errors) {
        if (this.javaServiceGeneratorConfiguration != null) {
            this.javaServiceGeneratorConfiguration.validate(errors, this.getId());
        }
        if (this.javaControllerGeneratorConfiguration != null) {
            this.javaControllerGeneratorConfiguration.validate(errors, this.getId());
        }
        super.validate(errors);
    }

}
