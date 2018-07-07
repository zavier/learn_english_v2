package com.zavier.lenglish.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Roles {
    private Integer id;

    private String roleName;

    private Date gmtCreate;

    private Date gmtModified;

    private Byte isDeleted;
}