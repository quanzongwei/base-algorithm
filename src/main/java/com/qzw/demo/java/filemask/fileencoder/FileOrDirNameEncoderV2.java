package com.qzw.demo.java.filemask.fileencoder;

import com.qzw.demo.java.filemask.absclass.AbstractFileEncoderV2;
import com.qzw.demo.java.filemask.component.PasswordHolder;
import com.qzw.demo.java.filemask.enums.DirChooseEnum;
import com.qzw.demo.java.filemask.enums.FileEncoderTypeEnum;
import com.qzw.demo.java.filemask.exception.MaskException;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.io.File;

/**
 *
 * todo 新增的文件重名问题 +2就好了
 * todo sequece保存问题
 * @author BG388892
 * @date 2020/1/18
 */
@Log4j2
public class FileOrDirNameEncoderV2 extends AbstractFileEncoderV2 {
    @Override
    public FileEncoderTypeEnum getFileEncoderType() {
        return FileEncoderTypeEnum.FILE_OR_DIR_NAME_ENCODE;
    }

    public static int sequence = 0;

    /**
     * @param fileOrDir
     * @param dirChooseEnum
     */
    @Override
    public void encodeFile(File fileOrDir, DirChooseEnum dirChooseEnum) {
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
    public void decodeFile(File fileOrDir, DirChooseEnum dirChooseEnum) {
        if (!fileOrDir.exists()) {
            throw new MaskException(10000, "文件不存在,解密失败");
        }
        //todo 文件不存在校验
        // 暂未发现rename失败的情况
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
        String originName = fileOrDir.getName();
        String targetName = (fileOrDir.isDirectory() ? "nDDir" : "nDFiLe") + sequence++;
        String targetPath = fileOrDir.getParent() + File.separatorChar + targetName;
        // todo test change 名字还是原来的名字, 确实是的
        boolean b = fileOrDir.renameTo(new File(targetPath));
        if (!b) {
            log.info("文件加密(方式一)失败,{}", fileOrDir.getPath());
            return null;
        }
        byte[][] result = new byte[2][];
        result[0] = originName.getBytes();
        result[1] = targetName.getBytes();
        return result;
    }

    @Override
    protected boolean decryptOriginFile(File fileOrDir, byte[] extraParam) {
        String originPath = fileOrDir.getParent() + File.separatorChar + new String(extraParam);
        boolean b = fileOrDir.renameTo(new File(originPath));
        if (!b) {
            log.info("文件解密(方式一)失败,{}", fileOrDir.getPath());
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
        decodeFile(new File("D:\\Data\\测试\\nDDir114"), DirChooseEnum.CASCADE_DIR);
    }
}
