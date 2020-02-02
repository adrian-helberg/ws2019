package test;

public class Test implements Cloneable {
    public static void main(String[] args) throws CloneNotSupportedException {
        Test test = new Test();
        Test test2 = (Test) test.clone();
        System.out.println(test == test2);
        Integer.va
    }
}
