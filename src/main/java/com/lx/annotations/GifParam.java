package com.lx.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 生成Gif图片的参数
 * 可以用于方法和类上
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface GifParam {

    /**
     * 生成GIF图片之间间隔时间，单位为ms，默认为500ms
     */
    int delayTime() default 500;

    /**
     * 生成GIF文件名，默认gif.gif
     */
    String gifName() default "gif.gif";

    /**
     * 生成GIF文件的路径，默认当前项目路径
     */
    String gifFilePath() default "./";

}
