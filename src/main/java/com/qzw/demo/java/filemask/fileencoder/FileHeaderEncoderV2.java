package com.qzw.demo.java.filemask.fileencoder;

import com.qzw.demo.java.filemask.absclass.AbstractFileEncoder;
import com.qzw.demo.java.filemask.absclass.AbstractFileEncoderV2;
import com.qzw.demo.java.filemask.component.PasswordHolder;
import com.qzw.demo.java.filemask.enums.DirChooseEnum;
import com.qzw.demo.java.filemask.enums.FileEncoderTypeEnum;
import com.qzw.demo.java.filemask.exception.MaskException;
import com.qzw.demo.java.filemask.interfaces.PrivateDataAccessor;
import com.sun.beans.editors.ByteEditor;
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
    protected void encodeFile(File fileOrDir, DirChooseEnum dirChooseEnum) {
        if (!fileOrDir.exists()) {
            throw new MaskException(1000, "文件或者文件夹不存在解密加密失败");
        }
        if (dirChooseEnum.equals(DirChooseEnum.FILE_ONLY)) {
            this.mkPrivateDirIfNotExists(fileOrDir);
            executeEncrypt(fileOrDir);
        } else if (dirChooseEnum.equals(DirChooseEnum.CURRENT_DIR_ONLY)) {
            this.mkPrivateDirIfNotExists(fileOrDir);
            File[] files = fileOrDir.listFiles();
            if (files != null && files.length > 0) {
                this.mkPrivateDirIfNotExists(files[0]);
                for (File file : files) {
                    executeEncrypt(file);
                }
            }
            executeEncrypt(fileOrDir);
        } else if (dirChooseEnum.equals(DirChooseEnum.CASCADE_DIR)) {
            this.mkPrivateDirIfNotExists(fileOrDir);
            File[] files = fileOrDir.listFiles();
            if (files != null && files.length > 0) {
                this.mkPrivateDirIfNotExists(files[0]);

                for (File file : files) {
                    //cascade directory
                    if (file.isDirectory()) {
                        encodeFile(file, DirChooseEnum.CASCADE_DIR);
                        continue;
                    }
                    executeEncrypt(file);
                }
            }
            executeEncrypt(fileOrDir);
        }
    }

    @Override
    protected void decodeFile(File fileOrDir, DirChooseEnum dirChooseEnum) {
        if (!fileOrDir.exists()) {
            throw new MaskException(10000, "文件不存在,解密失败");
        }
        if (dirChooseEnum.equals(DirChooseEnum.FILE_ONLY)) {
            executeDecrypt(fileOrDir);
        } else if (dirChooseEnum.equals(DirChooseEnum.CURRENT_DIR_ONLY)) {
            File[] files = fileOrDir.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    executeDecrypt(file);
                }
            }
            executeDecrypt(fileOrDir);
        } else if (dirChooseEnum.equals(DirChooseEnum.CASCADE_DIR)) {
            File[] files = fileOrDir.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    //cascade directory
                    if (file.isDirectory()) {
                        decodeFile(file, DirChooseEnum.CASCADE_DIR);
                        continue;
                    }
                    executeDecrypt(file);
                }
            }
            executeDecrypt(fileOrDir);
        }
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
        encodeFile(new File("D:\\Data\\测试\\aa"), DirChooseEnum.CASCADE_DIR);
    }

    @Test
    public void testDecode() {
        PasswordHolder.password = "123456";
        decodeFile(new File("D:\\Data\\测试\\aa"), DirChooseEnum.CASCADE_DIR);
    }
}
