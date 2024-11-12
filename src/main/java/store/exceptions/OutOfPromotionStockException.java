package store.exceptions;

public class OutOfPromotionStockException extends RuntimeException{
    private final int outOfStockAmount;

    public OutOfPromotionStockException(int outOfStockAmount) {
        this.outOfStockAmount = outOfStockAmount;
    }

    public int getOutOfStockAmount() {
        return outOfStockAmount;
    }
}
