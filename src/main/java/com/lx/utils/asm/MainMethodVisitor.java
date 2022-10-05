package com.lx.utils.asm;

import org.objectweb.asm.MethodVisitor;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 分析mainMethod中所调用的algoMethod
 * <p>
 * 1. 将algoMethod调用时的参数获取到
 */
public class MainMethodVisitor extends MethodVisitor {

    private Method mainMethod;

    private List<Method> algoMethodList;

    public MainMethodVisitor(int api, MethodVisitor methodVisitor, Method mainMethod, List<Method> algoMethodList) {
        super(api, methodVisitor);
        this.mainMethod = mainMethod;
        this.algoMethodList = algoMethodList;
    }

    @Override
    public void visitCode() {
        super.visitCode();
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }
}

