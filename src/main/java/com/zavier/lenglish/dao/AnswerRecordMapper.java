package com.zavier.lenglish.dao;

import com.zavier.lenglish.pojo.AnswerRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AnswerRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AnswerRecord record);

    int insertSelective(AnswerRecord record);

    AnswerRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AnswerRecord record);

    int updateByPrimaryKey(AnswerRecord record);
}