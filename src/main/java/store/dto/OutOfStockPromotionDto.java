package store.dto;

import store.model.Item;

public record OutOfStockPromotionDto(Item item, int buyAmount, int outOfStockAmount) {

    public String itemName() {
        return item.getName();
    }
}
