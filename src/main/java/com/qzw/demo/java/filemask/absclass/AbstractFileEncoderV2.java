package com.qzw.demo.java.filemask.absclass;

import com.qzw.demo.java.filehide.ByteUtil;
import com.qzw.demo.java.filemask.enums.DirChooseEnum;
import com.qzw.demo.java.filemask.enums.FileEncoderTypeEnum;
import com.qzw.demo.java.filemask.exception.MaskException;
import com.qzw.demo.java.filemask.interfaces.FileEncoderType;
import com.qzw.demo.java.filemask.interfaces.PasswordHandler;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author BG388892
 * @date 2020/1/18
 */
@Log4j2
public abstract class AbstractFileEncoderV2 implements PasswordHandler, FileEncoderType {

    /**
     * entry method
     */
    public void encodeFileOrDir(File fileOrDir, DirChooseEnum dirChooseEnum) {
        if (!fileOrDir.exists()) {
            throw new MaskException(1000, "文件或者文件夹不存在解密加密失败,"+fileOrDir.getPath());
        }
        if (isFileMaskFile(fileOrDir)) {
            log.info("私有数据文件无需处理, {}", fileOrDir.getPath());
            return;
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
                        encodeFileOrDir(file, DirChooseEnum.CASCADE_DIR);
                        continue;
                    }
                    executeEncrypt(file);
                }
            }
            executeEncrypt(fileOrDir);
        }
    }

    /**
     * entry method
     */
    public void decodeFileOrDir(File fileOrDir, DirChooseEnum dirChooseEnum) {
        if (!fileOrDir.exists()) {
            throw new MaskException(10000, "文件或者文件夹不存在,解密失败, "+fileOrDir.getPath());
        }
        if (isFileMaskFile(fileOrDir)) {
            log.info("私有数据文件无需处理, {}", fileOrDir.getPath());
            return;
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
                        decodeFileOrDir(file, DirChooseEnum.CASCADE_DIR);
                        continue;
                    }
                    executeDecrypt(file);
                }
            }
            executeDecrypt(fileOrDir);
        }
    }

    protected boolean isFileMaskFile(File file) {
        boolean fileMask = file.getPath().contains(".fileMask");
        if (fileMask) {
            return true;
        }
        return false;
    }


    protected byte[] xorBySecretKey(byte[] text) {
        byte[] byte32 = this.get32byteMd5Value();
        byte[] rst = new byte[text.length];
        for (int i = 0; i < rst.length; i++) {
            rst[i] = (byte) (text[i] ^ byte32[i % (byte32.length)]);
        }
        return rst;
    }


    protected void mkPrivateDirIfNotExists(File fileOrDir) {
        String privateDataDirStr = fileOrDir.getParent() + File.separatorChar + ".fileMask";
        File file = new File(privateDataDirStr);
        if (!file.exists()) {
            file.mkdir();
            try {
                Runtime.getRuntime().exec("attrib " + "\"" + file.getAbsolutePath() + "\"" + " +H");
            } catch (IOException e) {
                // fileMask dir is not hid that has no effect
            }
        }
    }

    protected void executeEncrypt(File fileOrDir) {
        String pdf = fileOrDir.getParent() + File.separatorChar + ".fileMask" + File.separatorChar + fileOrDir.getName();
        File privateDataFile = new File(pdf);
        boolean exists = privateDataFile.exists();
        byte[] result1 = null;
        FileEncoderTypeEnum fileEncoderType = null;
        try (RandomAccessFile raf = new RandomAccessFile(privateDataFile, "rw")) {
            byte[] encodeMap = new byte[256];
            //1. 合法性校验
            if (exists) {
                // exists 意味着私有数据长度至少是256+32
                byte[] flags = new byte[3];
                raf.seek(16);
                raf.read(flags);
                boolean emptyFlag = true;
                for (byte flag : flags) {
                    if (flag == (byte) 0x01) {
                        emptyFlag = false;
                    }
                }
                if (emptyFlag) {
                    encodeMap = initialPrivateFileData(raf);
                } else {
                    // 检测md51 数据
                    raf.seek(0);
                    byte[] md51 = new byte[16];
                    raf.read(md51);
                    md51 = xorBySecretKey(md51);
                    if (!Arrays.equals(md51, getMd51())) {
                        log.info("文件已经被其他用户加密, 您无法执行加密操作, {}", fileOrDir.getPath());
                        return;
                    }
                    // 检测是否重复加密
                    boolean encrypted = encodedByCurrentUserUseTheSameType(flags, fileOrDir);
                    if (encrypted) {
                        return;
                    }
                }
            } else {
                encodeMap = initialPrivateFileData(raf);
            }
            //2. 执行加密
            byte[] extraParam = null;
            if (encodeMap == null) {
                raf.seek(32);
                raf.read(encodeMap);
                encodeMap = xorBySecretKey(encodeMap);
            }
            if (getFileEncoderType().equals(FileEncoderTypeEnum.FILE_CONTENT_ENCODE)) {
                extraParam = encodeMap;
            }
            byte[][] result = encryptOriginFile(fileOrDir, extraParam);
            if (result == null) {
//                log.info("加密失败,{}", fileOrDir.getPath());
                return;
            }
            byte[] result0 = result[0];
            result1 = result[1];
            //3. 私有数据文件处理
            fileEncoderType = getFileEncoderType();
            if (fileEncoderType.equals(FileEncoderTypeEnum.FILE_OR_DIR_NAME_ENCODE)) {
                raf.seek(16);
                raf.write((byte) 0x01);
                result0 = getResultByMap(result0, encodeMap, true);
                raf.seek(32 + 256 + 32);
                // n bytes
                raf.write(result0);
                //
            } else if (fileEncoderType.equals(FileEncoderTypeEnum.FILE_HEADER_ENCODE)) {
                raf.seek(16 + 1);
                raf.write((byte) 0x01);
                result0 = getResultByMap(result0, encodeMap, true);
                raf.seek(32 + 256);
                // 32 bytes
                raf.write(result0);
            } else if (fileEncoderType.equals(FileEncoderTypeEnum.FILE_CONTENT_ENCODE)) {
                raf.seek(16 + 2);
                raf.write((byte) 0x01);
                // no operation for return bytes, it is only a success flag for this FileEncoderType
            }
        } catch (IOException ex) {
            log.info("私有数据文件打开失败, 加密操作不成功,{}", pdf);
            return;
        }
        // IO操作完成后才可以执行重命名操作
        if (fileEncoderType.equals(FileEncoderTypeEnum.FILE_OR_DIR_NAME_ENCODE)) {
            //私有数据文件重命名
            boolean b = privateDataFile.renameTo(new File(privateDataFile.getParent() + File.separatorChar + new String(result1)));
            log.info("私有数据文件是否加密成功:{}", b);

        }

    }

    private byte[] getResultByMap(byte[] bytes, byte[] encodeMap, boolean isEncodeOperation) {
        if (isEncodeOperation) {
            log.info("加密操作 bytes:{}", new String(bytes));

            for (int j = 0; j < bytes.length; j++) {
                bytes[j] = encodeMap[ByteUtil.getUnsignedByte(bytes[j])];
            }
            log.info("加密操作 enodemap:{}", Base64.getEncoder().encodeToString(encodeMap));
            log.info("加密操作 bytes:{}", Base64.getEncoder().encodeToString(bytes));
            log.info("加密操作 bytes:{}", new String(bytes));

        } else {
            log.info("解密操作 bytes:{}", new String(bytes));

            byte[] decodeMap = new byte[256];
            for (int i = 0; i < encodeMap.length; i++) {
                decodeMap[ByteUtil.getUnsignedByte(encodeMap[i])] = (byte) i;
            }
            for (int j = 0; j < bytes.length; j++) {
                bytes[j] = decodeMap[ByteUtil.getUnsignedByte(bytes[j])];
            }
            log.info("解密操作 enodemap:{}", Base64.getEncoder().encodeToString(encodeMap));
            log.info("解密操作 bytes:{}", Base64.getEncoder().encodeToString(bytes));
            log.info("解密操作 bytes:{}", new String(bytes));

        }
        return bytes;
    }

    // exists at least one
    private boolean encodedByCurrentUserUseTheSameType(byte[] flags, File fileOrDir) {
        FileEncoderTypeEnum fileEncoderType = getFileEncoderType();
        int index = fileEncoderType.getFlagRelativeIndex();
        List<FileEncoderTypeEnum> encodedTypeList = new ArrayList<>();
        if (flags[0] == (byte) 0x01) {
            encodedTypeList.add(FileEncoderTypeEnum.FILE_OR_DIR_NAME_ENCODE);
        }
        if (flags[1] == (byte) 0x01) {
            encodedTypeList.add(FileEncoderTypeEnum.FILE_HEADER_ENCODE);
        }
        if (flags[2] == (byte) 0x01) {
            encodedTypeList.add(FileEncoderTypeEnum.FILE_CONTENT_ENCODE);
        }
        for (FileEncoderTypeEnum fileEncoderTypeEnum : encodedTypeList) {
            //加密方式相同
            if (fileEncoderTypeEnum.equals(getFileEncoderType())) {
                log.info("文件或文件夹已使用相同方式加密过, 无需重复加密,{}", fileOrDir.getPath());
                return true;
            }
            //加密方式为2
            if (FileEncoderTypeEnum.FILE_HEADER_ENCODE.equals(getFileEncoderType())) {
                if (fileEncoderTypeEnum.equals(FileEncoderTypeEnum.FILE_CONTENT_ENCODE)) {
                    log.info("文件已使用方式3加密, 不再使用方式2加密,{}", fileOrDir.getPath());
                    return true;
                }
            }
            //加密方式为3
            if (FileEncoderTypeEnum.FILE_CONTENT_ENCODE.equals(getFileEncoderType())) {
                if (fileEncoderTypeEnum.equals(FileEncoderTypeEnum.FILE_HEADER_ENCODE)) {
                    log.info("文件已使用方式2加密, 不再使用方式3加密,{}", fileOrDir.getPath());
                    return true;
                }
            }
        }
        return false;

    }

    private byte[] initialPrivateFileData(RandomAccessFile raf) throws IOException {
        raf.seek(0);
        raf.write(xorBySecretKey(getMd51()));
        byte[] encodeMap = generateEncodeMap();
        raf.seek(32);
        byte[] encryptedEncodeMap = xorBySecretKey(encodeMap);
        raf.write(encryptedEncodeMap);
        return encodeMap;

    }

    private byte[] generateEncodeMap() {
        byte[] encodeMap = new byte[256];
        Random rd = new Random();
        //todo 更加随机 +秘钥
        rd.setSeed(System.currentTimeMillis());
        byte[] rdBytes = new byte[256];
        rd.nextBytes(rdBytes);
        List<Byte> bList = new ArrayList<>();
        for (Integer i = 0; i < 256; i++) {
            bList.add(i.byteValue());
        }
        List<Byte> mappedList = new ArrayList<>();
        for (int i = 0; i < rdBytes.length; i++) {
            byte b = rdBytes[i];
            if (bList.contains(b)) {
                mappedList.add(b);
                bList.remove(bList.indexOf(b));
                bList = bList.stream().collect(Collectors.toList());
            } else {
                // conflict resolve
                int index = rd.nextInt(bList.size());
                mappedList.add(bList.get(index));
                bList.remove(index);
                bList = bList.stream().collect(Collectors.toList());
            }
        }
        for (int i = 0; i < mappedList.size(); i++) {
            encodeMap[i] = mappedList.get(i);
        }
        return encodeMap;
    }


    protected void executeDecrypt(File fileOrDir) {
        String pdf = fileOrDir.getParent() + File.separatorChar + ".fileMask" + File.separatorChar + fileOrDir.getName();
        File privateDataFile = new File(pdf);
        if (!privateDataFile.exists()) {
            log.info("文件从未加密过, 无需解密, {}", fileOrDir.getPath());
            return;
        }
        FileEncoderTypeEnum fileEncoderTypeEnum = null;
        // 加密方式一
        String originName = null;
        try (RandomAccessFile raf = new RandomAccessFile(privateDataFile, "rw")) {
            // 私有文件长度校验
            if (raf.length() < 32 + 256) {
                log.info("私有数据文件长度不合法, 无需解密,{}", fileOrDir.getPath());
                // 难道这里的return提前, 会导致io没有关闭吗
                return;
            }
            //用户合法性校验
            raf.seek(0);
            byte[] md51 = new byte[16];
            raf.read(md51);
            md51 = xorBySecretKey(md51);
            //是否使用指定方式加密
            if (!Arrays.equals(md51, getMd51())) {
                log.info("当前文件已被其他用户加密, 您无法执行解密操作,{}", fileOrDir.getPath());
                // io 没有关闭?
                raf.close();
                return;
            }
            //执行解密,并处理私有数据
            raf.seek(32);
            byte[] encodeMap = new byte[256];
            raf.read(encodeMap);
            encodeMap = xorBySecretKey(encodeMap);
            fileEncoderTypeEnum = getFileEncoderType();
            // 加密方式1
            if (fileEncoderTypeEnum.equals(FileEncoderTypeEnum.FILE_OR_DIR_NAME_ENCODE)) {
                raf.seek(32 + 256 + 32);
                byte[] extraParam = new byte[(int) (raf.length() - (32 + 256 + 32))];
                raf.read(extraParam);
                extraParam = getResultByMap(extraParam, encodeMap, false);
                originName = new String(extraParam);
                if (decryptOriginFile(fileOrDir, extraParam)) {
                    raf.seek(16);
                    // todo test write bytes
                    raf.write(0x00);
                    // 删除历史记录
                    raf.setLength(32 + 256 + 32);
                    //加密文件重命名
                }
            }
            //加密方式为2
            if (fileEncoderTypeEnum.equals(FileEncoderTypeEnum.FILE_HEADER_ENCODE)) {
                raf.seek(32 + 256);
                byte[] extraParam = new byte[32];
                raf.read(extraParam);
                extraParam = getResultByMap(extraParam, encodeMap, false);
                if (decryptOriginFile(fileOrDir, extraParam)) {
                    raf.seek(16 + 1);
                    raf.write(0x00);
                }
            }
            //加密方式为3
            if (fileEncoderTypeEnum.equals(FileEncoderTypeEnum.FILE_CONTENT_ENCODE)) {
                if (decryptOriginFile(fileOrDir, encodeMap)) {
                    raf.seek(16 + 2);
                    raf.write(0x00);
                }
            }
        } catch (IOException ex) {
            log.info("私有数据文件打开失败, 操作终止, {}", fileOrDir.getPath(), ex);
            return;
        }
        // IO操作完成后才可以执行重命名操作
        if (fileEncoderTypeEnum.equals(FileEncoderTypeEnum.FILE_OR_DIR_NAME_ENCODE)) {
            //私有数据文件重命名
            boolean b = privateDataFile.renameTo(new File(privateDataFile.getParent() + File.separatorChar + originName));
            log.info("私有数据文件是否重命名成功:{}", b);

        }
    }

    protected abstract byte[][] encryptOriginFile(File fileOrDir, byte[] extraParam);

    protected abstract boolean decryptOriginFile(File fileOrDir, byte[] extraParam);
}
