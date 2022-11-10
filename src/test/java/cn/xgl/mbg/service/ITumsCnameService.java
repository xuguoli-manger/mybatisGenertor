package cn.xgl.mbg.service;

import cn.xgl.mbg.model.TumsCname;

public interface ITumsCnameService {
    int deleteByPrimaryKey(String cname, String dname);

    int insert(TumsCname row);

    int insertSelective(TumsCname row);

    TumsCname selectByPrimaryKey(String cname, String dname);

    int updateByPrimaryKeySelective(TumsCname row);

    int updateByPrimaryKey(TumsCname row);
}