package store.discountPolicy;

import store.model.item.Item;

public class PromotionPolicy {

    public int calculateGift(Item item, int buyAmount) {
        int promotionBundleAmount = item.getPromotion().get().getBundleAmount();
        int giftAmount = Math.min(item.getPromotionQuantity(), buyAmount) / promotionBundleAmount;
        return giftAmount;
    }
}
