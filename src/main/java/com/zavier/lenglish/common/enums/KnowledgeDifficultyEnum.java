package com.zavier.lenglish.common.enums;

import com.zavier.lenglish.pojo.Knowledge;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 句子困难度枚举类
 */
@Slf4j
public enum KnowledgeDifficultyEnum {
    SIMPLE(0, "简单"),
    MIDDLE(1, "中等"),
    DIFFICULTY(2, "困难");

    @Getter
    private int code;
    @Getter
    private String text;

    KnowledgeDifficultyEnum(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public static String getText(int code) {
        KnowledgeDifficultyEnum[] values = KnowledgeDifficultyEnum.values();
        for (KnowledgeDifficultyEnum value : values) {
            if (code == value.code) {
                return value.text;
            }
        }
        log.error("未找到code:{}", code);
        return null;
    }

    public static Integer getCode(String text) {
        KnowledgeDifficultyEnum[] values = KnowledgeDifficultyEnum.values();
        for (KnowledgeDifficultyEnum value : values) {
            if (StringUtils.equals(value.text, text)) {
                return value.code;
            }
        }
        log.error("未找到text:{}", text);
        return null;
    }
}
