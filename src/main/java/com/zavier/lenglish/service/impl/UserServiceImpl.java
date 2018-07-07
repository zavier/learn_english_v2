package com.zavier.lenglish.service.impl;

import com.zavier.lenglish.common.EncryptConstants;
import com.zavier.lenglish.dao.UserRolesMapper;
import com.zavier.lenglish.dao.UsersMapper;
import com.zavier.lenglish.pojo.Users;
import com.zavier.lenglish.service.UserService;
import org.apache.shiro.crypto.CryptoException;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersMapper usersMapper;

    private UserRolesMapper userRolesMapper;

    /**
     * 对用户密码进行加密（加盐）
     * @param users
     * @return
     */
    @Override
    public Users encryptUserInfo(Users users) {
        String username = users.getUsername();
        String password = users.getPassword();

        Users newUser = new Users();
        newUser.setUsername(username);
        String salt1 = username;
        String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
        newUser.setPasswordSalt(salt2);
        SimpleHash simpleHash = new SimpleHash(EncryptConstants.HASH_ALGORITHM_NAME, password,
                salt1 + salt2, EncryptConstants.HASH_ITERATIONS);
        newUser.setPassword(simpleHash.toHex());
        return newUser;
    }
}
