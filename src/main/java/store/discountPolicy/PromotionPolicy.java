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

    public void applyPromotion(Item item, int buyAmount) {
    }


}
