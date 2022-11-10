package cn.xgl.mbg.service.impl;

import cn.xgl.mbg.dao.TumsCnameMapper;
import cn.xgl.mbg.model.TumsCname;
import cn.xgl.mbg.service.ITumsCnameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TumsCnameServiceImpl implements ITumsCnameService {
    @Autowired
    public TumsCnameMapper tumsCnameMapper;

    @Override
    public int deleteByPrimaryKey(String cname, String dname) {
        return tumsCnameMapper.deleteByPrimaryKey(cname, dname);
    }

    @Override
    public int insert(TumsCname row) {
        return tumsCnameMapper.insert(row);
    }

    @Override
    public int insertSelective(TumsCname row) {
        return tumsCnameMapper.insertSelective(row);
    }

    @Override
    public TumsCname selectByPrimaryKey(String cname, String dname) {
        return tumsCnameMapper.selectByPrimaryKey(cname, dname);
    }

    @Override
    public int updateByPrimaryKeySelective(TumsCname row) {
        return tumsCnameMapper.updateByPrimaryKeySelective(row);
    }

    @Override
    public int updateByPrimaryKey(TumsCname row) {
        return tumsCnameMapper.updateByPrimaryKey(row);
    }
}