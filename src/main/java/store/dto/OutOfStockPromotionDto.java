package store.dto;

import store.model.item.Item;

public record OutOfStockPromotionDto(Item item, int buyAmount, int outOfStockAmount) {

    public String itemName() {
        return item.getName();
    }
}
