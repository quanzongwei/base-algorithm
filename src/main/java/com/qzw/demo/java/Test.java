package com.qzw.demo.java;

import java.io.File;
import java.io.IOException;

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

    /**
     * 文件被 binary viewer打开后, 可读写状态
     * 测试失败
     */
    @org.junit.Test
    public void testWriteFalse() throws IOException {
        File file = new File("D:\\Data\\测试\\test\\admin阿斯达.txt");
        boolean canWrite = file.canWrite();
        boolean canRead = file.canRead();

        System.out.println(canRead);
//
//        Path path = Paths.get("D:\\Data\\测试\\test\\admin阿斯达.txt");
//        FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.WRITE);
//        FileLock lock = fileChannel.tryLock();
//        if (fileChannel.isOpen()) {
//            System.out.println(true);
//        }
//        System.out.println(lock.isValid());
    }
}
