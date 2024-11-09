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
        if (buyAmount > item.getPromotionQuantity()) {
            throw new OutOfPromotionStockException(item, buyAmount);
        }
        int promotionBundleAmount = item.getPromotion().get().getBundleAmount();
        if (buyAmount % promotionBundleAmount == 1) {
            if ((buyAmount + 1) <= item.getPromotionQuantity()) {
                throw new DidNotBringPromotionGiveProductException(item, buyAmount);
            }
        }
    }

    public void addGift(Item item, int buyAmount) {
        int promotionBundleAmount = item.getPromotion().get().getBundleAmount();
        int giftAmount = buyAmount / promotionBundleAmount; //프로모션의 일인가.
        updateGift(item, giftAmount);
        updateDiscountAmount(item.getPrice(), giftAmount);
    }

    private void updateGift(Item item, int giftAmount) {
        if (gift.keySet().contains(item)) {
            gift.replace(item, gift.get(item) + giftAmount);
            return;
        }
        gift.put(item, giftAmount);
    }

    private void updateDiscountAmount(int giftPrice, int giftAmount) {
        discountAmount += (giftAmount * giftPrice);
    }
}
