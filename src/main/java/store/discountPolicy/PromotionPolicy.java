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

    public Map<Item, Integer> getGift() {
        return gift;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }
}
