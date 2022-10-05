package asm.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HelloWorldRun {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<?> clazz = Class.forName("sample.HelloWorld");
        Method m = clazz.getDeclaredMethod("test", String.class, int.class, long.class, Object.class);
        Object o = clazz.newInstance();
        m.invoke(o, "param1", 1, 100L, "param4");
//
//        Method m1 = clazz.getDeclaredMethod("test");
//        m1.invoke(o);
    }
}
