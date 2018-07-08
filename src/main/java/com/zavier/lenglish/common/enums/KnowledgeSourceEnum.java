package com.zavier.lenglish.common.enums;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 来源枚举类
 */
@Slf4j
public enum KnowledgeSourceEnum {
    OWNER(0, "手动添加"),
    TRANSLATE(1, "翻译导入"),
    OTHER(2, "他人分享");

    private int code;
    private String text;

    KnowledgeSourceEnum(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public static String getText(int code) {
        KnowledgeSourceEnum[] values = KnowledgeSourceEnum.values();
        for (KnowledgeSourceEnum value : values) {
            if (value.code == code) {
                return value.text;
            }
        }
        log.error("未找到code:{}", code);
        return null;
    }

    public static Integer getCode(String text) {
        KnowledgeSourceEnum[] values = KnowledgeSourceEnum.values();
        for (KnowledgeSourceEnum value : values) {
            if (StringUtils.equals(value.text, text)) {
                return value.code;
            }
        }
        log.error("未找到text:{}", text);
        return null;
    }
}
