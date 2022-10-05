package com.lx.utils.asm;

import org.objectweb.asm.ClassVisitor;

/**
 * 在MainClass中添加方法
 * <p>
 * 功能：
 * 1. 根据MainClass中的全局变量paramPrefix为每个algoMethod生成helpMethod，用来跟踪algoMethod的执行情况
 */
public class MainClassAddHelpMethodVisitor extends ClassVisitor {

    private final int methodAccess;
    private final String methodName;
    private final String methodDesc;
    private final String methodSignature;
    private final String[] methodExceptions;

    public MainClassAddHelpMethodVisitor(int api, ClassVisitor classVisitor, int methodAccess, String methodName, String methodDesc, String signature, String[] exceptions) {
        super(api, classVisitor);
        this.methodAccess = methodAccess;
        this.methodName = methodName;
        this.methodDesc = methodDesc;
        this.methodSignature = signature;
        this.methodExceptions = exceptions;
    }
}
