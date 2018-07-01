package com.zavier.scaffold.service.impl;

import com.zavier.scaffold.dao.UserRolesMapper;
import com.zavier.scaffold.dao.UsersMapper;
import com.zavier.scaffold.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersMapper usersMapper;

    private UserRolesMapper userRolesMapper;
}
