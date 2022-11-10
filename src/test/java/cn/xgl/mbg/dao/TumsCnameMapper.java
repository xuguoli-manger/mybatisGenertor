package cn.xgl.mbg.dao;

import cn.xgl.mbg.model.TumsCname;
import org.apache.ibatis.annotations.Param;

public interface TumsCnameMapper {
    int deleteByPrimaryKey(@Param("cname") String cname, @Param("dname") String dname);

    int insert(TumsCname row);

    int insertSelective(TumsCname row);

    TumsCname selectByPrimaryKey(@Param("cname") String cname, @Param("dname") String dname);

    int updateByPrimaryKeySelective(TumsCname row);

    int updateByPrimaryKey(TumsCname row);
}