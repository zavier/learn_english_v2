package com.zavier.lenglish.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class UserRoles {
    private Integer id;

    private Integer userId;

    private Integer roleId;

    private Date gmtCreate;

    private Date gmtModified;

    private Byte isDeleted;
}