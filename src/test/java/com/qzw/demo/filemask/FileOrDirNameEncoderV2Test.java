package com.qzw.demo.filemask;

import com.qzw.demo.java.filemask.component.PasswordHolder;
import com.qzw.demo.java.filemask.enums.DirChooseEnum;
import com.qzw.demo.java.filemask.fileencoder.FileOrDirNameEncoderV2;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.AccessMode;

/**
 *
 * @author BG388892
 * @date 2020/1/18
 */
public class FileOrDirNameEncoderV2Test extends BaseTest {

    FileOrDirNameEncoderV2 encoder = new FileOrDirNameEncoderV2();

    // 必须抛出exception吗, 这个 todo test
    @Test
    public void testEncode() throws Exception {
        removeFileAndDir(getBaseFile(),true);
        PasswordHolder.password = "123456";
        int fileamount = createDirAndFile(getBaseFile().getPath(), 2);
        //
        encoder.encodeFileOrDir(subFile(),DirChooseEnum.CASCADE_DIR);
        PasswordHolder.password = "123456";

        validateFileNameFalse(subFileEncrypted(fileamount));
        encoder.decodeFileOrDir(subFileEncrypted(fileamount),DirChooseEnum.CASCADE_DIR);
        validateFileNameTrue(subFile());
        //
        //validateFileNameFalse(subFile());
    }


}
