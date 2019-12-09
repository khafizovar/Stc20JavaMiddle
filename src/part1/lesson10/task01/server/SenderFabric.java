package part1.lesson10.task01.server;

import java.io.IOException;

/**
 * @author KhafizovAR on 09.12.2019.
 * @project Stc20JavaMiddle
 */
public class SenderFabric {
    /**
     * Получить стратегию отправки
     * @param name
     * @return
     * @throws IOException
     */
    public static Send getSenderInstance(String name) throws IOException {
        switch(name) {
            case "withPm": {
                return new SendAllAndPm();
            }
            default: {
                return new SendAllOnly();
            }
        }
    }
}
