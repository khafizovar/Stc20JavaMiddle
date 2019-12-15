package part1.lesson12.task2;

import java.lang.reflect.Proxy;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

/**
 * Задание 2.     Доработать программу так, чтобы ошибка OutOfMemoryError возникала в Metaspace /Permanent Generation
 *
 * Желательно выставить -XX:MaxMetaspaceSize=64m иначе ждать придется довольно долго
 * @author KhafizovAR on 26.11.2019.
 * @project Stc20JavaMiddle
 */
public class Main {

    private static Map<String, SomeInterface> store = new HashMap<>();

    public static void main(String[] args) {
        run();
    }

    /**
     * Плодим прокси объекты и сохраняем их в хранилище мешая GC их собрать, без хранения
     */
    public static void run() {
        System.out.println("run");
        int i=0;
        try{
            while(true){
                String file = "file:" + i +".jar";
                URL[] jars = new URL[] {new URL(file)};
                URLClassLoader classLdr = new URLClassLoader(jars);
                SomeInterface proxy = (SomeInterface) Proxy.newProxyInstance(classLdr,
                        new Class<?>[]{SomeInterface.class},
                        new SomeInterfaceInvocationHandler(new SomeClass())
                );
                //Мешаем GC
                store.put(file, proxy);
                i++;
            }
        } catch(Throwable error){
            error.printStackTrace();
        }
    }
}
