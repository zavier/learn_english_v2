package com.zavier.lenglish.dao;

import com.zavier.lenglish.param.KnowledgeSearchParam;
import com.zavier.lenglish.pojo.Knowledge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface KnowledgeMapper {
    int deleteByPrimaryKey(Knowledge knowledge);

    int insert(Knowledge record);

    int insertBatch(@Param("knowledges") List<Knowledge> knowledges);

    int insertSelective(Knowledge record);

    Knowledge selectByPrimaryKey(Integer id);

    List<Knowledge> selectByIds(@Param("ids") Set<Integer> ids);

    List<Knowledge> searchByParam(KnowledgeSearchParam param);

    int updateByPrimaryKeySelective(Knowledge record);

    int updateByPrimaryKey(Knowledge record);

    int count();

    List<Knowledge> selectRandom(@Param("offset") int offset, @Param("limit") int limit);
}