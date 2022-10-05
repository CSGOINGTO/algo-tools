package com.lx.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileUtils {

    /**
     * 获取当前项目中所有的文件夹
     */
    public static List<File> getProjectAllDirectory() {
        List<File> allDirectoryList = new ArrayList<>();
        // 1. 获取当前项目的根路径
        String projectRootPath = Objects.requireNonNull(FileUtils.class.getResource("/")).getPath();
        File file = new File(projectRootPath);
        getAllDirectory(file, allDirectoryList);
        return allDirectoryList;
    }

    /**
     * 获取rootFile目录下的所有的文件夹
     */
    private static List<File> getAllDirectory(File rootFile, List<File> allDirectoryList) {
        if (rootFile.isDirectory()) {
            allDirectoryList.add(rootFile);
            File[] files = rootFile.listFiles();
            for (File file : files) {
                getAllDirectory(file, allDirectoryList);
            }
        }
        return allDirectoryList;
    }

    public static void writeBytes(String filepath, byte[] bytes) throws IOException {
        File file = new File(filepath);
        File dirFile = file.getParentFile();
        mkdirs(dirFile);

        try (OutputStream out = new FileOutputStream(filepath);
             BufferedOutputStream buff = new BufferedOutputStream(out)) {
            buff.write(bytes);
            buff.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mkdirs(File dirFile) {
        boolean file_exists = dirFile.exists();

        if (file_exists && dirFile.isDirectory()) {
            return;
        }

        if (file_exists && dirFile.isFile()) {
            throw new RuntimeException("Not A Directory: " + dirFile);
        }

        if (!file_exists) {
            dirFile.mkdirs();
        }
    }

    public static void main(String[] args) {
        String filepath = "file:\\D:\\git-project\\algo-tools\\target\\test-classes\\com\\lx\\main\\MainClassTest2.class";

        try (OutputStream out = new FileOutputStream(filepath); BufferedOutputStream buff = new BufferedOutputStream(out)) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
