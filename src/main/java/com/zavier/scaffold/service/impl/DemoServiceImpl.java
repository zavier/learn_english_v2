package com.zavier.scaffold.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zavier.scaffold.dao.CustomerMapper;
import com.zavier.scaffold.pojo.Customer;
import com.zavier.scaffold.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DemoServiceImpl implements DemoService {
    @Autowired
    private CustomerMapper customerMapper;

    public String test() {
        return "DemoService";
    }

    @Override
    @Transactional
    public void testRollBack() {
        Customer customer = new Customer();
        customer.setName("roll1");
        customerMapper.insertSelective(customer);
        Customer customer1 = new Customer();
        customer1.setName("testrollbackdadfa2");
        customerMapper.insertSelective(customer1);
    }

    @Override
    @Transactional
    public void testRollBack1() {
        Customer customer = new Customer();
        customer.setName("roll1");
        customerMapper.insertSelective(customer);
        throw new RuntimeException("就是不让你提交，看事物好用不");
    }

    @Override
    @Transactional
    public PageInfo<Customer> testPageHelper() {
        PageHelper.startPage(2, 2);
        List<Customer> customers = customerMapper.selectAll();
        PageInfo<Customer> customerPageInfo = new PageInfo<>(customers);
        return customerPageInfo;
    }
}
