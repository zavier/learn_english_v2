package com.zavier.lenglish.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Permissions {
    private Integer id;

    private String permission;

    private Date gmtCreate;

    private Date gmtModified;

    private Byte isDeleted;
}