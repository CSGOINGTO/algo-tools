package com.lx.managers.clazz;

import com.lx.managers.annotation.AnnotationManager;
import com.lx.processors.asm.MainClassProcessor;
import com.lx.utils.asm.MainClassAnalysis;

import java.io.IOException;

/**
 * 处理项目中所有的MainClass的总流程
 *
 */
public class MainClassManager {

    public void process() throws IOException {
        // 1. 分析项目中所有的MainClass
        new MainClassAnalysis().analysis();
        // 2. 将项目中原MainClass处理为所需要的格式
        new MainClassProcessor().mainClassAddParam();
    }
}
