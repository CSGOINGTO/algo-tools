package com.lx.managers;

import com.lx.managers.annotation.AnnotationManager;
import com.lx.managers.clazz.MainClassManager;
import com.lx.processors.asm.MainClassProcessor;
import com.lx.utils.asm.MainClassAnalysis;

import java.io.IOException;

public class MainManager {

    public static void process() throws IOException {
        // 1. 处理项目中所有的注解
        new AnnotationManager().process();
        // 2. 处理项目中所有的MainClass
        new MainClassManager().process();
    }

}
