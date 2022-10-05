package com.lx.utils;

import java.util.Arrays;

public class StringUtils {

    public static boolean classArrayAndStringArrayEquals(Class<?>[] classArr, String[] strArr) {
        boolean commEquals = arrayCommEquals(classArr, strArr);
        if (!commEquals) return false;
        if ((classArr == null || strArr == null) && commEquals) return true;
        String[] classNameArr = new String[classArr.length];
        for (int i = 0, classArrLength = classArr.length; i < classArrLength; i++) {
            classNameArr[i] = classArr[i].getName().replace(".", "/");
        }
        return stringArrayEquals(classNameArr, strArr);
    }

    public static boolean stringArrayEquals(String[] strArr1, String[] strArr2) {
        boolean commEquals = arrayCommEquals(strArr1, strArr2);
        if (!commEquals) return false;
        if ((strArr1 == null || strArr2 == null) && commEquals) return true;
        if (strArr1.length != strArr2.length) return false;
        Arrays.sort(strArr1);
        Arrays.sort(strArr2);
        for (int i = 0; i < strArr1.length; i++) {
            if (!strArr1[i].equals(strArr2[i])) return false;
        }
        return true;
    }

    public static String getMethodName(String simpleMethodName, String className) {
        return className.replace("/", ".") + "." + simpleMethodName;
    }

    private static boolean arrayCommEquals(Object[] objArr1, Object[] objArr2) {
        if ((objArr1 == null && objArr2 == null)) {
            return true;
        }
        if ((objArr1 != null && objArr1.length == 0 && objArr2 == null) || (objArr1 == null && objArr2 != null && objArr2.length == 0)) {
            return true;
        }
        if ((objArr1 == null && objArr2 != null && objArr2.length > 0) || (objArr1 != null && objArr1.length > 0 && objArr2 == null))
            return false;
        if (objArr1.length != objArr2.length) return false;
        return true;
    }


}
