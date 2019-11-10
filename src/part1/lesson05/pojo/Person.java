package part1.lesson05.pojo;

import java.util.Objects;

/**
 * Класс хозяина домашнего животного
 * @author KhafizovR by 10.11.2019
 * Stc20JavaMiddle
 */
public class Person implements Comparable<Person> {

    private int age;
    private String sex;
    private String name;

    public enum SexEnum {
        MAN, WOMAN
    }

    public Person(int age, String name, SexEnum sex) {
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
    public int compareTo(Person o) {
        if (this.getSex().compareTo(o.getSex()) < 0) {
            return -1;
        } else if( this.getSex().compareTo(o.getSex()) == 0) {
            if (this.getAge() > o.getAge()) {
                return -1;
            } else if(this.getAge() == o.getAge()) {
                if (this.getName().compareTo(o.getName()) < 0) {
                    return -1;
                }
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", sex='" + sex + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                Objects.equals(sex, person.sex) &&
                Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, sex, name);
    }
}
