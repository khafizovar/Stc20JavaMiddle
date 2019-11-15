package part1.lesson06.task02;

import java.io.*;
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
public class Main {
    private static Random rnd = new Random();

    private static final int MAX_LETTERS_COUNT = 15;                    //Максимальное количество слов в слове
    private static final int MAX_WORDS_COUNT = 15;                      //Максимальное количество слов в предложении
    private static final int MAX_SENTENCES_COUNT = 20;                  //Максимальное количество предложений в абзаце
    private static final int MIN_SENTENCE_SIZE = 1;                     //Минимальная длинна предложения

    private static final String[] sentenceFinalizer = {".","!","?"};

    public static void main(String[] args) throws UnsupportedEncodingException {
        getFiles("files/trash", 10, 1048576, new String [] {"bla","bla-bla","bla-bla-bla"}, 20);
    }

    /**
     * генерация заданного количества файлов
     * @param path          директория для генерации файлов
     * @param n             количество генерируемых файлов
     * @param size          размер файлов в байтах
     * @param words         Массив заданных слов
     * @param probability   Вероятность использования словв из массива words, от 0 до 100
     */
    private static void getFiles(String path, int n, int size, String[] words, int probability) throws UnsupportedEncodingException {
        System.out.println("Генерация файлов...");
        File file = new File(path);
        if(!file.isDirectory()) {
            throw new IllegalArgumentException("Неверно указана директория.");
        }

        for(int i = 0; i< n; i++) {
            //Генерируем абзацы
            StringBuilder builder = new StringBuilder();
            do {
                builder.append(getGeneratedParagraphWithMyDictionary(MAX_WORDS_COUNT, MAX_LETTERS_COUNT, MAX_SENTENCES_COUNT, Arrays.asList(words), (double)probability / 100));
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
     * @param wordsCount    Количество слов в предложении
     * @param maxWordSize   Максимальная количество букв в слове
     * @param myDictionary  Словарь слов
     * @param probability   вероятность от 0 до 1, определяющий что будет использовано слово из словаря
     * @return  Строка с сгнерерированным предложением.
     */
    private static String getGeneratedSentenceWithMyDictionary(int wordsCount, int maxWordSize, List<String> myDictionary, double probability) {

        StringBuilder builder = new StringBuilder();
        //Первое слово
        String firstString = getRandomString((int)getRandomDoubleBetweenRange(1, (double) maxWordSize));
        //Заглавная буква
        builder.append(firstString.substring(0, 1).toUpperCase() + firstString.substring(1));
        //Генерация слов
        for(int i=0; i<wordsCount; i++) {
            //Берем слово из словаря?
            if(myDictionary.size() > 0 && getRandomDoubleBetweenRange(0, 1) < probability) {
                builder.append(myDictionary.get(rnd.nextInt(myDictionary.size())));
            } else { //Генерируем случайное слово
                builder.append(getRandomString(rnd.nextInt(maxWordSize)));
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
     * Генерация параграфа, на базе генерации предложении и генерации слов
     * @param wordsCount        максимальное количество слов в предложении
     * @param maxWordSize       максимальная длинна слова предложении
     * @param maxSentencesCount    количество предложений в параграфе
     * @return  Строка с сгенерированным параграфом
     */
    private static String getGeneratedParagraphWithMyDictionary(int wordsCount, int maxWordSize, int maxSentencesCount, List<String> dictionary, double probability) {
        StringBuilder builder = new StringBuilder();
        maxSentencesCount = rnd.nextInt(Math.max(wordsCount, MIN_SENTENCE_SIZE));;
        for(int i=0; i<maxSentencesCount; i++) {
            builder.append(getGeneratedSentenceWithMyDictionary(wordsCount, maxWordSize, dictionary, probability));
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
