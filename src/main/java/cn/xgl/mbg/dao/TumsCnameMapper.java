package cn.xgl.mbg.dao;

import cn.xgl.mbg.model.TumsCname;
import cn.xgl.mbg.model.TumsCnameExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TumsCnameMapper {
    long countByExample(TumsCnameExample example);

    int deleteByExample(TumsCnameExample example);

    int deleteByPrimaryKey(@Param("cname") String cname, @Param("dname") String dname);

    int insert(TumsCname row);

    int insertSelective(TumsCname row);

    List<TumsCname> selectByExample(TumsCnameExample example);

    TumsCname selectByPrimaryKey(@Param("cname") String cname, @Param("dname") String dname);

    int updateByExampleSelective(@Param("row") TumsCname row, @Param("example") TumsCnameExample example);

    int updateByExample(@Param("row") TumsCname row, @Param("example") TumsCnameExample example);

    int updateByPrimaryKeySelective(TumsCname row);

    int updateByPrimaryKey(TumsCname row);
}