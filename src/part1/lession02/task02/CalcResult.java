package part1.lession02.task02;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс содержащий результат вычислений
 * @author KhafizovAR on 07.11.2019.
 * @project Stc20JavaMiddle
 */
public class CalcResult {
    private List<Integer> satisfyDigits = new ArrayList<>();
    private List<Integer> negativeNumbers = new ArrayList<>();

    public CalcResult(List<Integer> satisfyDigits, List<Integer> negativeNumbers) {
        this.satisfyDigits = satisfyDigits;
        this.negativeNumbers = negativeNumbers;
    }

    public List<Integer> getSatisfyDigits() {
        return satisfyDigits;
    }

    public List<Integer> getNegativeNumbers() {
        return negativeNumbers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CalcResult that = (CalcResult) o;

        if (satisfyDigits != null ? !satisfyDigits.equals(that.satisfyDigits) : that.satisfyDigits != null)
            return false;
        return negativeNumbers != null ? negativeNumbers.equals(that.negativeNumbers) : that.negativeNumbers == null;
    }

    @Override
    public int hashCode() {
        int result = satisfyDigits != null ? satisfyDigits.hashCode() : 0;
        result = 31 * result + (negativeNumbers != null ? negativeNumbers.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CalcResult{" +
                "satisfyDigits=" + satisfyDigits +
                ", negativeNumbers=" + negativeNumbers +
                '}';
    }
}
