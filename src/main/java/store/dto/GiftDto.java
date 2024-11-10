package store.dto;

import store.model.Item;

public record GiftDto(Item gift, int buyAmount) {

    public String giftName() {
        return gift.getName();
    }
}
