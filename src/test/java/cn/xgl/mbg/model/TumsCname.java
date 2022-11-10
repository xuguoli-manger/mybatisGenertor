package cn.xgl.mbg.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* Created by Mybatis Generator 2022/11/08 16
*/
@Getter
@Setter
@ToString
public class TumsCname {
    private String cname;

    private String dname;

    private String cnamecp;

    private String dnamecp;

    private String region;

    private Integer reqs;

    private Double importrate;

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