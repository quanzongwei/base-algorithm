package com.qzw.demo.java.filemask.enums;

import lombok.Getter;

/**
 * @author BG388892
 * @date 2020/1/18
 */
public enum FileEncoderTypeEnum {
    FILE_OR_DIR_NAME_ENCODE(1,0),
    FILE_HEADER_ENCODE(2,1),
    FILE_CONTENT_ENCODE(3,2);

    public int getType() {
        return type;
    }


    private int type;
    @Getter
    private int flagRelativeIndex;

    FileEncoderTypeEnum(int type,int flagRelativeIndex) {
        this.flagRelativeIndex = flagRelativeIndex;
        this.type = type;
    }
}
