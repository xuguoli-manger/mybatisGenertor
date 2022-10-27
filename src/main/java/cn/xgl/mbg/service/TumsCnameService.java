package cn.xgl.mbg.service;

import cn.xgl.mbg.model.TumsCname;
import cn.xgl.mbg.model.TumsCnameExample;
import java.util.List;

public interface TumsCnameService {
    long countByExample(TumsCnameExample example);

    int deleteByExample(TumsCnameExample example);

    int deleteByPrimaryKey(String cname, String dname);

    int insert(TumsCname row);

    int insertSelective(TumsCname row);

    List<TumsCname> selectByExample(TumsCnameExample example);

    TumsCname selectByPrimaryKey(String cname, String dname);

    int updateByExampleSelective(TumsCname row, TumsCnameExample example);

    int updateByExample(TumsCname row, TumsCnameExample example);

    int updateByPrimaryKeySelective(TumsCname row);

    int updateByPrimaryKey(TumsCname row);
}