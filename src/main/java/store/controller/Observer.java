package store.controller;

import store.dto.GiftDto;
import store.dto.OutOfStockPromotionDto;
import store.model.Cart;
import store.model.DiscountHistory;

public interface Observer {
    void notifyOutOfPromotionStock(OutOfStockPromotionDto dto, Cart cart, DiscountHistory discountHistory);

    void notifyAddGift(GiftDto giftDto, Cart cart, DiscountHistory discountHistory);
}
