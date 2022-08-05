package asm;

public class Test {

    @org.junit.Test
    public void helloWorldRun() throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        MyClassLoader classLoader = new MyClassLoader();
        Class<?> clazz = classLoader.loadClass("sample.HelloWorld");
        Object instance = clazz.newInstance();
        System.out.println(instance);

    }

}
