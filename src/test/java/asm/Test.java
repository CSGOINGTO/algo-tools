package asm;

import com.lx.main.Solution;
import com.lx.utils.StringUtils;
import org.objectweb.asm.Type;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class Test {

    @org.junit.Test
    public void helloWorldRun() throws ClassNotFoundException, InstantiationException, IllegalAccessException {

//        MyClassLoader classLoader = new MyClassLoader();
//        Class<?> clazz = classLoader.loadClass("sample.HelloWorld");
//        Object instance = clazz.newInstance();
//        System.out.println(instance);

        Class<Solution> aClass = Solution.class;
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            System.out.println("name: " + declaredMethod.getName()); // name
            System.out.println("owner: " + declaredMethod.getDeclaringClass().getName()); // owner
            System.out.println("descriptor: " + Type.getMethodDescriptor(declaredMethod)); // descriptor
            System.out.println("------------------------------------");
        }

    }

    @org.junit.Test
    public void test() {
        int beginSends = -1 * 10 * 3;
        int endSeconds = -1 * 10;

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, beginSends);
        String timeBegin = new SimpleDateFormat("yyyyMMddHHmmss").format(calendar.getTime());
        System.out.println(timeBegin);

        calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, endSeconds);
        String timeEnd = new SimpleDateFormat("yyyyMMddHHmmss").format(calendar.getTime());
        System.out.println(timeEnd);
    }

}
