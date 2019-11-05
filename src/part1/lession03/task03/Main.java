package part1.lession03.task03;

/**
 * Задание 3. Доработать классы MathBox и ObjectBox таким образом, чтобы MathBox был наследником ObjectBox.
 * Необходимо сделать такую связь, правильно распределить поля и методы.
 * Функциональность в целом должна сохраниться.
 * При попытке положить Object в MathBox должно создаваться исключение.
 */
public class Main {
    public static void main(String[] args) {
        Number[] data = {20,20,30,40,50,71,71,80,90,91, null};
        ObjectBox mb = new MathBox(data);

        System.out.println(mb.toString());
        mb.addBox(new Double(123));
        mb.addBox(new Integer(1235));
        mb.addBox(new Float(999));
        System.out.println(mb.toString());
        mb.deleteObject(new Float(999));
        System.out.println(mb.toString());
        System.out.println(((MathBox)mb).summator());

        mb.addBox(new Object());
    }
}
