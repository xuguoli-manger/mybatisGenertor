package cn.xgl.mbg.controller;

import cn.xgl.mbg.model.User;
import cn.xgl.mbg.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Api(tags ="用户信息表")
public class UserController {
    @Autowired
    private IUserService userUserService;

    @ApiOperation(value ="通过主键删除")
    @RequestMapping("/deleteByPrimaryKey")
    public int deleteByPrimaryKey(@ApiParam(name="userid",value="用户id") String userid) {
        return userUserService.deleteByPrimaryKey(userid);
    }

    @ApiOperation(value ="插入")
    @RequestMapping("/insert")
    public int insert(@ApiParam(name="user",value="用户信息表") User user) {
        return userUserService.insert(user);
    }

    @ApiOperation(value ="选择性插入")
    @RequestMapping("/insertSelective")
    public int insertSelective(@ApiParam(name="user",value="用户信息表") User user) {
        return userUserService.insertSelective(user);
    }

    @ApiOperation(value ="通过主键查询")
    @RequestMapping("/selectByPrimaryKey")
    public User selectByPrimaryKey(@ApiParam(name="userid",value="用户id") String userid) {
        return userUserService.selectByPrimaryKey(userid);
    }

    @ApiOperation(value ="通过主键修改部分数据")
    @RequestMapping("/updateByPrimaryKeySelective")
    public int updateByPrimaryKeySelective(@ApiParam(name="user",value="用户信息表") User user) {
        return userUserService.updateByPrimaryKeySelective(user);
    }

    @ApiOperation(value ="通过主键修改全部数据")
    @RequestMapping("/updateByPrimaryKey")
    public int updateByPrimaryKey(@ApiParam(name="user",value="用户信息表") User user) {
        return userUserService.updateByPrimaryKey(user);
    }
}