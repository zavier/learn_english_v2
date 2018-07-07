package com.zavier.lenglish.dao;

import com.zavier.lenglish.pojo.Knowledge;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KnowledgeMapper {
    int deleteByPrimaryKey(Knowledge knowledge);

    int insert(Knowledge record);

    int insertSelective(Knowledge record);

    Knowledge selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Knowledge record);

    int updateByPrimaryKey(Knowledge record);
}