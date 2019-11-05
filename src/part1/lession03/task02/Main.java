package part1.lession03.task02;

public class Main {
    public static void main(String[] args) {
        ObjectBox ob1 = new ObjectBox();
        ObjectBox ob2 = new ObjectBox();

        ob1.addBox(new Integer(128));
        ob1.addBox(new Integer(128));
        ob1.addBox("222");

        ob2.addBox(new Integer(128));
        ob2.addBox(new Integer(128));
        ob2.addBox("222");

        System.out.println(ob1);
        System.out.println(ob2);
        System.out.println(ob2.equals(ob1));

        System.out.println("****удаление элементов******");
        ob1.deleteObject(new Integer(128));
        System.out.println(ob1);
        ob1.deleteObject(new Integer(128));
        System.out.println(ob1);
    }
}
