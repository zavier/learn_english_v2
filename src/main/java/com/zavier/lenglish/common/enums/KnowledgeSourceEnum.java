package com.zavier.lenglish.common.enums;

/**
 * 来源枚举类
 */
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
        return null;
    }
}
