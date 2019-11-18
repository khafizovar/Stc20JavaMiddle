package part1.lesson08;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

/**
 * Простой сериализатор обрабатывающий примитивы и ссылочные типы
 * @author KhafizovAR on 18.11.2019.
 * @project Stc20JavaMiddle
 */
public class SimpleSerializer {
    /**
     * Сериализация объекта в файл, объект должен быть {@link Serializable}
     * @param object  объект для сериализации
     * @param file    наименование файла
     */
    public void serialize (Object object, String file) {
            System.out.println("---------------- write to file -----------------------");
            try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
                //Записали наименование класса
                dos.writeUTF(object.getClass().getCanonicalName());
                writeToFile(object, dos);
            } catch (IOException | IllegalAccessException e) {
                e.printStackTrace();
            }
    }

    /**
     * Десериализация объекта из файла
     * @param file  Файл-источник для чтения объекта
     * @return  Экземпляр объекта без приведения типа
     */
    public Object deSerialize(String file) {
        System.out.println("---------------- restore from file -----------------------");
        try(DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            //читаем наименование класса
            Class<?> className = Class.forName(dis.readUTF());
            return restoreFromFile(className, dis);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void writeToFile(Object obj, DataOutputStream dos) throws IllegalAccessException, IOException {
        if(obj instanceof Serializable) {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field declaredField : fields) {
                declaredField.setAccessible(true); // доступ к приватному полю
                switch (declaredField.getType().getSimpleName()) {
                    case "Double":
                    case "double": {
                        dos.writeDouble((double) declaredField.get(obj));
                        break;
                    }
                    case "Integer":
                    case "int": {
                        dos.writeInt((int) declaredField.get(obj));
                        break;
                    }
                    case "Boolean":
                    case "boolean": {
                        dos.writeBoolean((boolean) declaredField.get(obj));
                        break;
                    }
                    case "Byte":
                    case "byte": {
                        dos.writeByte((byte) declaredField.get(obj));
                        break;
                    }
                    case "Char":
                    case "char": {
                        dos.writeByte((char) declaredField.get(obj));
                        break;
                    }
                    case "Float":
                    case "float": {
                        dos.writeFloat((float) declaredField.get(obj));
                        break;
                    }
                    case "Long":
                    case "long": {
                        dos.writeLong((long) declaredField.get(obj));
                        break;
                    }
                    case "String": {
                        dos.writeUTF((String) declaredField.get(obj));
                        break;
                    }
                    default: { //is it object?
                        this.writeToFile(declaredField.get(obj), dos);
                    }
                }
            }
        } else {
            throw new NotSerializableException(obj.getClass().toString());
        }
    }

    private Object restoreFromFile(Class clazz, DataInputStream dis) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException, ClassNotFoundException {
            Field[] fields = clazz.getDeclaredFields();
            Constructor<?> ctor = clazz.getConstructor();
            Object obj = ctor.newInstance();
            for (Field declaredField : fields) {
                declaredField.setAccessible(true);
                if(declaredField.getName().equals("serialVersionUID")) {
                    long sviFromFile = dis.readLong();
                    if(sviFromFile != (long) declaredField.get(obj)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("serialVersionID fromFile:" + sviFromFile + "\n");
                        sb.append("serialVersionID local:" + (long) declaredField.get(obj) + "\n");
                        throw new InvalidClassException(sb.toString());
                    }
                    continue;
                }
                switch (declaredField.getType().getSimpleName()) {
                    case "Double":
                    case "double": {
                        declaredField.set(obj, dis.readDouble()); break;
                    }
                    case "Integer":
                    case "int": {
                        declaredField.set(obj, dis.readInt()); break;
                    }
                    case "Boolean":
                    case "boolean": {
                        declaredField.set(obj, dis.readBoolean()); break;
                    }
                    case "Byte":
                    case "byte": {
                        declaredField.set(obj, dis.readByte()); break;
                    }
                    case "Char":
                    case "char": {
                        declaredField.set(obj, dis.readChar()); break;
                    }
                    case "Float":
                    case "float": {
                        declaredField.set(obj, dis.readFloat()); break;
                    }
                    case "Long":
                    case "long": {
                        declaredField.set(obj, dis.readLong()); break;
                    }
                    case "String": {
                        declaredField.set(obj, dis.readUTF()); break;
                    }
                    default: { //is it object?
                        Object subObj = this.restoreFromFile(Class.forName(declaredField.getType().getCanonicalName()), dis);
                        declaredField.set(obj, subObj);
                    }
                }
            }
            return obj;
    }

    /**
     * Метод для вывода информации по полям объекта
     * @param obj
     * @throws IllegalAccessException
     */
    public static void printFields(Object obj) throws IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field declaredField : fields) {
            System.out.print(
                    Modifier.toString(declaredField.getModifiers()) + " " +
                            declaredField.getType().getSimpleName() + " " +
                            declaredField.getName() + ": ");
            declaredField.setAccessible(true); // доступ к приватному полю
            System.out.println(declaredField.get(obj));
        }
    }
}
