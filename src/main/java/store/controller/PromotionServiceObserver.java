package store.controller;

import store.dto.GiftDto;
import store.dto.OutOfStockPromotionDto;
import store.model.consumer.Cart;
import store.model.consumer.DiscountHistory;

public interface PromotionServiceObserver {
    void notifyOutOfPromotionStock(OutOfStockPromotionDto dto, Cart cart, DiscountHistory discountHistory);

    void notifyAddGift(GiftDto giftDto, Cart cart, DiscountHistory discountHistory);
}
