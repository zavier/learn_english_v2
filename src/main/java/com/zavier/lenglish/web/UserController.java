package com.zavier.lenglish.web;

import com.zavier.lenglish.bo.UsersBO;
import com.zavier.lenglish.common.ResultBean;
import com.zavier.lenglish.pojo.Users;
import com.zavier.lenglish.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private Mapper mapper;

    @GetMapping("name")
    public ResultBean<String> getName() {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        return ResultBean.createBySuccessMessage(username);
    }

    @GetMapping("info")
    public ResultBean<UsersBO> getUserInfo() {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        Users userInfo = userService.getUserInfo(username);
        if (userInfo == null) {
            return ResultBean.createByErrorMessage("当前用户不存在");
        }
        UsersBO usersBo = mapper.map(userInfo, UsersBO.class);
        return ResultBean.createBySuccess(usersBo);
    }

    @PostMapping("update")
    public ResultBean<UsersBO> updateUserInfo(@RequestBody Users users) {
        Users updateUsers = userService.updateInfo(users);
        UsersBO usersBO = mapper.map(updateUsers, UsersBO.class);
        return ResultBean.createBySuccess(usersBO);
    }

    @PostMapping("reset-pwd")
    public ResultBean<String> resetPassword(@RequestParam String pwd) {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        Users users = new Users();
        users.setUsername(username);
        users.setPassword(pwd);
        userService.resetPassword(users);
        // 重设密码后退出登录状态
        subject.logout();
        return ResultBean.createBySuccess();
    }
}
