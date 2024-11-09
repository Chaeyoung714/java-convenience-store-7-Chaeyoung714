package store.exceptions;

import store.model.Item;

public class OutOfPromotionStockException extends RuntimeException{
    private final Item item;
    private final int buyAmount;
    private final int outOfStockAmount;

    public OutOfPromotionStockException(Item item, int buyAmount) {
        this.item = item;
        this.buyAmount = buyAmount;
        this.outOfStockAmount = item.getPromotionQuantity() - buyAmount;
    }

    public Item getItem() {
        return item;
    }

    public int getBuyAmount() {
        return buyAmount;
    }

    public int getOutOfStockAmount() {
        return outOfStockAmount;
    }
}
