package com.zavier.scaffold.web;

import com.github.pagehelper.PageInfo;
import com.zavier.scaffold.common.ProcessException;
import com.zavier.scaffold.common.ResultBean;
import com.zavier.scaffold.pojo.Customer;
import com.zavier.scaffold.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DemoController {

    @Value("${amazon.sourceId}")
    private String sourceId;

    @Autowired
    private DemoService demoService;

    @GetMapping("/test")
    public ResultBean<String> test() {
        log.info(sourceId);
        return ResultBean.createBySuccess("SUCCESS");
    }

    @GetMapping("/testerror")
    public ResultBean<String> testError() {
        log.error("error");
        throw new ProcessException("testError");
    }

    @GetMapping("/testerror1")
    public String testError1() {
        log.error("error");
        throw new ProcessException("testError");
    }

    @GetMapping("/rollback")
    public ResultBean<String> testRollBack() {
        demoService.testRollBack();
        return ResultBean.createBySuccess();
    }

    @GetMapping("/rollback1")
    public ResultBean<String> testRollBack1() {
        demoService.testRollBack1();
        return ResultBean.createBySuccess();
    }

    @GetMapping("/page")
    public ResultBean<PageInfo<Customer>> testPageHelper() {
        PageInfo<Customer> customerPageInfo = demoService.testPageHelper();
        return ResultBean.createBySuccess(customerPageInfo);
    }
}
