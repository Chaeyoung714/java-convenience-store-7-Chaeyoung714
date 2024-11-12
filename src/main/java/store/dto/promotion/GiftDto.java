package store.dto.promotion;

import store.model.item.Item;

public record GiftDto(Item gift, int buyAmount) {

    public String giftName() {
        return gift.getName();
    }
}
