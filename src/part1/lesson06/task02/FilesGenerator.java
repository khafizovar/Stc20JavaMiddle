package part1.lesson06.task02;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * Задание 2. Создать генератор текстовых файлов, работающий по следующим правилам:
 *
 * Предложение состоит из 1<=n1<=15 слов. В предложении после произвольных слов могут находиться запятые.
 * Слово состоит из 1<=n2<=15 латинских букв
 * Слова разделены одним пробелом
 * Предложение начинается с заглавной буквы
 * Предложение заканчивается (.|!|?)+" "
 * Текст состоит из абзацев. в одном абзаце 1<=n3<=20 предложений. В конце абзаца стоит разрыв строки и перенос каретки.
 * Есть массив слов 1<=n4<=1000. Есть вероятность probability вхождения одного из слов этого массива в следующее предложение (1/probability).
 * Необходимо написать метод getFiles(String path, int n, int size, String[] words, int probability), который создаст n файлов размером size в каталоге path. words - массив слов, probability - вероятность.
 *
 * @author KhafizovAR on 12.11.2019.
 * @project Stc20JavaMiddle
 */
public class FilesGenerator {
    private static Random rnd = new Random();

    private static final int MAX_LETTERS_COUNT = 15;                    //Максимальное количество слов в слове
    private static final int MIN_LETTERS_COUNT = 1;                    //Максимальное количество слов в слове
    private static final int MAX_WORDS_COUNT = 15;                      //Максимальное количество слов в предложении
    private static final int MIN_WORDS_COUNT = 1;
    private static final int MAX_SENTENCES_COUNT = 20;                  //Максимальное количество предложений в абзаце
    private static final int MIN_SENTENCE_SIZE = 1;                     //Минимальная длинна предложения

    private static final String[] sentenceFinalizer = {".","!","?"};
    private static List<String> wordsDictionary = new ArrayList<>();

    /**
     * генерация заданного количества файлов, эта сигнатура определена
     * @param path          директория для генерации файлов
     * @param n             количество генерируемых файлов
     * @param size          размер файлов в байтах
     * @param words         Массив заданных слов
     * @param probability   Вероятность использования словв из массива words, от 0 до 100
     */
    static void getFiles(String path, int n, int size, String[] words, int probability) throws UnsupportedEncodingException {
        wordsDictionary = Arrays.asList(words);
        System.out.println("Генерация файлов...");
        File file = new File(path);
        if(!file.isDirectory()) {
            throw new IllegalArgumentException("Неверно указана директория.");
        }

        for(int i = 0; i< n; i++) {
            //Генерируем абзацы
            StringBuilder builder = new StringBuilder();
            do {
                builder.append(getGeneratedParagraphWithMyDictionary((double)probability / 100));
                //Пока размер не больше чем указано
            } while (builder.toString().getBytes("UTF-8").length < size);

            byte[] toWrite = Arrays.copyOfRange(builder.toString().getBytes(), 0, size);
            try (FileOutputStream fos = new FileOutputStream(path + "/" + getRandomString(5) + ".txt", true)) {
                fos.write(toWrite);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Генерация завершена, выходная директория: " + file.getAbsolutePath());
    }

    /**
     * Метод генерации предложения.
     * @param probability   вероятность от 0 до 1, определяющий что будет использовано слово из словаря
     * @return  Строка с сгнерерированным предложением.
     */
    private static String getGeneratedSentenceWithMyDictionary(double probability) {

        StringBuilder builder = new StringBuilder();
        //Первое слово
        String firstString = getRandomString((int)getRandomDoubleBetweenRange(MIN_LETTERS_COUNT, rnd.nextInt(MAX_LETTERS_COUNT)));
        //Заглавная буква
        builder.append(firstString.substring(0, 1).toUpperCase() + firstString.substring(1));
        //Случайное количество слов в пределах диапазона
        int wordsCount = Math.max(rnd.nextInt(MAX_WORDS_COUNT), MIN_WORDS_COUNT);
        //Генерация слов
        for(int i=0; i<wordsCount; i++) {
            //Берем слово из словаря?
            if(wordsDictionary.size() > 0 && getRandomDoubleBetweenRange(0, 1) < probability) {
                builder.append(wordsDictionary.get(rnd.nextInt(wordsDictionary.size())));
            } else { //Генерируем случайное слово
                builder.append(getRandomString((int)getRandomDoubleBetweenRange(MIN_LETTERS_COUNT, rnd.nextInt(MAX_LETTERS_COUNT))));
            }
            //Добавляем пробел если не последнее слово
            if ( i != wordsCount-1) {
                //Возможно ставим запятую
                if(rnd.nextBoolean())
                    builder.append(',');
                builder.append(" ");
            }
        }
        //Окончание предложения
        builder.append(sentenceFinalizer[rnd.nextInt(sentenceFinalizer.length)] + " ");
        return builder.toString();
    }

    /**
     *
     * @param probability   вероятность использования слоа из слоаря в предложении
     * @return Строка с сгенерированным параграфом
     */
    private static String getGeneratedParagraphWithMyDictionary(double probability) {
        StringBuilder builder = new StringBuilder();
        //Случайное количество предложений в параграфе в пределах диапазона
        int maxSentencesCount = Math.max(rnd.nextInt(MAX_SENTENCES_COUNT), MIN_SENTENCE_SIZE);
        for(int i=0; i<maxSentencesCount; i++) {
            builder.append(getGeneratedSentenceWithMyDictionary(probability));
        }
        builder.append("\n\r");
        return builder.toString();
    }

    /**
     * Генерация случайного слова от 'a' до 'z'
     * @param targetStringLength  длинна генерируемой строки
     * @return
     */
    private static String getRandomString(int targetStringLength) {
        int aCode = 97;
        int zCode = 122;
        StringBuilder str = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = aCode + (int)
                    (rnd.nextFloat() * (zCode - aCode + 1));
            str.append((char) randomLimitedInt);
        }
        return str.toString();
    }

    /**
     * Генерация числа double от min до max
     * @param min   минимальная граница
     * @param max   максимальная граница
     * @return  случайное числочисло
     */
    private static double getRandomDoubleBetweenRange(double min, double max){
        double x = (Math.random()*((max-min)+1))+min;
        return x;
    }
}
