package com.zavier.lenglish.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class RolesPermissions {
    private Integer id;

    private Integer roleId;

    private Integer permissionId;

    private Date gmtCreate;

    private Date gmtModified;

    private Byte isDeleted;
}