package store;

import java.time.LocalDate;
import java.util.Date;

public class Promotion {
    private String name;
    private int buyAmount;
    private int getAmount;
    private LocalDate startDate;
    private LocalDate endDate;

    public Promotion(String name, String buyAmount, String getAmount, String startDate, String endDate) {
        this.name = name;
        this.buyAmount = Integer.parseInt(buyAmount);
        this.getAmount = Integer.parseInt(getAmount);
        this.startDate = transferToDate(startDate);
        this.endDate = transferToDate(endDate);
    }

    private LocalDate transferToDate(String dateInput) {
        String[] date = dateInput.split("-");
        return LocalDate.of(Integer.parseInt(date[0])
                , Integer.parseInt(date[1])
                , Integer.parseInt(date[2]));
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
