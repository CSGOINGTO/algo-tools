package com.lx.utils.ImageUtils;

import java.lang.reflect.Method;

/**
 * 功能：
 * 1. 实时监听DrawImage的任务是否完成
 * 2. 实时监听本次绘制GIF的任务是否添加完成
 */
public class DrawImageListener {

    /**
     * 本次绘制GIF的任务是否添加完成
     */
    public volatile boolean isAddCompleted = false;

    /**
     * DrawImage的任务是否完成
     */
    public volatile boolean isTaskCompleted = false;

    public void judgeTaskAddCompleted() {
        Runtime.getRuntime().traceMethodCalls(true);
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            String methodName = stackTraceElement.getMethodName();
//            Method method =
        }
    }

}
