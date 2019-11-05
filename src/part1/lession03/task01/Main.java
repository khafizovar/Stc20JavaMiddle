package part1.lession03.task01;

import helpers.Helper;

public class Main {
    private static final int MAX_VALUE = 100;
    private static final int MIN_VALUE = -100;
    public static void main(String[] args) throws Exception {
        //MathBox mb = new MathBox(Helper.getArrayOfRandomInts(10,MIN_VALUE,MAX_VALUE));
        Number[] data = {20,20,30,40,50,71,71,80,90,91, null};
        MathBox mb = new MathBox(data);
        Number[] data2 = {20,20,30,50,40,71,71,80,90,91, null};
        MathBox mb2 = new MathBox(data2);

        System.out.println(mb.toString());
        System.out.println(mb.summator());
        //mb.splitter(15);
        //mb2.splitter(15);
        System.out.println(mb.toString());
        System.out.println(mb.summator());
        System.out.println(mb2.summator());
        System.out.println("-----------hashCode;equals------------");
        System.out.println(mb.toString());
        System.out.println(mb2.toString());
        System.out.println("mb:" + mb.hashCode());
        System.out.println("mb2:" + mb2.hashCode());
        System.out.println("equals:" + mb.equals(mb2));
    }
}
