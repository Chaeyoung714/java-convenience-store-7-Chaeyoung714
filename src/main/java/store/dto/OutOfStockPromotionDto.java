package store.dto;

import store.model.Item;

public record OutOfStockPromotionDto(Item item, int buyAmount, int outOfStockAmount) {

    public static OutOfStockPromotionDto from(Item item, int buyAmount) {
        int bundleAmount = item.getPromotion().get().getBundleAmount();
        int outOfStockAmount = buyAmount - (bundleAmount * (item.getPromotionQuantity() / bundleAmount));
        return new OutOfStockPromotionDto(item, buyAmount, outOfStockAmount);
    }

    public String itemName() {
        return item.getName();
    }
}
