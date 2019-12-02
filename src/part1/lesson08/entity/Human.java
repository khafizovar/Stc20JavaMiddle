package part1.lesson08.entity;

import java.io.Serializable;

/**
 * @author KhafizovAR on 18.11.2019.
 * @project Stc20JavaMiddle
 */
public class Human implements Serializable {

    private static final long serialVersionUID = 7550397792441749340L;

    private String type;
    private String name;
    private int money;
    private Integer salary;
    private Pet pet;

    public Human() { }

    public Human(String type, String name, int money, Integer salary, Pet pet) {
        this.type = type;
        this.name = name;
        this.money = money;
        this.salary = salary;
        this.pet = pet;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    @Override
    public String toString() {
        return "Human{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", money=" + money +
                ", salary=" + salary +
                ", pet=" + pet +
                '}';
    }
}