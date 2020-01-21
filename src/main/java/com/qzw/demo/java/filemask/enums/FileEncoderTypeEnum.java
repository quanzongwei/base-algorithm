package com.qzw.demo.java.filemask.enums;

/**
 * @author BG388892
 * @date 2020/1/18
 */
public enum FileEncoderTypeEnum {
    FILE_OR_DIR_NAME_ENCODE(1),
    FILE_HEADER_ENCODE(2),
    FILE_CONTENT_ENCODE(3);

    public int getType() {
        return type;
    }

    private int type;

    FileEncoderTypeEnum(int type) {
        this.type = type;
    }
}
