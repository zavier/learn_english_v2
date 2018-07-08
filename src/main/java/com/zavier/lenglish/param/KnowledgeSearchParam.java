package com.zavier.lenglish.param;

import lombok.Data;

import java.util.Date;

@Data
public class KnowledgeSearchParam {

    private Integer creator;

    private Integer modifier;

    private String english;

    private String chinese;

    private Byte difficultyDegree;

    private Byte isPublished;

    private Byte source;

    private String remark;

    private String tags;

    private Integer page;

    private Integer size = 10;
}
