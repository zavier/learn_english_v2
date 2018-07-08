package com.zavier.lenglish.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class AnswerRecord {
    private Integer id;

    private Date gmtCreate;

    private Date gmtModified;

    private Byte isDeleted;

    private Integer knowledgeId;

    private Integer usersId;

    private String userAnswer;

    private Byte answerType;

    private Byte isRight;

    private String questionBatch;

}