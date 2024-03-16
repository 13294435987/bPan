package onem.baymax.pan.core.util;

import cn.hutool.core.date.DateUtil;
import lombok.experimental.UtilityClass;
import onem.baymax.pan.core.constant.BPanConstant;
import onem.baymax.pan.core.exception.BPanBusinessException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;

/**
 * 文件工具类
 *
 * @author hujiabin wrote in 2024/3/12 21:14
 */
@UtilityClass
public class FileUtils {
    /**
     * 获取文件的后缀
     *
     * @param filename 文件名
     * @return 后缀
     */
    public static String getFileSuffix(String filename) {
        if (StringUtils.isBlank(filename) || filename.lastIndexOf(BPanConstant.POINT_STR) == BPanConstant.MINUS_ONE_INT) {
            return BPanConstant.EMPTY_STR;
        }
        return filename.substring(filename.lastIndexOf(BPanConstant.POINT_STR)).toLowerCase();
    }

    /**
     * 获取文件的类型
     *
     * @param filename 文件名
     * @return 类型
     */
    public static String getFileExtName(String filename) {
        if (StringUtils.isBlank(filename) || filename.lastIndexOf(BPanConstant.POINT_STR) == BPanConstant.MINUS_ONE_INT) {
            return BPanConstant.EMPTY_STR;
        }
        return filename.substring(filename.lastIndexOf(BPanConstant.POINT_STR) + BPanConstant.ONE_INT).toLowerCase();
    }

    /**
     * 通过文件大小转化文件大小的展示名称
     *
     * @param totalSize 文件大小
     * @return 展示名称
     */
    public static String byteCountToDisplaySize(Long totalSize) {
        if (Objects.isNull(totalSize)) {
            return BPanConstant.EMPTY_STR;
        }
        return org.apache.commons.io.FileUtils.byteCountToDisplaySize(totalSize);
    }

    /**
     * 批量删除物理文件
     *
     * @param realFilePathList 路径集合
     */
    public static void deleteFiles(List<String> realFilePathList) throws IOException {
        if (CollectionUtils.isEmpty(realFilePathList)) {
            return;
        }
        for (String realFilePath : realFilePathList) {
            org.apache.commons.io.FileUtils.forceDelete(new File(realFilePath));
        }
    }

    /**
     * 生成文件的存储路径
     * <p>
     * 生成规则：基础路径 + 年 + 月 + 日 + 随机的文件名称
     *
     * @param basePath 基础路径
     * @param filename 文件名
     * @return 文件存储路径
     */
    public static String generateStoreFileRealPath(String basePath, String filename) {
        return basePath +
                File.separator +
                DateUtil.thisYear() +
                File.separator +
                (DateUtil.thisMonth() + 1) +
                File.separator +
                DateUtil.thisDayOfMonth() +
                File.separator +
                UUIDUtil.getUuid() +
                getFileSuffix(filename);

    }

    /**
     * 将文件的输入流写入到文件中
     * 使用底层的sendfile零拷贝来提高传输效率
     *
     * @param inputStream 输入流
     * @param targetFile  目标文件
     * @param totalSize   文件大小
     */
    public static void writeStream2File(InputStream inputStream, File targetFile, Long totalSize) throws IOException {
        createFile(targetFile);
        RandomAccessFile randomAccessFile = new RandomAccessFile(targetFile, "rw");
        FileChannel outputChannel = randomAccessFile.getChannel();
        ReadableByteChannel inputChannel = Channels.newChannel(inputStream);
        outputChannel.transferFrom(inputChannel, 0L, totalSize);
        inputChannel.close();
        outputChannel.close();
        randomAccessFile.close();
        inputStream.close();
    }

    /**
     * 创建文件
     * 包含父文件一起视情况去创建
     *
     * @param targetFile 目标文件
     */
    public static void createFile(File targetFile) throws IOException {
        if (!targetFile.getParentFile().exists()) {
            boolean mkdirs = targetFile.getParentFile().mkdirs();
            if (!mkdirs) {
                throw new BPanBusinessException("Failed to mkdirs!");
            }
        }
        boolean newFile = targetFile.createNewFile();
        if (!newFile) {
            throw new BPanBusinessException("Failed to create file!");
        }
    }

    /**
     * 生成默认的文件存储路径
     * <p>
     * 生成规则：当前登录用户的文件目录 + bpan
     *
     * @return 默认路径
     */
    public static String generateDefaultStoreFileRealPath() {
        return System.getProperty("user.home") +
                File.separator +
                "bpan";
    }

    /**
     * 生成默认的文件分片的存储路径前缀
     *
     * @return 前缀
     */
    public static String generateDefaultStoreFileChunkRealPath() {
        return System.getProperty("user.home") +
                File.separator +
                "bpan" +
                File.separator +
                "chunks";
    }

    /**
     * 生成文件分片的存储路径
     * <p>
     * 生成规则：基础路径 + 年 + 月 + 日 + 唯一标识 + 随机的文件名称 + __,__ + 文件分片的下标
     *
     * @param basePath    基础路径
     * @param identifier  标识符
     * @param chunkNumber 块序号
     * @return 路径
     */
    public static String generateStoreFileChunkRealPath(String basePath, String identifier, Integer chunkNumber) {
        return basePath +
                File.separator +
                DateUtil.thisYear() +
                File.separator +
                (DateUtil.thisMonth() + 1) +
                File.separator +
                DateUtil.thisDayOfMonth() +
                File.separator +
                identifier +
                File.separator +
                UUIDUtil.getUuid() +
                BPanConstant.COMMON_SEPARATOR +
                chunkNumber;
    }

    /**
     * 追加写文件
     *
     * @param target 目标文件
     * @param source 源文件
     */
    public static void appendWrite(Path target, Path source) throws IOException {
        Files.write(target, Files.readAllBytes(source), StandardOpenOption.APPEND);
    }

    /**
     * 利用零拷贝技术读取文件内容并写入到文件的输出流中
     *
     * @param fileInputStream 文件输入流
     * @param outputStream    输出流
     * @param length          大小
     * @throws IOException 异常
     */
    public static void writeFile2OutputStream(FileInputStream fileInputStream, OutputStream outputStream, long length) throws IOException {
        FileChannel fileChannel = fileInputStream.getChannel();
        WritableByteChannel writableByteChannel = Channels.newChannel(outputStream);
        fileChannel.transferTo(BPanConstant.ZERO_LONG, length, writableByteChannel);
        outputStream.flush();
        fileInputStream.close();
        outputStream.close();
        fileChannel.close();
        writableByteChannel.close();
    }

    /**
     * 普通的流对流数据传输
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     */
    public static void writeStream2StreamNormal(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != BPanConstant.MINUS_ONE_INT) {
            outputStream.write(buffer, BPanConstant.ZERO_INT, len);
        }
        outputStream.flush();
        inputStream.close();
        outputStream.close();
    }

}
