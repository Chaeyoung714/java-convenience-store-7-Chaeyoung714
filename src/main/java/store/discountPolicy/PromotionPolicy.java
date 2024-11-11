package store.discountPolicy;

import store.model.item.Item;

public interface PromotionPolicy {

    int calculateGiftAmount(Item item, int buyAmount);

    int getDefaultGiftAmount();
}
