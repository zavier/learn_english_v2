package com.zavier.lenglish.dao;

import com.zavier.lenglish.pojo.Users;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Integer id);

    Users selectByUserName(String username);

    int updateByPrimaryKeySelective(Users record);

    int updateBaseInfoByUsernameSelective(Users record);

    int updatePassword(Users users);

    int updateByPrimaryKey(Users record);
}