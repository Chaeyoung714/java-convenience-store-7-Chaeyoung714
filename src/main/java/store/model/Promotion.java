package store.model;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.util.Date;

public class Promotion {
    private final String name;
    private final int buyAmount;
    private final int getAmount;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private Promotion(String name, int buyAmount, int getAmount, LocalDate startDate, LocalDate endDate) {
        validatePositiveNumber(buyAmount);
        validatePositiveNumber(getAmount);
        this.name = name;
        this.buyAmount = buyAmount;
        this.getAmount = getAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Promotion from(String name, String buyAmount, String getAmount, String startDate, String endDate) {
        try {
            return new Promotion(name
                    , Integer.parseInt(buyAmount)
                    , Integer.parseInt(getAmount)
                    , transferToLocalDateFrom(startDate)
                    , transferToLocalDateFrom(endDate));
        } catch (NumberFormatException e) {
            throw new IllegalStateException("[SYSTEM] 잘못된 수량입니다.");
        }
    }

    private void validatePositiveNumber(int number) {
        if (number <= 0) {
            throw new IllegalStateException("[SYSTEM] 잘못된 수량입니다.");
        }
    }

    private static LocalDate transferToLocalDateFrom(String dateString) {
        try {
            String[] date = dateString.split("-");
            return LocalDate.of(Integer.parseInt(date[0])
                    , Integer.parseInt(date[1])
                    , Integer.parseInt(date[2]));
        } catch (NullPointerException | NumberFormatException e) {
            throw new IllegalStateException("[SYSTEM] 잘못된 날짜입니다.");
        }
    }

    public boolean isOngoing() { //실시간 바뀌므로 변수로 굳이 관리 x
        LocalDate date = DateTimes.now().toLocalDate();
        return (startDate.isEqual(date) || startDate.isBefore(date))
                && (endDate.isEqual(date) || endDate.isAfter(date));
    }

    public String getName() {
        return name;
    }

    public int getBuyAmount() {
        return buyAmount;
    }

    public int getGetAmount() {
        return getAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
