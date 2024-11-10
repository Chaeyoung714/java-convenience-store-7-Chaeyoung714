package store.service;

import store.dto.GiftDto;
import store.dto.OutOfStockPromotionDto;
import store.model.Cart;
import store.model.DiscountHistory;

public class ApplyPromotionHandler {
    private final OrderService orderService;

    public ApplyPromotionHandler(OrderService orderService) {
        this.orderService = orderService;
    }

    public void orderAddingOrWithoutGift(String answer, GiftDto dto, Cart cart, DiscountHistory discountHistory) {
        if (answer.equals("Y")) {
            orderService.applyPromotionAddingGift(dto, cart, discountHistory);
        }
        if (answer.equals("N")) {
            orderService.simplyApplyPromotion(dto.gift(), dto.buyAmount(), discountHistory);
        }
    }

    public void orderWithOrWithoutRegularItems(String answer, OutOfStockPromotionDto dto, Cart cart,
                                               DiscountHistory discountHistory) {
        if (answer.equals("Y")) {
            orderService.simplyApplyPromotion(dto.item(), dto.buyAmount(), discountHistory);
        }
        if (answer.equals("N")) {
            orderService.applyPromotionWithoutRegularItems(dto, cart, discountHistory);
        }
    }
}
