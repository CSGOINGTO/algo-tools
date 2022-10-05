package asm.utils;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.util.Printer;

import java.util.ArrayList;
import java.util.List;

/**
 * 将指定的方法中调用的方法打印出来
 */
public class MethodFindInvokeVisitor extends ClassVisitor {

    private final String methodName;

    private final String methodDesc;

    public MethodFindInvokeVisitor(int api, ClassVisitor classVisitor, String methodName, String methodDesc) {
        super(api, classVisitor);
        this.methodName = methodName;
        this.methodDesc = methodDesc;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if (methodName.equals(name) && methodDesc.equals(descriptor)) {
            return new MethodFindInvokeAdapter(api, null);
        }
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    private static class MethodFindInvokeAdapter extends MethodVisitor {

        private final List<String> list = new ArrayList<>();

        public MethodFindInvokeAdapter(int api, MethodVisitor methodVisitor) {
            super(api, methodVisitor);
        }

        /**
         * 获取当前方法的调用详情
         */
        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
            String info = String.format("%s %s.%s %s", Printer.OPCODES[opcode], owner, name, descriptor);
            if (!list.contains(info)) {
                list.add(info);
            }
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }

        /**
         * 当前方法调用完毕后调用
         */
        @Override
        public void visitEnd() {
            list.forEach(System.out::println);
            super.visitEnd();
        }
    }
}
