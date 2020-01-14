package part1.lesson11.task1;

import java.math.BigInteger;
import java.util.function.Function;

/**
 * Класс по расчету факториала для запуска в потоке
 * @author KhafizovAR on 15.11.2019.
 * @project Stc20JavaMiddle
 */
public class Factorial implements Runnable {
    /** Число для расчета факториала */
    private final int n;
    private final Function<Integer, BigInteger> fn;

    Factorial(int n, Function<Integer, BigInteger> fn) { this.n = n; this.fn = fn;}

    /**
     * Метод расчета факториала с учетом ранее расчитанных, но без участия в расчете
     * @return
     */
    private BigInteger factorial() {
        return fn.apply(n);
    }

    @Override
    public void run() {
           this.factorial();
    }
}
