package store.model.promotion;

import java.time.DateTimeException;
import java.time.LocalDate;
import store.util.DateMonitor;

public class Promotion {
    private static final String DATE_DELIMITER = "-";
    private static final int YEAR = 0;
    private static final int MONTH = 1;
    private static final int DAY = 2;

    private final String name;
    private final int buyAmount;
    private final int getAmount;
    private final int bundleAmount;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private Promotion(String name, int buyAmount, int getAmount, LocalDate startDate, LocalDate endDate) {
        validatePositiveNumber(buyAmount);
        validatePositiveNumber(getAmount);
        this.name = name;
        this.buyAmount = buyAmount;
        this.getAmount = getAmount;
        this.bundleAmount = buyAmount + getAmount;
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
            String[] date = dateString.split(DATE_DELIMITER);
            return LocalDate.of(Integer.parseInt(date[YEAR])
                    , Integer.parseInt(date[MONTH])
                    , Integer.parseInt(date[DAY]));
        } catch (NullPointerException | NumberFormatException | DateTimeException e) {
            throw new IllegalStateException("[SYSTEM] 잘못된 날짜입니다.");
        }
    }

    public boolean isOngoing() {
        LocalDate date = DateMonitor.today();
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

    public int getBundleAmount() {
        return bundleAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
