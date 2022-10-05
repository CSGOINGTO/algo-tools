package com.lx.utils.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 在MainClass类中为每个algoMethod创建一个全局变量param，用来标记algoMethod是否执行完毕
 */
public class MainClassAddFiledVisitor extends ClassVisitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainClassAddFiledVisitor.class);

    /**
     * MainClass中AlgoMethod与生成的paramName之间的映射
     * MainClass -> (algoMethod -> paramName)List
     */
    public static final Map<Class<?>, List<Map<Method, String>>> mainClass2AlgoMethodParamMap = new HashMap<>();

    /**
     * 属性名自增器
     */
    private final AtomicInteger PARAM_SUFFIX_INC = new AtomicInteger(1);

    /**
     * 当前所访问的MainClass
     */
    private final Class<?> mainClass;

    /**
     * 添加属性的前缀
     */
    private final String paramPrefix = "paramPrefix";

    /**
     * 当前MainClass中mainMethod调用的所有的algoMethod
     */
    private final List<Method> algoMethodInMainClassList = new ArrayList<>();

    public MainClassAddFiledVisitor(int api, ClassVisitor classVisitor, Class<?> mainClass) {
        super(api, classVisitor);
        this.mainClass = mainClass;
        MainClassAnalysis.mainCLass2mainMethod2algoMethodListMap.get(mainClass).forEach(map -> map.values().forEach(algoMethodInMainClassList::addAll));
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        if (algoMethodInMainClassList.size() > 0) {
            if (access == Opcodes.ACC_PUBLIC && name.equals(paramPrefix + PARAM_SUFFIX_INC.get()) && Type.getType(Boolean.class).getDescriptor().equals(descriptor) && signature == null && (Integer) value == 0) {
                LOGGER.debug("MainClass：{}中已经存在全局变量（Filed）Boolean param：{}", mainClass.getName(), name);
                List<Map<Method, String>> algoMethod2ParamNameList = mainClass2AlgoMethodParamMap.getOrDefault(mainClass, new ArrayList<>());
                addAlgoMethodParam(algoMethod2ParamNameList, algoMethodInMainClassList.get(0), name);
                algoMethodInMainClassList.remove(0);
                PARAM_SUFFIX_INC.incrementAndGet();
                mainClass2AlgoMethodParamMap.put(mainClass, algoMethod2ParamNameList);
            }
        }
        return super.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public void visitEnd() {
        // 1. 获取到当前mainClass中所有的algoMethod
        List<Map<Method, String>> algoMethod2ParamNameList = mainClass2AlgoMethodParamMap.getOrDefault(mainClass, new ArrayList<>());
        // 2. 遍历algoMethod
        for (Method algoMethod : algoMethodInMainClassList) {
            // 2.1 为每个algoMethod创建一个boolean类型的param的全局变量，默认值为false
            String paramName = paramPrefix + PARAM_SUFFIX_INC.get();
            super.visitField(Opcodes.ACC_PUBLIC, paramName, Type.getDescriptor(Boolean.class), null, false);
            LOGGER.debug("MainClass：{}中新增全局变量Boolean {}", mainClass.getName(), paramName);
            // 2.2 将algoMethod与新创建的全局变量关联起来
            addAlgoMethodParam(algoMethod2ParamNameList, algoMethod, paramName);
            // 2.3 param属性名后缀自增
            PARAM_SUFFIX_INC.incrementAndGet();
        }
        // 3. 将获取到的algoMethod与新创建的全局变量保存起来
        mainClass2AlgoMethodParamMap.put(mainClass, algoMethod2ParamNameList);
        super.visitEnd();
    }

    private void addAlgoMethodParam(List<Map<Method, String>> algoMethod2ParamNameList, Method algoMethod, String paramName) {
        Map<Method, String> algoMethod2ParamName = new HashMap<>(1);
        algoMethod2ParamName.put(algoMethod, paramName);
        algoMethod2ParamNameList.add(algoMethod2ParamName);
        LOGGER.debug("【注意】MainClass：{}中的algoMethod：{}所对应的paramName为{}", mainClass.getName(), algoMethod.getName(), paramName);
    }
}
