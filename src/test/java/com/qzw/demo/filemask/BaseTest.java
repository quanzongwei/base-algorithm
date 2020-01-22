package com.qzw.demo.filemask;

import com.qzw.demo.java.filemask.exception.MaskException;
import org.junit.Before;
import sun.misc.OSEnvironment;

import java.io.*;

/**
 * @author BG388892
 * @date 2020/1/22
 */
public class BaseTest {

    protected File getBaseFile() {
        File file = new File("D:\\Data\\测试\\test");
        return file;
    }

    protected File subFile() {
        File file = new File("D:\\Data\\测试\\test\\dir");
        return file;
    }
    protected File subFileEncrypted(int number) {
        File file = new File("D:\\Data\\测试\\test\\nDDir" + number);
        return file;
    }

    @Before
    public void setUp() {

    }

    public static char sp = File.separatorChar;
    String text = "admin中华人民共和国admin中华人民共和国admin中华人民共和国admin中华人民共和国admin中华人民共和国admin中华人民共和国";

    protected int createDirAndFile(String path, int level) throws IOException {
        boolean isFirstLevel = true;
        int returnValue = (level - 1) * 2 + 1-1;
        while (level-- > 0) {
            File dir = new File(path + sp + "dir");
            dir.mkdir();
            if (!isFirstLevel) {
                File file = new File(path + sp + "file.txt");
                try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
                    bw.write(text);
                }
            }

            path = path + sp + "dir";
            isFirstLevel = false;
        }
        return returnValue;
    }

    protected void validateFileNameTrue(File file) throws IOException {
        if (isFileMaskFile(file)) {
            return;
        }
        File[] files = file.listFiles();
        if (files != null && files.length > 0) {
            for (File one : files) {
                if (one.isDirectory()) {
                    if (isFileMaskFile(one)) {
                        continue;
                    }
                    if (!one.getName().equals("dir")) {
                        throw new MaskException(1000, "文件名称不正确,测试失败" + one.getPath());
                    }
                    validateFileNameTrue(one);
                    continue;
                }
                if (!one.getName().equals("file.txt")) {
                    throw new MaskException(1000, "文件名称不正确,测试失败" + one.getPath());
                }
            }
        }
        if (!file.getName().equals("dir")) {
            throw new MaskException(1000, "文件名称不正确,测试失败" + file.getPath());
        }
    }

    boolean isFileMaskFile(File file) {
        if (file.getPath().contains(".fileMask")) {
            return true;
        }
        return false;
    }

    protected void validateFileNameFalse(File file) throws IOException {

        if (isFileMaskFile(file)) {
            return;
        }
        File[] files = file.listFiles();
        if (files != null && files.length> 0) {
            for (File one : files) {
                if (one.isDirectory()) {
                    if (isFileMaskFile(one)) {
                        continue;
                    }
                    if (one.getName().equals("dir")) {
                        throw new MaskException(1000, "文件名称不正确,测试失败" + one.getPath());
                    }
                    validateFileNameFalse(one);
                    continue;
                }
                if (one.getName().equals("file.txt")) {
                    throw new MaskException(1000, "文件名称不正确,测试失败" + one.getPath());
                }
            }
        }
        if (file.getName().equals("dir")) {
            throw new MaskException(1000, "文件名称不正确,测试失败" + file.getPath());
        }
    }


    //todo
    protected void validateFileContent(File file) throws IOException {
        File[] files = file.listFiles();
        if (files != null && files.length > 0) {
            for (File one : files) {
                if (one.isDirectory()) {
                    validateFileContent(one);
                    continue;
                }
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(one)));
                if (!br.readLine().equals(text)) {
                    throw new MaskException(1000, "文件内容不正确,测试失败" + one.getPath());

                }
            }
        }
    }

    // 级联删除文件夹下所有数据 不包含外面的文件夹
    protected void removeFileAndDir(File file, boolean isFirst) {
        if (!file.getPath().contains("测试")) {
            throw new MaskException(10000,"费测试文件禁止删除");
        }
        File[] files = file.listFiles();
        if (files != null && files.length > 0) {
            for (File one : files) {
                if (one.isDirectory()) {
                    removeFileAndDir(one, false);
                }
                one.delete();
            }
        }
        if (!isFirst) {
            file.delete();
        }
    }
}
