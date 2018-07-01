package com.zavier.scaffold.web;

import com.zavier.scaffold.common.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;

@RestController
@Slf4j
public class CommonController {
    @GetMapping(value = "/unauthorication")
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResultBean<String> unauthorication() {
        return ResultBean.createByNotLogin();
    }

    @GetMapping(value = "/login")
    @ResponseBody
    public ResultBean<String> login() {
        Subject subject = SecurityUtils.getSubject();
        if(!subject.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken("name", "password", false);
            try {
                subject.login(token);
            } catch (UnknownAccountException ae) {
                log.info("当前用户未登录");
                return ResultBean.createByNotLogin();
            } catch (AuthenticationException e) {
                log.info("认证失败");
                return ResultBean.createByNotLogin();
            }
        }
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
