package com.lx.utils;

import com.lx.annotations.GifParam;
import com.lx.annotations.ProcessMethod;
import com.lx.beans.GifParamBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

/**
 * 处理注解的工具
 */
public class AnnotationUtils {

    /**
     * 所有的算法处理方法的列表
     */
    public static final List<String> processMethodList = new ArrayList<>();

    /**
     * 所有的算法处理方法最终所要生成的Gif文件的参数
     */
    public static final Map<String, GifParamBean> processMethodGifParamMap = new HashMap<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationUtils.class);

    private static final Set<Class<?>> annotationClassSet = new HashSet<>();

    private static final Set<Class<?>> mainClassSet = new HashSet<>();

    /**
     * 加载AnnotationUtils
     */
    public synchronized static void loadAnnotationsUtils(String annotationPackageName, String mainPackageName) {
        AnnotationUtils.processAnnotation(annotationPackageName, mainPackageName);
    }

    /**
     * 处理所有的注解
     */
    private static void processAnnotation(String annotationPackageName, String mainPackageName) {
        // 1. 获取packageName中的所有的注解类
        findAnnotationClassByPackageName(annotationPackageName);
        // 2. 获取mainPackageName中的所有的处理类
        findAnnotationClassByPackageName(mainPackageName);

        mainClassSet.forEach((mc) -> {
            Method[] methods = mc.getDeclaredMethods();
            for (Method method : methods) {
                String processMethodName = mc.getPackage().getName() + "." + mc.getName() + "." + method.getName();
                // 3. 获取所有的处理算法
                if (method.isAnnotationPresent(ProcessMethod.class)) {
                    LOGGER.info("获取到一个处理算法{}", processMethodName);
                    processMethodList.add(processMethodName);

                    // 4. 获取处理算法最终生成的Gif参数
                    GifParam gifParamAnnotation = method.getAnnotation(GifParam.class);
                    if (gifParamAnnotation != null) {
                        processMethodGifParamMap.put(processMethodName, new GifParamBean(gifParamAnnotation.delayTime(), gifParamAnnotation.gifName(), gifParamAnnotation.gifFilePath()));
                    } else {
                        processMethodGifParamMap.put(processMethodName, getDefaultGifParam());
                    }
                }

            }
        });
    }

    private static GifParamBean getDefaultGifParam() {
        int delayTime = 0;
        String gifName = "";
        String gifFilePath = "";
        Class<GifParam> gifParamClass = GifParam.class;
        for (Method method : gifParamClass.getDeclaredMethods()) {
            if ("delayTime".equals(method.getName())) {
                delayTime = (int) method.getDefaultValue();
            }
            if ("gifName".equals(method.getName())) {
                gifName = String.valueOf(method.getDefaultValue());
            }
            if ("gifFilePath".equals(method.getName())) {
                gifFilePath = String.valueOf(method.getDefaultValue());
            }
        }
        LOGGER.info("获取到生成的Gif文件的默认参数，delayTime:{}ms，gifFilePath：{}，gifName：{}", delayTime, gifFilePath, gifName);
        return new GifParamBean(delayTime, gifName, gifFilePath);
    }

    /**
     * 获取packageName包中的所有的注解类
     *
     * @param packageName 包路径，例如com/lx/annotations
     */
    private static void findAnnotationClassByPackageName(String packageName) {
        try {
            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(packageName);
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    LOGGER.info("获取{}下的注解类", filePath);
                    findAnnotationClassesInPackageByFile(packageName, filePath);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取packagePath包中的所有的注解类
     *
     * @param packageName 基础包名
     * @param packagePath 所要获取注解类的包路径
     */
    private static void findAnnotationClassesInPackageByFile(String packageName, String packagePath) {
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        File[] dirFiles = dir.listFiles((file) -> file.isDirectory() || file.getName().endsWith(".class"));
        if (dirFiles == null || dirFiles.length == 0) return;
        String className = "";
        try {
            for (File file : dirFiles) {
                if (file.isDirectory()) {
                    findAnnotationClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath());
                } else {
                    className = packageName.replace("/", ".") + "." + file.getName().substring(0, file.getName().lastIndexOf(".class"));
                    LOGGER.info("准备加载{}", className);
                    Class<?> aClass = Thread.currentThread().getContextClassLoader().loadClass(className);
                    if (aClass.isAnnotation()) {
                        annotationClassSet.add(aClass);
                        LOGGER.info("注解类{}加载成功！", aClass.getName());
                    }
                    if (!aClass.isAnnotation()) {
                        mainClassSet.add(aClass);
                        LOGGER.info("处理类{}加载成功", aClass.getName());
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            LOGGER.error("加载{}失败！", className);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AnnotationUtils.loadAnnotationsUtils("com/lx/annotations", "com/lx/main");
        System.out.println(processMethodList);
        System.out.println(processMethodGifParamMap);
    }
}
