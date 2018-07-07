package com.zavier.lenglish.pojo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class Users {
    private Integer id;

    private String username;

    private String password;

    private String passwordSalt;

    private Date gmtCreate;

    private Date gmtModify;

    private Byte isDeleted;
}