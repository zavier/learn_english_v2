package com.zavier.lenglish.common.enums;

/**
 * 句子困难度枚举类
 */
public enum KnowledgeDifficultyEnum {
    SIMPLE(0, "简单"),
    MIDDLE(1, "中等"),
    DIFFICULTY(2, "困难");

    private int code;
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
        return null;
    }
}
