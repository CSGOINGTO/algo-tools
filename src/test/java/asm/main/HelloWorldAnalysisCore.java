package asm.main;

import asm.complexity.FileUtils;
import asm.utils.MethodFindInvokeVisitor;
import asm.utils.MethodFindRefVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 分析HelloWorld类中void test(int, int)方法中调用了哪些方法
 */
public class HelloWorldAnalysisCore {
    public static void main(String[] args) {
        String filePath = FileUtils.getFilePath("./sample/HelloWorld.class");
        byte[] bytes = FileUtils.readBytes(filePath);

        ClassReader classReader = new ClassReader(bytes);

        ClassVisitor classVisitor = new MethodFindInvokeVisitor(Opcodes.ASM9, null, "test", "(II)V");

        classReader.accept(classVisitor, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);

        ClassVisitor classVisitor1 = new MethodFindRefVisitor(Opcodes.ASM9, null, "sample/HelloWorld", "test", "(II)V");
        classReader.accept(classVisitor1, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
    }
}
