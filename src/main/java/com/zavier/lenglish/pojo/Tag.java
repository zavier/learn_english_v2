package com.zavier.lenglish.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Tag {
    private Integer id;

    private String tagName;

    private Date gmtCreate;

    private Date gmtModified;

    private Integer creator;

    private Integer modifier;

    private Byte isDeleted;

}