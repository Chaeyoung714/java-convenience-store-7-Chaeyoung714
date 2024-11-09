package store.exceptions;

import store.model.Item;

public class DidNotBringPromotionGiveProductException extends RuntimeException{
    private final Item gift;
    private final int buyAmount;

    public DidNotBringPromotionGiveProductException(Item gift, int buyAmount) {
        this.gift = gift;
        this.buyAmount = buyAmount;
    }

    public Item getGift() {
        return gift;
    }

    public int getBuyAmount() {
        return buyAmount;
    }
}
