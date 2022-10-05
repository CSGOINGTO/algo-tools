package com.lx.managers.image;

import com.lx.processors.annotation.AlgoMethodProcessor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 管理DrawImagerListener的管理器
 * 功能：
 * 1. 将algoMethod与DrawImageListener关联
 * 2. 实时监听algoMethod的状态，当运行结束之后，设置对应DrawImageListener的状态
 * 3.
 */
public class DrawImageListenerManager {

    /**
     * 存放已经运行结束的algoMethod
     */
    private BlockingDeque<String> runFinishAlgoMethodQueue = new LinkedBlockingDeque<>();

    /**
     * 存放algoMethod与DrawImageListener的映射
     * key：algoMethodName
     * value：DrawImageListener
     */
    private Map<String, DrawImageListener> algoMethodListenerMap = new ConcurrentHashMap<>();

    private static DrawImageListenerManager drawImageListenerManager;

    private List<String> allAlgoMethodList;

    public DrawImageListenerManager() {
        // 加载AnnotationUtils获取所有的algoMethod
        AlgoMethodProcessor algoMethodProcessor = AlgoMethodProcessor.getInstance();
        algoMethodProcessor.loadAnnotations("com/lx/annotations", "com/lx/main");
        allAlgoMethodList = algoMethodProcessor.processMethodList;
    }

    public static DrawImageListenerManager getInstance() {
        if (drawImageListenerManager == null) {
            synchronized (DrawImageListenerManager.class) {
                if (drawImageListenerManager == null) {
                    drawImageListenerManager = new DrawImageListenerManager();
                }
            }
        }
        return drawImageListenerManager;
    }

    private void processAllAlgoMethod() {
        allAlgoMethodList.forEach(mn -> algoMethodListenerMap.put(mn, new DrawImageListener(mn)));
    }

    private void getRunFinishAlgoMethod() {

    }


}
