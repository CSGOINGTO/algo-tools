package com.lx.utils.asm;

import com.lx.utils.CommonUtils;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 在MainMethod中找到所有调用的AlgoMethod
 */
public class AlgoMethodFindInvokeAdapter extends MethodVisitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlgoMethodFindInvokeAdapter.class);

    /**
     * mainMethod中调用的所有的algoMethod
     */
    public final List<Method> algoMethodListInMainClass = new ArrayList<>();

    public final Map<Object, List<Method>> algoClassInstance2algoMethodList = new HashMap<>();

    public AlgoMethodFindInvokeAdapter(int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
    }

    @Override
    public void visitTypeInsn(int opcode, String type) {
        // 获取到mainMethod中创建的AlgoClass对象
        List<Class<?>> algoClassList = CommonUtils.isAlgoClass(type);
        if (opcode == Opcodes.NEW && algoClassList.size() == 1) {
            Class<?> algoClass = algoClassList.get(0);
            try {
                algoClassInstance2algoMethodList.put(algoClass.newInstance(), new ArrayList<>());
            } catch (InstantiationException | IllegalAccessException e) {
                LOGGER.error("反射创建AlgoClass：{}实例对象失败！原因为：{}", algoClass.getName(), e.getMessage());
                e.printStackTrace();
            }
        }
        super.visitTypeInsn(opcode, type);
    }

    /**
     * 访问MainMethod方法中被调用的所有方法，当被调用的方法在algoMethod列表中时，则加入到algoMethodListInMainClass列表中
     */
    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        LOGGER.debug("判断被调用的方法是否在algoMethod列表中！opcode：{}，owner：{}，name：{}，descriptor：{}，isInterface：{}", opcode, owner, name, descriptor, isInterface);
        // 判断被调用的方法是否在algoMethod列表中
        List<Method> algoMethodList = CommonUtils.isAlgoMethod(owner, name, descriptor);
        if (algoMethodList != null && algoMethodList.size() == 1) {
            algoMethodListInMainClass.add(algoMethodList.get(0));
        }
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }
}
