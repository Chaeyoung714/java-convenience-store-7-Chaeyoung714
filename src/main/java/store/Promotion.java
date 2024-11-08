package store;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;

public class Promotion {
    private final String name;
    private final int buyAmount;
    private final int getAmount;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private boolean isOngoing;

    public Promotion(String name, String buyAmount, String getAmount, String startDate, String endDate,
                     LocalDate today) {
        this.name = name; //이름 중복 검증 필요!
        this.buyAmount = Integer.parseInt(buyAmount);
        this.getAmount = Integer.parseInt(getAmount);
        this.startDate = transferToDate(startDate);
        this.endDate = transferToDate(endDate);
        this.isOngoing = isOngoingToday(today); //ongoing 확인 주기 : 한번 구매 시작할때(재입력 포함!), 즉 최초세팅때
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

    private boolean isOngoingToday(LocalDate date) {
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

    public boolean isOngoing() {
        return isOngoing;
    }
}
