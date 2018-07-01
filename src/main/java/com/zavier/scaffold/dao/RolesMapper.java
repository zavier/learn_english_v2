package com.zavier.scaffold.dao;

import com.zavier.scaffold.pojo.Roles;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RolesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Roles record);

    int insertSelective(Roles record);

    Roles selectByPrimaryKey(Integer id);

    List<Roles> selectRolesByUserName(String username);

    int updateByPrimaryKeySelective(Roles record);

    int updateByPrimaryKey(Roles record);
}