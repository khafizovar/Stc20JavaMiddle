package part1.lesson11;

import java.math.BigInteger;
import java.util.Map;
import java.util.function.Function;

/**
 * @author KhafizovAR on 25.11.2019.
 * @project Stc20JavaMiddle
 */
public interface NumAndBool {
    Map<Integer, BigInteger> run(int [] numbers, Function<Integer, BigInteger> fn);
}
