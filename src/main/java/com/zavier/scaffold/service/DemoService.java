package com.zavier.scaffold.service;

import com.github.pagehelper.PageInfo;
import com.zavier.scaffold.pojo.Customer;

public interface DemoService {
    void testRollBack();
    void testRollBack1();
    PageInfo<Customer> testPageHelper();
}
