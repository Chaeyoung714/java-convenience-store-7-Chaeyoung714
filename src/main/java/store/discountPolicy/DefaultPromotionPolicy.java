package store.discountPolicy;

import store.model.item.Item;

public class DefaultPromotionPolicy implements PromotionPolicy{
    private final int defaultGiftAmount;

    public DefaultPromotionPolicy() {
        this.defaultGiftAmount = 1;
    }

    @Override
    public int calculateGiftAmount(Item item, int buyAmount) {
        int promotionBundleAmount = item.getPromotion().get().getBundleAmount();
        int promotionApplicableItemAmount = Math.min(item.getPromotionQuantity(), buyAmount);
        int giftAmount = promotionApplicableItemAmount / promotionBundleAmount;
        return giftAmount;
    }

    @Override
    public int getDefaultGiftAmount() {
        return defaultGiftAmount;
    }
}
