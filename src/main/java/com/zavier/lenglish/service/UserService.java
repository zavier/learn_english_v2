package com.zavier.lenglish.service;

import com.zavier.lenglish.pojo.Users;

public interface UserService {
    /**
     * 注册用户时使用
     * 生成盐同时加密密码
     * @param users
     * @return
     */
    Users encryptUserInfo(Users users);

    void signIn(Users users);
}
