package store.discountPolicy;

import store.model.item.Item;

public class PromotionPolicy {
    private final int giftAmount;

    public PromotionPolicy() {
        this.giftAmount = 1;
    }

    public int calculateGift(Item item, int buyAmount) {
        int promotionBundleAmount = item.getPromotion().get().getBundleAmount();
        int promotionApplicableItemAmount = Math.min(item.getPromotionQuantity(), buyAmount);
        int giftAmount = promotionApplicableItemAmount / promotionBundleAmount;
        return giftAmount;
    }

    public int getGiftAmount() {
        return giftAmount;
    }
}
