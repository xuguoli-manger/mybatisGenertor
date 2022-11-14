package cn.xgl.mbg.service;

import cn.xgl.mbg.model.User;

public interface IUserService {
    int deleteByPrimaryKey(String userid);

    int insert(User row);

    int insertSelective(User row);

    User selectByPrimaryKey(String userid);

    int updateByPrimaryKeySelective(User row);

    int updateByPrimaryKey(User row);
}