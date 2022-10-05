package com.lx.utils;

import com.lx.managers.annotation.AnnotationManager;
import org.objectweb.asm.Type;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public class CommonUtils {

    /**
     * 判断Class是否是可用类
     */
    public static boolean isClass(Class<?> clazz) {
        return !(clazz.isAnnotation()
                && clazz.isInterface()
                && clazz.isArray()
                && clazz.isEnum()
                && clazz.isSynthetic()
                && clazz.isLocalClass()
                && clazz.isAnonymousClass()
                && clazz.isMemberClass());
    }

    /**
     * 获取方法的异常列表
     */
    public static String[] getMethodExceptions(Method method) {
        int exceptionsLen = method.getExceptionTypes().length;
        if (exceptionsLen == 0) return null;
        String[] methodExceptionArr = new String[exceptionsLen];

        Class<?>[] methodExceptionTypes = method.getExceptionTypes();
        for (int i = 0; i < methodExceptionTypes.length; i++) {
            Class<?> exceptionType = methodExceptionTypes[i];
            methodExceptionArr[i] = exceptionType.getName().replace(".", "/");
        }
        return methodExceptionArr;
    }

    /**
     * 通过方法的描述判断该方法是否为algoMethod
     *
     * @param owner      方法所在的包名
     * @param name       方法名
     * @param descriptor 方法描述信息，形参类型和返回值类型
     * @return 符合方法描述的algoMethod列表
     */
    public static List<Method> isAlgoMethod(String owner, String name, String descriptor) {
        return AnnotationManager.algoMethodList
                .stream()
                .filter(method -> (method.hashCode() ^ Type.getMethodDescriptor(method).hashCode()) == (owner.replace("/", ".").hashCode() ^ name.hashCode() ^ descriptor.hashCode()))
                .collect(Collectors.toList());
    }

    /**
     * 通过className判断该Class是否是AlgoClass
     */
    public static List<Class<?>> isAlgoClass(String className) {
        return AnnotationManager.algoClassList
                .stream()
                .filter(clazz -> clazz.getName().equals(className))
                .collect(Collectors.toList());
    }


}
