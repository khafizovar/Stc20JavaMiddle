package part1.lesson05;

import part1.lesson05.pojo.Pet;

import java.util.*;


/**
 * @author KhafizovR by 10.11.2019
 * Stc20JavaMiddle
 */
public class PseudoStore {
    List<Pet> store = new ArrayList<>();

    /**
     *
     * @param p
     * @throws RowExistException
     */
    void add(Pet p) throws RowExistException {
        if(store.contains(p)) {
            throw new RowExistException("дублирование записи");
        } else {
            store.add(p);
        }
    }

    /**
     * Вывод на экран списка животных в отсортированном порядке. Поля для сортировки –  хозяин, кличка животного, вес.
     */
    void printItems() {
        Collections.sort(store, new Comparator<Pet>() {
            @Override
            public int compare(Pet o1, Pet o2) {
                return o1.compareTo(o2);
            }
        });
        for (Pet p1: store) {
            System.out.println(p1.toString());
        }
    }

    /**
     * Поиск по имени
     * @param name
     * @return
     */
    Pet findByPetName(String name) {
        for(Pet pet : store) {
            if(pet.getName().equals(name)) {
                return pet;
            }
        }
        return null;
    }

    /**
     * Изменение элемента по UUID
     * @param p
     * @return
     */
    boolean updatePetByUUID(Pet p) {
        for(Pet pet : store) {
            if(pet.getId().equals(p.getId())) {
                if(p.getName() != null)
                    pet.setName(p.getName());
                if(p.getOwner() != null)
                    pet.setOwner(p.getOwner());
                if(p.getWeight() != null) {
                    pet.setWeight(p.getWeight());
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Исключение сущестования записи
     */
    static class RowExistException extends Exception {
        public RowExistException(String message) {
            super(message);
        }
    }
}
