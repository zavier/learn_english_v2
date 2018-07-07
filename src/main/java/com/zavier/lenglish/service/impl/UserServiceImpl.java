package com.zavier.lenglish.service.impl;

import com.zavier.lenglish.common.BusinessProcessException;
import com.zavier.lenglish.common.EncryptConstants;
import com.zavier.lenglish.common.util.ValidatorUtil;
import com.zavier.lenglish.dao.UserRolesMapper;
import com.zavier.lenglish.dao.UsersMapper;
import com.zavier.lenglish.pojo.Users;
import com.zavier.lenglish.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UserRolesMapper userRolesMapper;
    @Autowired
    private Mapper mapper;
    @Autowired
    private ValidatorUtil validatorUtil;

    /**
     * 对用户密码进行加密（加盐）
     * @param users
     * @return
     */
    @Override
    public Users encryptUserInfo(Users users) {
        String username = users.getUsername();
        String password = users.getPassword();

        Users newUser = mapper.map(users, Users.class);
        String salt1 = username;
        String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
        newUser.setPasswordSalt(salt2);
        SimpleHash simpleHash = new SimpleHash(EncryptConstants.HASH_ALGORITHM_NAME, password,
                salt1 + salt2, EncryptConstants.HASH_ITERATIONS);
        newUser.setPassword(simpleHash.toHex());
        return newUser;
    }

    @Override
    public void signIn(Users users) {
        Subject subject = SecurityUtils.getSubject();
        if(! subject.isAuthenticated()) {
            log.info("未认证，{}", users.toString());
            UsernamePasswordToken token = new UsernamePasswordToken(users.getUsername(), users.getPassword(), false);
            subject.login(token);
        }
    }

    @Override
    public Users getUserInfo(String username) {
        if (StringUtils.isBlank(username)) {
            return null;
        }
        Users users = usersMapper.selectByUserName(username);
        return users;
    }

    @Override
    public Users updateInfo(Users users) {
        if (users == null || StringUtils.isBlank(users.getUsername())) {
            throw new BusinessProcessException("参数不能为空");
        }
        usersMapper.updateBaseInfoByUsernameSelective(users);
        return users;
    }

    @Override
    public void resetPassword(Users users) {
        if (users == null
                || StringUtils.isBlank(users.getUsername())
                || StringUtils.isBlank(users.getPassword())) {
            throw new BusinessProcessException("参数不能为空");
        }
        Users encryptUserInfo = encryptUserInfo(users);
        Users newPwdUserInfo = new Users();
        newPwdUserInfo.setUsername(users.getUsername());
        newPwdUserInfo.setPassword(encryptUserInfo.getPassword());
        newPwdUserInfo.setPasswordSalt(encryptUserInfo.getPasswordSalt());
        usersMapper.updatePassword(newPwdUserInfo);
    }
}
