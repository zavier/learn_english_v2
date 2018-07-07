package com.zavier.lenglish.common.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.zavier.lenglish.pojo.Users;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.concurrent.TimeUnit;

public class CacheUtil {
    private static Cache<String, Users> cache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .build();

    public static void putUserInfo(Users users) {
        cache.put(users.getUsername(), users);
    }

    public static Users getUserInfo(String username) {
        return cache.getIfPresent(username);
    }
}
