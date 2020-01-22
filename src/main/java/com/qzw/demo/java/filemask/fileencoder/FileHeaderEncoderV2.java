package com.qzw.demo.java.filemask.fileencoder;

import com.qzw.demo.java.filemask.absclass.AbstractFileEncoderV2;
import com.qzw.demo.java.filemask.component.PasswordHolder;
import com.qzw.demo.java.filemask.enums.DirChooseEnum;
import com.qzw.demo.java.filemask.enums.FileEncoderTypeEnum;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author BG388892
 * @date 2020/1/18
 */
@Log4j2
public class FileHeaderEncoderV2 extends AbstractFileEncoderV2 {
    @Override
    public FileEncoderTypeEnum getFileEncoderType() {
        return FileEncoderTypeEnum.FILE_HEADER_ENCODE;
    }

    @Override
    protected byte[][] encryptOriginFile(File fileOrDir, byte[] extraParam) {
        try (RandomAccessFile raf = new RandomAccessFile(fileOrDir, "rw")) {
            raf.seek(0);
            byte[] originHead = new byte[32];
            raf.read(originHead);
            raf.seek(0);
            raf.writeByte(255);
            raf.writeByte(254);
            raf.writeByte(0);
            raf.writeByte(0);
            byte[][] result = new byte[2][];
            result[0] = originHead;
            // not used
            result[1] = null;
            return result;
        } catch (IOException e) {
            log.info("文件使用中,加密失败,{}", fileOrDir.getPath());
            return null;
        }
    }

    @Override
    protected boolean decryptOriginFile(File fileOrDir, byte[] extraParam) {
        byte[] originHeader = extraParam;
        try (RandomAccessFile raf = new RandomAccessFile(fileOrDir, "rw")) {
            raf.seek(0);
            raf.write(originHeader);
        } catch (IOException e) {
            log.info("文件使用中,解密失败,{}", fileOrDir.getPath());
            return false;
        }
        return true;
    }

    @Test
    public void testEncode() {
        PasswordHolder.password = "123456";
        encodeFileOrDir(new File("D:\\Data\\测试\\aa"), DirChooseEnum.CASCADE_DIR);
    }

    @Test
    public void testDecode() {
        PasswordHolder.password = "123456";
        decodeFileOrDir(new File("D:\\Data\\测试\\aa"), DirChooseEnum.CASCADE_DIR);
    }
}
