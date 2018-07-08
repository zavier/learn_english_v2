package com.zavier.lenglish.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("key1", "value-test1");
    }

    @Test
    public void testChinese() {
        redisTemplate.opsForValue().set("中文key", "中文value");
        String value = (String) redisTemplate.opsForValue().get("中文key");
        Assert.assertEquals("中文value", value);
    }
}
