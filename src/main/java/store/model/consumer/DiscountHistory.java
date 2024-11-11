package store.model.consumer;

import java.util.HashMap;
import java.util.Map;
import store.model.item.Item;

public class DiscountHistory {
    private int promotionDiscountAmount;
    private int membershipDiscountAmount;
    private final Map<Item, Integer> gifts;
    private boolean isMembershipApplied;

    public DiscountHistory() {
        this.gifts = new HashMap<>();
        this.isMembershipApplied = false;
    }

    public int getPromotionAppliedAmount() {
        int promotionAppliledAmount = 0;
        for (Item item : gifts.keySet()) {
            int bundleAmount = item.getPromotion().get().getBundleAmount();
            promotionAppliledAmount += (bundleAmount * gifts.get(item) * item.getPrice());
        }
        return promotionAppliledAmount;
    }

    public void addGift(final Item item, final int amount) {
        if (amount == 0) {
            return;
        }
        validateHasOngoingPromotion(item);
        validateDuplicatedGift(item);
        gifts.put(item, amount);
        promotionDiscountAmount += (item.getPrice() * amount);
    }

    public void setMembershipDiscount(final int membershipDiscountAmount) {
        if (isMembershipApplied) {
            throw new IllegalStateException("[SYSTEM] Duplicated membership applied.");
        }
        this.membershipDiscountAmount = membershipDiscountAmount;
        this.isMembershipApplied = true;
    }

    private void validateDuplicatedGift(final Item item) {
        if (gifts.keySet().contains(item)) {
            throw new IllegalStateException("[SYSTEM] Duplicated promotion on same item.");
        }
    }

    private void validateHasOngoingPromotion(final Item item) {
        if (!item.hasOngoingPromotion()) {
            throw new IllegalStateException("[SYSTEM] Gift is not have Ongoing Promotion Item.");
        }
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
