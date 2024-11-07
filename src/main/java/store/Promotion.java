package store;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;

public class Promotion {
    private final String name;
    private final int buyAmount;
    private final int getAmount;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final boolean isAvailable;
    private boolean isOngoing;

    public Promotion(String name, String buyAmount, String getAmount, String startDate, String endDate) {
        this.name = name; //이름 중복 검증 필요!
        this.buyAmount = Integer.parseInt(buyAmount);
        this.getAmount = Integer.parseInt(getAmount);
        this.startDate = transferToDate(startDate);
        this.endDate = transferToDate(endDate);
        this.isAvailable = (this.getAmount != 0);
        this.isOngoing = isOngoing(DateTimes.now().toLocalDate());
    }

    private LocalDate transferToDate(String dateInput) {
        try {
            String[] date = dateInput.split("-");
            return LocalDate.of(Integer.parseInt(date[0])
                    , Integer.parseInt(date[1])
                    , Integer.parseInt(date[2]));
        } catch (NullPointerException e) {
            return null;
        }
    }

    public void checkIsOngoing(LocalDate date) {
        if (isOngoing(date)) {
            this.isOngoing = true;
            return;
        }
        this.isOngoing = false;
    }

    private boolean isOngoing(LocalDate date) {
        if (!isAvailable) {
            return false;
        }
        return (isAvailable
                && startDate.isEqual(date) || startDate.isBefore(date))
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

    public boolean isAvailable() {
        return isAvailable;
    }

    public boolean isOngoing() {
        return isOngoing;
    }
}
