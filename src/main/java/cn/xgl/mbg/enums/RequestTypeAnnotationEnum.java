package cn.xgl.mbg.enums;

/**
 * @Description:
 * @Author: xgl
 * @Date: 2023/4/3 14:14
 */
public enum  RequestTypeAnnotationEnum {
    //
    GET("get","@GetMapping","org.springframework.web.bind.annotation.GetMapping"),
    POST("post","@PostMapping","org.springframework.web.bind.annotation.PostMapping"),
    REQUEST("request","@RequestMapping","org.springframework.web.bind.annotation.RequestMapping"),
    ;

    private String requestType;

    private String annotationName;

    private String annotationType;

    RequestTypeAnnotationEnum(String requestType, String annotationName, String annotationType) {
        this.requestType = requestType;
        this.annotationName = annotationName;
        this.annotationType = annotationType;
    }

    public String getRequestType() {
        return requestType;
    }

    public String getAnnotationName() {
        return annotationName;
    }

    public String getAnnotationType() {
        return annotationType;
    }
}
