package com.lx.processors.annotation;

import com.lx.annotations.GifParam;
import com.lx.beans.GifParamBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 处理@GifParam注解
 * <p>
 * 功能：
 * 1. 处理默认GIF参数
 */
public class GifParamProcessor {

    private GifParamProcessor() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(GifParamProcessor.class);

    private static GifParamProcessor gifParamProcessor;

    private static AtomicInteger gifFileNameIncr = new AtomicInteger(0);

    public static GifParamProcessor getInstance() {
        if (gifParamProcessor == null) {
            gifParamProcessor = new GifParamProcessor();
        }
        return gifParamProcessor;
    }

    /**
     * 获取默认的GIF文件参数
     *
     * @param fileName 生成的GIF图片文件的文件名
     * @return GIF文件参数对象
     */
    public GifParamBean getDefaultGifParam(String fileName) {
        int delayTime = 0;
        String gifName = "";
        String gifFilePath = "";
        Class<GifParam> gifParamClass = GifParam.class;
        for (Method method : gifParamClass.getDeclaredMethods()) {
            if ("delayTime".equals(method.getName())) {
                delayTime = (int) method.getDefaultValue();
            }
            if ("gifName".equals(method.getName())) {
                if (fileName != null && fileName.length() != 0) {
                    gifName = fileName;
                } else {
                    gifName = String.valueOf(method.getDefaultValue());
                    String gifDefaultFileName = gifName.substring(0, gifName.lastIndexOf("."));
                    gifDefaultFileName = gifDefaultFileName + gifFileNameIncr.getAndIncrement();
                    gifName = gifDefaultFileName + "." + gifName.substring(gifName.lastIndexOf(".") + 1);
                }
            }
            if ("gifFilePath".equals(method.getName())) {
                gifFilePath = String.valueOf(method.getDefaultValue());
            }
        }
        LOGGER.info("获取到生成的Gif文件的默认参数，delayTime:{}ms，gifFilePath：{}，gifName：{}", delayTime, gifFilePath, gifName);
        return new GifParamBean(delayTime, gifName, gifFilePath);
    }
}
