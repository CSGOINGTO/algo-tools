package sample;

import com.lx.main.Solution;

import java.math.BigDecimal;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("01234".substring(2));
    }
//
//    public void test() {
//        int n = 20;
//        int m = 30;
//        int left = (n + m + 1) / 2;
//        int right = (n + m + 2) / 2;
//        System.out.println(left * right);
//    }
//
    public void test(int a, int b) {
        int c = Math.addExact(a, b);
        String line = String.format("%d + %d = %d", a, b, c);
        System.out.println(line);
    }

    public int test(String name, int age, long idCard, Object obj) {
//        System.out.println(name);
//        System.out.println(age);
//        System.out.println(idCard);
//        System.out.println(obj);
        int hashCode = 0;
        hashCode += name.hashCode();
        hashCode += age;
        hashCode += (int) (idCard % Integer.MAX_VALUE);
        hashCode += obj.hashCode();
//        System.out.println(hashCode);
        return hashCode;
    }

    public void testObject() {
        try {
            Solution.combinationSum2(new int[]{1, 2, 3, 5, 1000}, 100);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new String("hello").equals("h");
    }


    public void sum() {
        test(1000, 20000);
    }
//
//    public void sub() {
//        test(3, 45);
//    }

}
