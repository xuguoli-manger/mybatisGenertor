package cn.xgl.mbg.service.impl;

import cn.xgl.mbg.dao.TumsCnameMapper;
import cn.xgl.mbg.model.TumsCname;
import cn.xgl.mbg.model.TumsCnameExample;
import cn.xgl.mbg.service.TumsCnameService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TumsCnameServiceImpl implements TumsCnameService {
    @Autowired
    public TumsCnameMapper tumsCnameMapper;

    @Override
    public long countByExample(TumsCnameExample example) {
        return tumsCnameMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(TumsCnameExample example) {
        return tumsCnameMapper.deleteByExample(example);
    }

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
    public List<TumsCname> selectByExample(TumsCnameExample example) {
        return tumsCnameMapper.selectByExample(example);
    }

    @Override
    public TumsCname selectByPrimaryKey(String cname, String dname) {
        return tumsCnameMapper.selectByPrimaryKey(cname, dname);
    }

    @Override
    public int updateByExampleSelective(TumsCname row, TumsCnameExample example) {
        return tumsCnameMapper.updateByExampleSelective(row, example);
    }

    @Override
    public int updateByExample(TumsCname row, TumsCnameExample example) {
        return tumsCnameMapper.updateByExample(row, example);
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