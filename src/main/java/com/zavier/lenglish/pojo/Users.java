package com.zavier.lenglish.pojo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class Users {
    private Integer id;

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String email;
    @NotNull
    private String mobile;

    private String passwordSalt;

    private Date gmtCreate;

    private Date gmtModify;

    private Byte isDeleted;
}