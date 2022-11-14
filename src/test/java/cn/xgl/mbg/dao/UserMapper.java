package cn.xgl.mbg.dao;

import cn.xgl.mbg.model.User;

public interface UserMapper {
    int deleteByPrimaryKey(String userid);

    int insert(User row);

    int insertSelective(User row);

    User selectByPrimaryKey(String userid);

    int updateByPrimaryKeySelective(User row);

    int updateByPrimaryKey(User row);
}