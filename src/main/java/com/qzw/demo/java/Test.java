package com.qzw.demo.java;

import java.io.File;

/**
 * @author BG388892
 * @date 2019/11/25
 */
public class Test {

    /**
     * 文件被 binary viewer打开后, 会重命名失败
     */
    @org.junit.Test
    public void testRenameFalse() {
        File file = new File("D:\\Data\\测试\\.fileMask\\aa");
        boolean b = file.renameTo(new File("D:\\Data\\测试\\.fileMask\\target"));
        System.out.println(b);
    }
}
