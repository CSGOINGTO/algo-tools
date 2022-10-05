package com.lx.processors.asm;

import com.lx.managers.annotation.AnnotationManager;
import com.lx.utils.FileUtils;
import com.lx.utils.asm.MainClassAddFiledVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 处理MainClass
 * <p>
 * 功能：
 * 1. 为每个MainClass添加algoMethod所对应的全局变量param
 * 2. 为其中所处理的algoMethod创建新的方法，在新的方法中处理algoMethod，并跟踪algoMethod是否执行完毕
 */
public class MainClassProcessor {

    public void mainClassAddParam() throws IOException {
        List<Class<?>> mainClassList = AnnotationManager.mainClassList;
        for (Class<?> mainClass : mainClassList) {
            // 1. 读取初始MainClass
            ClassReader classReader = new ClassReader(mainClass.getName());

            // 2. 创建ClassWriter，COMPUTE_FRAMES参数可以让ASM自动计算ClassFile中的max stacks、max locals和stack map frames
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

            // 3. 串连ClassWriter
            MainClassAddFiledVisitor mainClassAddFiledVisitor = new MainClassAddFiledVisitor(Opcodes.ASM9, cw, mainClass);
            classReader.accept(mainClassAddFiledVisitor, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
            byte[] bytes = cw.toByteArray();
            String classPath = Thread.currentThread().getContextClassLoader().getResource(".").getPath() + mainClass.getName().replace(".", File.separator) + ".class";
            FileUtils.writeBytes(classPath, bytes);
        }
    }

    public void mainClassAddMethod() {

    }

}
