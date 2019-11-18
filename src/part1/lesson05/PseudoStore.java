package part1.lesson05;

import part1.lesson05.pojo.Person;
import part1.lesson05.pojo.Pet;

import java.util.*;


/**
 * Класс хранилище для списка Pet'ов
 * @author KhafizovR by 10.11.2019
 * Stc20JavaMiddle
 */
public class PseudoStore {
    private static List<Pet> store = new LinkedList<>();

    /**
     * Метод добавления нового Pet
     * @param p
     * @throws RowExistException
     */
    public static void add(Pet p) throws RowExistException {
        if(store.contains(p)) {
            throw new RowExistException("дублирование записи");
        } else {
            store.add(p);
        }
    }

    /**
     * Вывод на экран списка животных в отсортированном порядке. Поля для сортировки –  хозяин, кличка животного, вес.
     */
    public static void printItems() {
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
    public static Pet findByPetName(String name) {
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
    public static boolean updatePetByUUID(UUID p, String name, Person owner, Double weight) {
        if(p == null || (name == null && owner == null && weight == null))
            return false;
        for(Pet pet : store) {
            if(pet.getId().equals(p)) {
                if(name != null)
                    pet.setName(name);
                if(owner != null)
                    pet.setOwner(owner);
                if(weight != null) {
                    pet.setWeight(weight);
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
