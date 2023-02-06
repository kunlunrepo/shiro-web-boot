package com.msb.service.impl;

import com.msb.entity.User;
import com.msb.mapper.UserMapper;
import com.msb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-02-02 17:38
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }
}
