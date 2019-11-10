package part1.lesson05.pojo;

import java.util.Objects;
import java.util.UUID;

/**
 * Класс домашнего животного
 * @author KhafizovR by 10.11.2019
 * Stc20JavaMiddle
 */
public class Pet implements Comparable<Pet> {
    private UUID id;
    private String name;
    private Person owner;
    private Double weight;

    public Pet(UUID id, String name, Person owner, Double weight) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.weight = weight;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Double.compare(pet.weight, weight) == 0 &&
                Objects.equals(id, pet.id) &&
                Objects.equals(name, pet.name) &&
                Objects.equals(owner, pet.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner, weight);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int compareTo(Pet o) {
        if (this.getOwner().compareTo(o.getOwner()) < 0) {
            return -1;
        } else if( this.getName().compareTo(o.getName()) == 0) {
            if (this.getWeight() > o.getWeight()) {
                return -1;
            }
        }
        return 0;
    }
}
