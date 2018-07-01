package com.zavier.scaffold.dao;

import com.zavier.scaffold.pojo.RolesPermissions;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RolesPermissionsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RolesPermissions record);

    int insertSelective(RolesPermissions record);

    RolesPermissions selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RolesPermissions record);

    int updateByPrimaryKey(RolesPermissions record);
}