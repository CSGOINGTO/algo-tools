package com.lx.managers.annotation;

import org.junit.Test;


public class AnnotationManagerTest {

    @Test
    public void process() {
        AnnotationManager annotationManager = new AnnotationManager();
        annotationManager.process();
        System.out.println("当前项目中所有的class类---------------");

        AnnotationManager.allClassList.forEach(c -> System.out.println(c.getName()));

        System.out.println("当前项目中所有标记@MainMethod注解的方法---------------");

        AnnotationManager.mainMethodList.forEach(System.out::println);

        System.out.println("当前项目中所有标记@AlgoMethod注解的方法---------------");

        AnnotationManager.algoMethodList.forEach(method -> {
            System.out.println(method.getName());
            System.out.println(method.getModifiers());
        });


        System.out.println("AlgoMethod对应的Gif参数---------------");

        AnnotationManager.algoMethodName2gifParamMap.forEach((k, v) -> System.out.println(k.getName() + v));

        System.out.println("AlgoClass中，所有被调用的algoMethod---------------");

        AnnotationManager.algoClass2algoMethodNameListMap.forEach((k, v) -> {
            System.out.println(k + ": ");
            v.forEach(System.out::println);
        });
    }
}