package com.lx.utils.asm;

import com.lx.utils.StringUtils;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 访问MainClass类
 * <p>
 * 功能：
 * 1. 访问MainMethod方法，获取其中调用的所有algoMethod方法
 */
public class MainClassVisitor extends ClassVisitor {

    /**
     * MainClass中所有的mainMethod与其中所有调用的algoMethod之间的map
     * <p>
     * mainMethod -> algoMethod List
     */
    public final Map<Method, List<Method>> mainMethodName2algoMethodList = new HashMap<>();

    private AlgoMethodFindInvokeAdapter algoMethodFindInvokeAdapter;

    /**
     * mainMethod方法
     */
    private final Method mainMethod;

    public MainClassVisitor(int api, ClassVisitor classVisitor, Method mainMethod) {
        super(api, classVisitor);
        this.mainMethod = mainMethod;
    }

    /**
     * 访问mainClass中的mainMethod方法
     * <p>
     * 功能：
     * 1. 获取mainMethod方法中调用的algoMethod
     * 2. 获取调用algoMethod的参数
     */
    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        // 判断当前访问的方法是否为为mainMethod，如果是，则进入mainMethod方法中，获取其中调用的algoMethod
        if (mainMethod.getModifiers() == access && mainMethod.getName().equals(name) && Type.getMethodDescriptor(mainMethod).equals(descriptor) && StringUtils.classArrayAndStringArrayEquals(mainMethod.getExceptionTypes(), exceptions)) {
            algoMethodFindInvokeAdapter = new AlgoMethodFindInvokeAdapter(api, null);
            return algoMethodFindInvokeAdapter;
        }
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    /**
     * 访问mainMethod之后，需要把获取到的algoMethodList存放起来
     */
    @Override
    public void visitEnd() {
        if (algoMethodFindInvokeAdapter != null && algoMethodFindInvokeAdapter.algoMethodListInMainClass.size() != 0) {
            mainMethodName2algoMethodList.put(mainMethod, algoMethodFindInvokeAdapter.algoMethodListInMainClass);
        }
        super.visitEnd();
    }
}
