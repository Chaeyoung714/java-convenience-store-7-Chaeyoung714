package store.service;

import java.util.Map;
import store.controller.Observer;
import store.dto.GiftDto;
import store.dto.OutOfStockPromotionDto;
import store.exceptions.NotAddGiftException;
import store.exceptions.OutOfPromotionStockException;
import store.model.Cart;
import store.model.DiscountHistory;
import store.model.Item;

public class PromotionServiceOutboundHandler {
    private final PromotionService promotionService;
    private final Observer observer;

    public PromotionServiceOutboundHandler(PromotionService promotionService, Observer observer) {
        this.promotionService = promotionService;
        this.observer = observer;
    }

    public void applyPromotion(Cart consumerCart, DiscountHistory discountHistory) {
        Map<Item, Integer> cart = consumerCart.getCart();
        for (Item item : cart.keySet()) {
            try {
                promotionService.checkAndApplyPromotion(item, cart.get(item), discountHistory);
            } catch (OutOfPromotionStockException e) {
                observer.notifyOutOfPromotionStock(
                        OutOfStockPromotionDto.from(item, cart.get(item)), consumerCart, discountHistory);
            } catch (NotAddGiftException e) {
                observer.notifyAddGift(new GiftDto(item, cart.get(item)), consumerCart, discountHistory);
            }
        }
    }
}
