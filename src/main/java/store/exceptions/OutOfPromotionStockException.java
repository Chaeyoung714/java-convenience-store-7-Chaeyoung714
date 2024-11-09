package store.exceptions;

import store.model.Item;

public class OutOfPromotionStockException extends RuntimeException{
    private final Item item;
    private final int buyAmount;

    public OutOfPromotionStockException(Item item, int buyAmount) {
        this.item = item;
        this.buyAmount = buyAmount;
    }

    public Item getItem() {
        return item;
    }

    public int getBuyAmount() {
        return buyAmount;
    }
}
