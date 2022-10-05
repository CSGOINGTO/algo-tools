package com.lx.utils.asm;

import com.lx.managers.annotation.AnnotationManager;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 分析MainClass类
 * <p>
 * 功能：
 * 1. 获取MainClass类中的mainMethod中所调用的algoMethod
 */
public class MainClassAnalysis {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainClassAnalysis.class);

    /**
     * MainClass中mainMethod中所调用的algoMethod
     * MainClass -> mainMethod List -> algoMethod List
     */
    public static final Map<Class<?>, List<Map<Method, List<Method>>>> mainCLass2mainMethod2algoMethodListMap = new HashMap<>();

    /**
     * 获取MainClass类中所有的mainMethod中所调用的algoMethod
     *
     * @throws IOException
     */
    public void analysis() throws IOException {
        /*
         * 1. 获取到所有的MainClass及MainClass类中所有的mainMethod
         * 2. 在mainMethod中获取所调用的所有algoMethod
         * 3. 获取mainMethod中调用的algoMethod的参数
         */
        Map<Class<?>, List<Method>> mainClass2mainMethodNameListMap = AnnotationManager.mainClass2mainMethodNameListMap;
        for (Map.Entry<Class<?>, List<Method>> entry : mainClass2mainMethodNameListMap.entrySet()) {
            Class<?> analysisMainClass = entry.getKey();
            // 获取mainClass的全量名
            String analysisMainClassName = analysisMainClass.getName();

            ClassReader classReader = new ClassReader(analysisMainClassName);

            List<Method> mainMethodNameList = entry.getValue();
            for (Method mainMethod : mainMethodNameList) {
                String mainMethodName = mainMethod.getName();
                LOGGER.debug("对{}类进行分析，查找该类中{}方法所处理的所有的algoMethod！", analysisMainClassName, mainMethodName);
                MainClassVisitor mainClassVisitor = new MainClassVisitor(Opcodes.ASM9, null, mainMethod);

                classReader.accept(mainClassVisitor, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
                LOGGER.debug("【重要】{}类中的{}方法中所有调用的algoMethod方法：{}", analysisMainClassName, mainMethodName, Collections.unmodifiableMap(mainClassVisitor.mainMethodName2algoMethodList));
                List<Map<Method, List<Method>>> mapList = mainCLass2mainMethod2algoMethodListMap.getOrDefault(analysisMainClass, new ArrayList<>());
                mapList.add(mainClassVisitor.mainMethodName2algoMethodList);
                mainCLass2mainMethod2algoMethodListMap.put(analysisMainClass, mapList);

                // TODO 获取algoMethod的调用参数
                MainMethodVisitor mainMethodVisitor = new MainMethodVisitor(Opcodes.ASM9, null, mainMethod, mainClassVisitor.mainMethodName2algoMethodList.get(mainMethod));
                
            }
        }
    }
}
