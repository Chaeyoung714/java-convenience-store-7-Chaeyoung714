package store.exceptions;

import store.dto.GiftDto;
import store.model.Item;

public class NotAddGiftException extends RuntimeException{
    private final GiftDto giftDto;


    public NotAddGiftException(Item gift, int buyAmount) {
        this.giftDto = new GiftDto(gift, buyAmount);
    }

    public GiftDto getGiftDto() {
        return giftDto;
    }
}
