package com.zavier.lenglish.web;

import com.zavier.lenglish.common.ResultBean;
import com.zavier.lenglish.dao.UsersMapper;
import com.zavier.lenglish.pojo.Users;
import com.zavier.lenglish.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CommonController {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/error")
    public ResultBean<String> error(Exception e) {
        log.error("发生异常", e);
        return ResultBean.createByErrorMessage("系统内部错误");
    }

    @GetMapping(value = "/unauthorication")
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResultBean<String> unauthorication() {
        return ResultBean.createByNotLogin();
    }

    @PostMapping(value = "/sign-in")
    public ResultBean<String> signIn(@RequestBody Users users) {
        Subject subject = SecurityUtils.getSubject();
        if(!subject.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(users.getUsername(), users.getPassword(), false);
            try {
                subject.login(token);
            } catch (UnknownAccountException ae) {
                log.info("用户名不存在");
                return ResultBean.createByNotLogin();
            } catch (AuthenticationException e) {
                log.info("认证失败");
                return ResultBean.createByNotLogin();
            }
        }
        return ResultBean.createBySuccess();
    }

    @PostMapping(value = "sign-up")
    public ResultBean<String> signUp(@RequestBody Users users) {
        Users encryptUserInfo = userService.encryptUserInfo(users);
        usersMapper.insert(encryptUserInfo);
        return ResultBean.createBySuccess();
    }

    @GetMapping(value = "/secure")
    @ResponseBody
    public ResultBean<String> secure() {
        Subject currentUser = SecurityUtils.getSubject();
        String role = "", permission = "";

        if(currentUser.hasRole("admin")) {
            role = role  + "You are an Admin";
        } else if(currentUser.hasRole("editor")) {
            role = role + "You are an Editor";
        } else if(currentUser.hasRole("author")) {
            role = role + "You are an Author";
        }

        if(currentUser.isPermitted("articles:compose")) {
            permission = permission + "You can compose an article, ";
        } else {
            permission = permission + "You are not permitted to compose an article!, ";
        }

        if(currentUser.isPermitted("articles:save")) {
            permission = permission + "You can save articles, ";
        } else {
            permission = permission + "\nYou can not save articles, ";
        }

        if(currentUser.isPermitted("articles:publish")) {
            permission = permission  + "\nYou can publish articles";
        } else {
            permission = permission + "\nYou can not publish articles";
        }

        return ResultBean.createBySuccess();
    }
}
