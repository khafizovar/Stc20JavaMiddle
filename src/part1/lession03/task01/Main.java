package part1.lession03.task01;

import helpers.Helper;

public class Main {
    private static final int MAX_VALUE = 100;
    private static final int MIN_VALUE = -100;
    public static void main(String[] args) throws Exception {
        //MathBox mb = new MathBox(Helper.getArrayOfRandomInts(10,MIN_VALUE,MAX_VALUE));
        Number[] data = {20,20,30,40,50,71,71,80,90,91, null};
        MathBox mb = new MathBox(data);

        mb.print();
        System.out.println(mb.summator());
        mb.splitter(15);
        mb.print();
        System.out.println(mb.summator());

    }
}
