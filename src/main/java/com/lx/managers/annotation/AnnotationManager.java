package com.lx.managers.annotation;

import com.lx.annotations.AlgoMethod;
import com.lx.annotations.GifParam;
import com.lx.annotations.MainMethod;
import com.lx.beans.AlgoMethodInfoBean;
import com.lx.beans.GifParamBean;
import com.lx.processors.annotation.GifParamProcessor;
import com.lx.utils.CommonUtils;
import com.lx.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

/**
 * 管理项目中的注解
 * <p>
 * 功能：
 * 1. 扫描当前项目中所有的注解
 * 2. 将扫描到的注解按照类型分配给对应的processor
 */
public class AnnotationManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationManager.class);

    /**
     * 存放当前项目中所有的class类
     */
    public static final List<Class<?>> allClassList = new ArrayList<>();

    /**
     * 存放所有的标记@MainMethod的方法
     */
    public static final List<Method> mainMethodList = new ArrayList<>();

    /**
     * 存放所有的标记有@MainMethod的MainClass
     */
    public static final List<Class<?>> mainClassList = new ArrayList<>();

    /**
     * 存放mainClass中所有的mainMethod
     * mainClass -> mainMethod 的map
     */
    public static final Map<Class<?>, List<Method>> mainClass2mainMethodNameListMap = new HashMap<>();

    /**
     * 存放所有的标记有@AlgoMethod的AlgoClass
     */
    public static final List<Class<?>> algoClassList = new ArrayList<>();

    /**
     * 存放所有标记@AlgoMethod的方法
     */
    public static final List<Method> algoMethodList = new ArrayList<>();

    /**
     * 存放algoMethod所对应的GifParam
     * algoMethod方法 -> GifParamBean
     */
    public static final Map<Method, GifParamBean> algoMethodName2gifParamMap = new HashMap<>();

    /**
     * 存放algoClass中所有的algoMethod
     * algoClass -> algoMethodList 的map
     */
    public static final Map<Class<?>, List<Method>> algoClass2algoMethodNameListMap = new HashMap<>();

    /**
     * gifParamProcessor @GifParam处理器
     */
    private final GifParamProcessor gifParamProcessor = GifParamProcessor.getInstance();


    public void process() {
        findAllClassByPackageName();
        processAllClasses();
    }

    /**
     * 遍历当前项目中所有的class文件，并根据注解类型进行分类处理
     */
    private void processAllClasses() {
        allClassList.forEach((mc) -> {
            // 1. 获取class类中的所有的方法
            Method[] methods = mc.getDeclaredMethods();
            // 2. 遍历所有的方法
            for (Method method : methods) {
                String processMethodName = method.toString();
                LOGGER.debug("当前处理的方法名：{}", processMethodName);
                // 3. 判断方法上是否有@AlgoMethod注解
                if (method.isAnnotationPresent(AlgoMethod.class)) {
                    LOGGER.debug("获取到algoMethod：{}", processMethodName);
                    // 3.1 将被@AlgoMethod注解标记的方法放到algoMethodList中
                    algoMethodList.add(method);
                    // 3.2 处理当前所处理的class类中的所有被@AlgoMethod标记的方法
                    List<Method> algoMethodList = algoClass2algoMethodNameListMap.getOrDefault(mc, new ArrayList<>());
                    algoMethodList.add(method);
                    algoClass2algoMethodNameListMap.put(mc, algoMethodList);
                    // 3.3 将AlgoClass添加到algoClassList
                    if (!algoClassList.contains(mc)) {
                        algoClassList.add(mc);
                    }
                }
                // 4. 判断方法上是否有@MainMethod注解
                if (method.isAnnotationPresent(MainMethod.class)) {
                    LOGGER.debug("获取到mainMethod：{}", processMethodName);
                    // 4.1 将被@MainMethod注解标记的方法放到mainClassList中
                    mainMethodList.add(method);
                    // 4.2 将mainClass添加到mainClassList
                    if (!mainClassList.contains(mc)) {
                        LOGGER.debug("获取到MainClass：{}", mc);
                        mainClassList.add(mc);
                    }
                    // 4.3 处理当前处理的class类中所有被@MainMethod标记的方法
                    List<Method> mainMethodNameList = mainClass2mainMethodNameListMap.getOrDefault(mc, new ArrayList<>());
                    mainMethodNameList.add(method);
                    mainClass2mainMethodNameListMap.put(mc, mainMethodNameList);
                }
                // 5. 判断方法上是否有@GifParam注解和AlgoMethod注解
                if (method.isAnnotationPresent(GifParam.class) && method.isAnnotationPresent(AlgoMethod.class)) {
                    // 5.1 获取@GifParam注解
                    GifParam gifParamAnnotation = method.getAnnotation(GifParam.class);
                    LOGGER.debug("获取到被@GifParam注解标记的方法：{}", processMethodName);
                    // 5.2 将algoMethod方法与解析后的@GifParam注解存放到algoMethod2GifParamMap中
                    algoMethodName2gifParamMap.put(method, new GifParamBean(gifParamAnnotation.delayTime(), gifParamAnnotation.gifName(), gifParamAnnotation.gifFilePath()));
                } else if (method.isAnnotationPresent(AlgoMethod.class)) {
                    // 5.3 将没有标记@GifParam注解的方法与默认的gif生成参数关联起来
                    LOGGER.debug("AlgoMethod：{}，没有使用@GifParam注解，该方法使用默认的Gif图片生成参数！", processMethodName);
                    algoMethodName2gifParamMap.put(method, gifParamProcessor.getDefaultGifParam(processMethodName));
                }
            }
        });
    }


    /**
     * 获取当前项目中的所有的class类
     */
    private void findAllClassByPackageName() {
        try {
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            // 1. 获取当前项目中的所有的资源
            // 1.1 获取当前项目中所有的文件夹
            List<File> projectAllDirectory = FileUtils.getProjectAllDirectory();
            // 1.2 遍历文件夹中所有的资源
            for (File file : projectAllDirectory) {
                String resourcePath = file.getName();
                LOGGER.debug("获取{}文件夹下的所有的资源！", resourcePath);
                Enumeration<URL> resources = contextClassLoader.getResources(resourcePath);
                // 2. 处理每一个资源
                while (resources.hasMoreElements()) {
                    URL url = resources.nextElement();
                    // 2.1 获取资源文件的类型
                    String protocol = url.getProtocol();
                    // 2.2 如果资源文件是file，则继续处理
                    if ("file".equals(protocol)) {
                        String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                        LOGGER.debug("获取{}下的所有的类", filePath);
                        // 3. 从文件中获取所有的class文件
                        findClassesInPackageByFile(resourcePath, filePath);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取packagePath包中的所有的class
     *
     * @param packageName 基础包名
     * @param packagePath 所要获取注解类的包路径
     */
    private void findClassesInPackageByFile(String packageName, String packagePath) {
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
                    findClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath());
                } else {
                    className = packageName.replace("/", ".") + "." + file.getName().substring(0, file.getName().lastIndexOf(".class"));
                    LOGGER.debug("准备加载{}", className);
                    // 加载类
                    Class<?> aClass = Thread.currentThread().getContextClassLoader().loadClass(className);
                    // 判断是否为Class类型
                    if (CommonUtils.isClass(aClass)) {
                        if (!allClassList.contains(aClass)) {
                            allClassList.add(aClass);
                            LOGGER.debug("处理类{}加载成功", aClass.getName());
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            LOGGER.error("加载{}失败！", className);
            e.printStackTrace();
        }
    }

}
