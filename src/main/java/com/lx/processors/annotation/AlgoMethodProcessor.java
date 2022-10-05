package com.lx.processors.annotation;

import com.lx.beans.GifParamBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 处理@AlgoMethod注解
 * 功能：
 * 1. 扫描当前项目中所有标记@AlgoMethod的方法
 * 2. 将标记@AlgoMethod方法的类中的@AlgoMethod方法汇总
 * 3. TODO 需要实时将AlgoMethod方法中的数据结构的变化提供出去（注意，每个AlgoMethod中会有多个需要跟踪的数据结构）
 */
public class AlgoMethodProcessor {

    /**
     * 所有的算法处理方法的列表
     */
    public final List<String> processMethodList = new ArrayList<>();

    /**
     * 所有的算法处理方法最终所要生成的Gif文件的参数
     */
    public static final Map<String, GifParamBean> processMethodGifParamMap = new HashMap<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(AlgoMethodProcessor.class);

    private final Set<Class<?>> annotationClassSet = new HashSet<>();

    public final Set<Class<?>> mainClassSet = new HashSet<>();

    private static AlgoMethodProcessor algoMethodProcessor;

    public static AlgoMethodProcessor getInstance() {
        if (algoMethodProcessor == null) {
            synchronized (AlgoMethodProcessor.class) {
                if (algoMethodProcessor == null) {
                    algoMethodProcessor = new AlgoMethodProcessor();
                }
            }
        }
        return algoMethodProcessor;
    }

    /**
     * 加载AnnotationUtils
     */
    public synchronized void loadAnnotations(String annotationPackageName, String mainPackageName) {
//        algoMethodProcessor.processAnnotation(annotationPackageName, mainPackageName);
    }


    public static void main(String[] args) {
        AlgoMethodProcessor algoMethodProcessor = AlgoMethodProcessor.getInstance();
        algoMethodProcessor.loadAnnotations("com/lx/annotations", "com/lx/main");
        System.out.println(algoMethodProcessor.processMethodList);
        System.out.println(processMethodGifParamMap);
    }
}
