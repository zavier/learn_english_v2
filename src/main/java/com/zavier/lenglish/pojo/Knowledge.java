package com.zavier.lenglish.pojo;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class Knowledge {
    private Integer id;

    private Date gmtCreate;

    private Date gmtModified;

    private Byte isDeleted;

    private Integer creator;

    private Integer modifier;
    @NotNull
    private String english;
    @NotNull
    private String chinese;

    private Byte difficultyDegree;

    private Byte isPublished;

    private Byte source;

    private String remark;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}