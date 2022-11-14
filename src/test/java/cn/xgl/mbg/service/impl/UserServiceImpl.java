package cn.xgl.mbg.service.impl;

import cn.xgl.mbg.dao.UserMapper;
import cn.xgl.mbg.model.User;
import cn.xgl.mbg.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    public UserMapper userMapper;

    @Override
    public int deleteByPrimaryKey(String userid) {
        return userMapper.deleteByPrimaryKey(userid);
    }

    @Override
    public int insert(User row) {
        return userMapper.insert(row);
    }

    @Override
    public int insertSelective(User row) {
        return userMapper.insertSelective(row);
    }

    @Override
    public User selectByPrimaryKey(String userid) {
        return userMapper.selectByPrimaryKey(userid);
    }

    @Override
    public int updateByPrimaryKeySelective(User row) {
        return userMapper.updateByPrimaryKeySelective(row);
    }

    @Override
    public int updateByPrimaryKey(User row) {
        return userMapper.updateByPrimaryKey(row);
    }
}