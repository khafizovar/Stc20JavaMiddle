package part1.lesson05.pojo;

import java.util.Objects;
import java.util.UUID;

/**
 * Класс домашнего животного
 * @author KhafizovR by 10.11.2019
 * Stc20JavaMiddle
 */
public class Pet implements Comparable<Pet> {
    private final UUID id;
    private String name;
    private Person owner;
    private Double weight;

    public Pet(String name, Person owner, Double weight) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.owner = owner;
        this.weight = weight;
    }

    public UUID getId() {
        return id;
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

        if (name != null ? !name.equals(pet.name) : pet.name != null) return false;
        if (owner != null ? !owner.equals(pet.owner) : pet.owner != null) return false;
        return weight != null ? weight.equals(pet.weight) : pet.weight == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        return result;
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
            if (this.getWeight() < o.getWeight()) {
                return -1;
            }
        }
        return 0;
    }
}
