package part1.lesson06.task01;

import java.io.*;
import java.util.*;

/**
 *
 * Задание 1. Написать программу, читающую текстовый файл. Программа должна составлять отсортированный по алфавиту список слов, найденных в файле и сохранять его в файл-результат.
 * Найденные слова не должны повторяться, регистр не должен учитываться. Одно слово в разных падежах – это разные слова.
 * @author KhafizovAR on 12.11.2019.
 * @project Stc20JavaMiddle
 */
public class Main {

    public static void main(String[] args) {
        WordCounter.run("files/source.txt", "files/words.txt");
    }

    /**
     * Класс извлечения слов
     */
    static class WordCounter {
        /**
         * Запуск процесса извлечения слов
         * @param fileName          наименование/путь файла исходника
         * @param outputFileName    наименование/путь результирующего файла со списком найденных слов
         */
        public static void run(String fileName, String outputFileName) {
            Set<String> words = readFile(fileName);
            List<String> list = new ArrayList<String>(words);
            Collections.sort(list);
            writeToFile(list, outputFileName);
        }

        /**
         * Метод чтения файлов
         * @param fileName  наименование файла
         * @return
         */
        private static Set<String> readFile(String fileName) {
            Set<String> words = new HashSet<>();
            try(FileReader fr = new FileReader(fileName)) {
                BufferedReader br = new BufferedReader(fr);
                String s;
                while ((s = br.readLine()) != null)
                    for (String substr : s.split("[\\p{Punct}\\s]+")) {
                        words.add(substr.toLowerCase());
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return words;
        }

        /**
         * Метод записи реузльтата
         * @param words     список слов для записи
         * @param fileName  наименование выходного файла
         */
        private static void writeToFile(List<String> words, String fileName) {
            try(FileWriter fw = new FileWriter(fileName)) {
                fw.write("-------------" + new Date().toString() + "-------------\n");
                for ( String s: words ) {
                    fw.write(s + "\n");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
