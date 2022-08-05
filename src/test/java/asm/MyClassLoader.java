package asm;

public class MyClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if ("sample.HelloWorld".equals(name)) {
            byte[] bytes = HelloWorldDump.dump();
            Class<?> aClass = defineClass(name, bytes, 0, bytes.length);
            return aClass;
        }
        throw new ClassNotFoundException("Class Not Found: " + name);
    }
}
