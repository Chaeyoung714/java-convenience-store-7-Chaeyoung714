package store;

import java.time.LocalDate;

public class Promotion {
    private String name;
    private int buyAmount;
    private int getAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isAvailable;

    public Promotion(String name, String buyAmount, String getAmount, String startDate, String endDate) {
        this.name = name; //이름 중복 검증 필요!
        this.buyAmount = Integer.parseInt(buyAmount);
        this.getAmount = Integer.parseInt(getAmount);
        this.startDate = transferToDate(startDate);
        this.endDate = transferToDate(endDate);
        this.isAvailable = (this.getAmount != 0);
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
}
