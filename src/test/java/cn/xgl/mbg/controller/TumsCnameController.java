package cn.xgl.mbg.controller;

import cn.xgl.mbg.model.TumsCname;
import cn.xgl.mbg.service.ITumsCnameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tumsCname")
public class TumsCnameController {
    @Autowired
    private ITumsCnameService tumsCnameTumsCnameService;

    @RequestMapping("/deleteByPrimaryKey")
    public int deleteByPrimaryKey(String cname, String dname) {
        return tumsCnameTumsCnameService.deleteByPrimaryKey(cname, dname);
    }

    @RequestMapping("/insert")
    public int insert(TumsCname row) {
        return tumsCnameTumsCnameService.insert(row);
    }

    @RequestMapping("/insertSelective")
    public int insertSelective(TumsCname row) {
        return tumsCnameTumsCnameService.insertSelective(row);
    }

    @RequestMapping("/selectByPrimaryKey")
    public TumsCname selectByPrimaryKey(String cname, String dname) {
        return tumsCnameTumsCnameService.selectByPrimaryKey(cname, dname);
    }

    @RequestMapping("/updateByPrimaryKeySelective")
    public int updateByPrimaryKeySelective(TumsCname row) {
        return tumsCnameTumsCnameService.updateByPrimaryKeySelective(row);
    }

    @RequestMapping("/updateByPrimaryKey")
    public int updateByPrimaryKey(TumsCname row) {
        return tumsCnameTumsCnameService.updateByPrimaryKey(row);
    }
}