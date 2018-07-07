package com.zavier.lenglish.bo;

import lombok.Data;

import java.util.Date;

@Data
public class UsersBO {

    private Integer id;

    private String username;

    private String password;

    private String email;

    private String mobile;
}
