package cn.xgl.mbg.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* Created by Mybatis Generator 2022/11/11 18
*/
@Getter
@Setter
@ToString
@ApiModel(value ="null")
public class TumsCname {
    @ApiModelProperty(value ="null")
    private String cname;

    @ApiModelProperty(value ="null")
    private String dname;

    @ApiModelProperty(value ="null")
    private String cnamecp;

    @ApiModelProperty(value ="null")
    private String dnamecp;

    @ApiModelProperty(value ="null")
    private String region;

    @ApiModelProperty(value ="null")
    private Integer reqs;

    @ApiModelProperty(value ="null")
    private Double importrate;

    @ApiModelProperty(value ="null")
    private Double selfrate;

    public TumsCname(String cname, String dname, String cnamecp, String dnamecp, String region, Integer reqs, Double importrate, Double selfrate) {
        this.cname = cname;
        this.dname = dname;
        this.cnamecp = cnamecp;
        this.dnamecp = dnamecp;
        this.region = region;
        this.reqs = reqs;
        this.importrate = importrate;
        this.selfrate = selfrate;
    }

    public TumsCname() {
        super();
    }
}