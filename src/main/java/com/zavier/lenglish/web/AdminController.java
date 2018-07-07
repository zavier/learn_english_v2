package com.zavier.lenglish.web;

import com.zavier.lenglish.common.ResultBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @GetMapping("/admin")
    public ResultBean<String> admin() {
        return ResultBean.createBySuccess("成功进入管理员界面");
    }
}
