package part1.lession02.task03;

/**
 * Person
 * @author KhafizovAR
 */
public class Person {
    // закомментированный код
    //private final static String MAN = "man";
    //private final static String WOMAN = "woman";

    private int age;
    private String sex;
    private String name;

    public enum SexEnum {
        MAN, WOMAN
    }

    //мёртвый код
    Person() { }

    Person(int age, String name, SexEnum sex) {
        this.age = age;
        this.name = name;
        this.sex = sex.name();
    }

    public int getAge() {
        return age;
    }

    //сеттеры мёртвый код
    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(SexEnum sex) {
        this.sex = sex.name();
    }

    @Override
    public String toString() {
        return "[" + name + "; " + sex + "; " + age + "]";
    }
}
