package store.discountPolicy;

import java.util.HashMap;
import java.util.Map;
import store.model.Item;

public class PromotionPolicy {
    private final Map<Item, Integer> gift;
    private int discountAmount;

    public PromotionPolicy() {
        this.gift = new HashMap<>();
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

    public int getPromotionAppliedAmount() {
        int promotionAppliledAmount = 0;
        for (Item item : gift.keySet()) {
            int bundleAmount = item.getPromotion().get().getBundleAmount();
            promotionAppliledAmount += (bundleAmount * gift.get(item) * item.getPrice());
        }
        return promotionAppliledAmount;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public Map<Item, Integer> getGift() {
        return gift;
    }
}
