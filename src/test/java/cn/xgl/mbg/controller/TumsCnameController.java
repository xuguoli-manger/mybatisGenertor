package cn.xgl.mbg.controller;

import cn.xgl.mbg.model.TumsCname;
import cn.xgl.mbg.service.ITumsCnameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags ="null")
@RequestMapping("/tumsCname")
public class TumsCnameController {
    @Autowired
    private ITumsCnameService tumsCnameTumsCnameService;

    @ApiOperation(value ="通过主键删除")
    @RequestMapping("/deleteByPrimaryKey")
    public int deleteByPrimaryKey(@ApiParam(name="cname",value="cname") String cname, @ApiParam(name="dname",value="dname") String dname) {
        return tumsCnameTumsCnameService.deleteByPrimaryKey(cname, dname);
    }

    @ApiOperation(value ="插入")
    @RequestMapping("/insert")
    public int insert(@ApiParam(name="row",value="row") TumsCname row) {
        return tumsCnameTumsCnameService.insert(row);
    }

    @ApiOperation(value ="选择性插入")
    @RequestMapping("/insertSelective")
    public int insertSelective(@ApiParam(name="row",value="row") TumsCname row) {
        return tumsCnameTumsCnameService.insertSelective(row);
    }

    @ApiOperation(value ="通过主键查询")
    @RequestMapping("/selectByPrimaryKey")
    public TumsCname selectByPrimaryKey(@ApiParam(name="cname",value="cname") String cname, @ApiParam(name="dname",value="dname") String dname) {
        return tumsCnameTumsCnameService.selectByPrimaryKey(cname, dname);
    }

    @ApiOperation(value ="通过主键修改部分数据")
    @RequestMapping("/updateByPrimaryKeySelective")
    public int updateByPrimaryKeySelective(@ApiParam(name="row",value="row") TumsCname row) {
        return tumsCnameTumsCnameService.updateByPrimaryKeySelective(row);
    }

    @ApiOperation(value ="通过主键修改全部数据")
    @RequestMapping("/updateByPrimaryKey")
    public int updateByPrimaryKey(@ApiParam(name="row",value="row") TumsCname row) {
        return tumsCnameTumsCnameService.updateByPrimaryKey(row);
    }
}