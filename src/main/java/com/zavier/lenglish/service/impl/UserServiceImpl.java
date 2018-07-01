package com.zavier.lenglish.service.impl;

import com.zavier.lenglish.dao.UserRolesMapper;
import com.zavier.lenglish.dao.UsersMapper;
import com.zavier.lenglish.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersMapper usersMapper;

    private UserRolesMapper userRolesMapper;
}
