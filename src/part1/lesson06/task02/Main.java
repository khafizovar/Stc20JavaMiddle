package part1.lesson06.task02;

import java.io.UnsupportedEncodingException;

/**
 * @author KhafizovR by 24.11.2019
 * Stc20JavaMiddle
 */
public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException {
        FilesGenerator.getFiles("files/trash", 10, 1048576, new String [] {"bla","bla-bla","bla-bla-bla"}, 20);
    }
}
