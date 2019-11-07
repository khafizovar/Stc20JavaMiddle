package part1.lession02.task03;

/**
 * Person
 * @author KhafizovAR
 */
public class Person  implements Comparable<Person>  {
    private int age;
    private String sex;
    private String name;

    public enum SexEnum {
        MAN, WOMAN
    }

    Person(int age, String name, SexEnum sex){
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
}
