package com.zavier.lenglish.web;

import com.zavier.lenglish.common.ResultBean;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("getName")
    public ResultBean<String> getName() {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        return ResultBean.createBySuccessMessage(username);
    }
}
