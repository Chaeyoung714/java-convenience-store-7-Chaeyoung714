package store.discountPolicy;

import java.util.HashMap;
import java.util.Map;
import store.exceptions.DidNotBringPromotionGiveProductException;
import store.exceptions.OutOfPromotionStockException;
import store.model.Item;

public class PromotionPolicy {
    private final Map<Item, Integer> gift;
    private int discountAmount;

    public PromotionPolicy() {
        this.gift = new HashMap<>();
    }

    public void applyPromotion(Item item, int buyAmount) {
        checkWhetherReAskToConsumer(item, buyAmount);
    }

    private void checkWhetherReAskToConsumer(Item item, int buyAmount) {
        int promotionBundleAmount = item.getPromotionQuantity() + item.getRegularQuantity();
        if (buyAmount > item.getPromotionQuantity()) {
            throw new OutOfPromotionStockException(item, buyAmount);
        }
        if (buyAmount % promotionBundleAmount == 1) {
            if ((buyAmount + 1) <= item.getPromotionQuantity()) {
                throw new DidNotBringPromotionGiveProductException(item, buyAmount);
            }
        }
    }


}
