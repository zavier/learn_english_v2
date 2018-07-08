package com.zavier.lenglish.dao;

import com.zavier.lenglish.pojo.Knowledge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface KnowledgeMapper {
    int deleteByPrimaryKey(Knowledge knowledge);

    int insert(Knowledge record);

    int insertSelective(Knowledge record);

    Knowledge selectByPrimaryKey(Integer id);

    List<Knowledge> selectByIds(@Param("ids") Set<Integer> ids);

    int updateByPrimaryKeySelective(Knowledge record);

    int updateByPrimaryKey(Knowledge record);
}