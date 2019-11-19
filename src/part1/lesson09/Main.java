package part1.lesson09;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * Дан интерфейс
 *
 * public interface Worker {
 *
 *     void doWork();
 *
 * }
 *
 * Необходимо написать программу, выполняющую следующее:
 *
 * Программа с консоли построчно считывает код метода doWork. Код не должен требовать импорта дополнительных классов.
 * После ввода пустой строки считывание прекращается и считанные строки добавляются в тело метода public void doWork() в файле SomeClass.java.
 * Файл SomeClass.java компилируется программой (в рантайме) в файл SomeClass.class.
 * Полученный файл подгружается в программу с помощью кастомного загрузчика
 * Метод, введенный с консоли, исполняется в рантайме (вызывается у экземпляра объекта подгруженного класса)
 *
 * @author KhafizovAR on 19.11.2019.
 * @project Stc20JavaMiddle
 */
public class Main {

    public static void main(String[] args) throws Exception {
        List<String> str = consoleReader();
        str.forEach(System.out::println);
        String fileToCompile = writeToJava(str, "SomeClass.java");
        compilationTask(Arrays.asList(new File(fileToCompile)));
        Worker w = useCustomClassLoader();
        //Выполняем то что ввели с консоли
        w.doWork();
    }

    /**
     * Читаем строки из консоли
     * @return  список строк введеных из консоли
     */
    private static List<String> consoleReader() {
        System.out.println("Ожидается ввод строк метода void doWork();");
        List<String> str = new ArrayList<String>();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String currentStr = "";
            do {
                currentStr = br.readLine();
                str.add(currentStr);
            } while( currentStr.length() > 0);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Ввод окончен. Попытка компиляции");
        return str;
    }

    /**
     * Дописываем наш файл с шаблоном и сохраняем в рабочей директории
     * @param additionalCode    код который будет добавлен к нашему классу
     * @param fileName          наименование входного файла, в данном случае SomeClass.java
     * @return                  Полный путь с именем файла к вновь созданному файлу на основе шаблона
     * @throws IOException
     */
    private static String writeToJava(List<String> additionalCode, String fileName) throws IOException {
        String fullFileName = System.getProperty("user.dir") + "\\src\\part1\\lesson09\\" + fileName;
        //Сперва считываем содержимое файла
        List<String> list = Files.readAllLines(new File(fullFileName).toPath(), Charset.defaultCharset());
        //Сносим сигнатуру package
        list = list.subList(1, list.size());
        //Поиск сигнатуры doWork
        for (int i=1; i<list.size();i++) {
            if(list.get(i).indexOf("doWork()") > -1) {
               for(int j=additionalCode.size()-1; j>=0; j--) {
                   list.add(i + 1, additionalCode.get(j));
               }
                break;
            }
        }
        list.forEach(System.out::println);
        //теперь записываем в директорию
        String newFileName = System.getProperty("user.dir") + "\\files\\" + fileName;
        FileWriter fw = new FileWriter(newFileName);
        for (String s: list) {
            fw.write(s);
        }
        fw.close();
        return newFileName;
    }

    /**
     * Метод выполнябщий компиляцию в рантайме, подробнее https://docs.oracle.com/javase/8/docs/api/javax/tools/JavaCompiler.html
     * @param filesToCompile    Файлы для компиляции
     * @throws IOException
     */
    private static void compilationTask(List<java.io.File> filesToCompile) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        Iterable<? extends JavaFileObject> compilationUnits1 =
                fileManager.getJavaFileObjectsFromFiles(filesToCompile);
        compiler.getTask(null, fileManager, null, null, null, compilationUnits1).call();

        fileManager.close();
    }

    /**
     * Используем кастомный Лоадер
     * @throws Exception
     */
    private static Worker useCustomClassLoader() throws Exception {
        SomeClassLoader cl = new SomeClassLoader();
        Class<?> kindClass = cl.loadClass("SomeClass");
        return (Worker) kindClass.newInstance();
    }
}
