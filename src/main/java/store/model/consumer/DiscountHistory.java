package store.model.consumer;

import java.util.HashMap;
import java.util.Map;
import store.model.item.Item;

public class DiscountHistory {
    private int promotionDiscountAmount;
    private int membershipDiscountAmount;
    private final Map<Item, Integer> gifts;

    public DiscountHistory() {
        this.gifts = new HashMap<>();
    }

    public int getPromotionAppliedAmount() {
        int promotionAppliledAmount = 0;
        for (Item item : gifts.keySet()) {
            int bundleAmount = item.getPromotion().get().getBundleAmount();
            promotionAppliledAmount += (bundleAmount * gifts.get(item) * item.getPrice());
        }
        return promotionAppliledAmount;
    }

    public void addGift(Item item, int amount) {
        //필요시 중복이름 체크도 하기
        gifts.put(item, amount);
        promotionDiscountAmount += (item.getPrice() * amount);
    }

    public void addMembershipDiscount(int membershipDiscountAmount) {
        this.membershipDiscountAmount = membershipDiscountAmount;
    }

    public int getPromotionDiscountAmount() {
        return promotionDiscountAmount;
    }

    public int getMembershipDiscountAmount() {
        return membershipDiscountAmount;
    }

    public Map<Item, Integer> getGifts() {
        return gifts;
    }
}
