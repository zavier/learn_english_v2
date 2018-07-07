package com.zavier.lenglish.dao;

import com.zavier.lenglish.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Tag record);

    int insertSelective(Tag record);

    Tag selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Tag record);

    int updateByPrimaryKey(Tag record);
}