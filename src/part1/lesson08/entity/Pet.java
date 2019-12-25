package part1.lesson08.entity;

import java.io.Serializable;

/**
 * @author KhafizovAR on 18.11.2019.
 * @project Stc20JavaMiddle
 */
public class Pet implements Serializable {

    private String name;
    private Double weight;

    public Pet() {
    }


    public Pet(String name, Double weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                '}';
    }
}
