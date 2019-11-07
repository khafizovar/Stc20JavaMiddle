package part1.lession02.task03;

/**
 * Person
 * @author KhafizovAR
 */
public class Person {
    private int age;
    private String sex;
    private String name;

    public enum SexEnum {
        MAN, WOMAN
    }

    Person(int age, String name, SexEnum sex) {
        this.age = age;
        this.name = name;
        this.sex = sex.name();
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    @Override
    public String toString() {
        return "[" + name + "; " + sex + "; " + age + "]";
    }
}
