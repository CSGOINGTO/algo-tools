package asm.main;

import asm.complexity.FileUtils;
import asm.utils.MethodAroundVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

/**
 * 修改HelloWorld类中的方法
 */
public class HelloWorldTransformCore {

    public static void main(String[] args) {
        String filePath = FileUtils.getFilePath("./sample/HelloWorld.class");
        byte[] bytes = FileUtils.readBytes(filePath);

        ClassReader classReader = new ClassReader(bytes);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        ClassVisitor methodEnterVisitor = new MethodAroundVisitor(Opcodes.ASM9, classWriter);
        classReader.accept(methodEnterVisitor, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
        byte[] bytes1 = classWriter.toByteArray();
        FileUtils.writeBytes(filePath, bytes1);
    }

}
