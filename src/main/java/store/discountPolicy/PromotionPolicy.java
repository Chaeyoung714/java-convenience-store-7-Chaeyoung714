package store.discountPolicy;

import store.model.Item;

public class PromotionPolicy {
    /**
     * gift를 정해주는 역할.
     */

    public int calculateGift(Item item, int buyAmount) {
        int promotionBundleAmount = item.getPromotion().get().getBundleAmount();
        int giftAmount = Math.min(item.getPromotionQuantity(), buyAmount) / promotionBundleAmount;
        return giftAmount;
    }
}
