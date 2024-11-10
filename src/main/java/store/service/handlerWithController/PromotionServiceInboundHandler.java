package store.service.handlerWithController;

import static store.util.Answer.NO;
import static store.util.Answer.YES;

import store.dto.GiftDto;
import store.dto.OutOfStockPromotionDto;
import store.model.consumer.Cart;
import store.model.consumer.DiscountHistory;
import store.service.PromotionService;

public class PromotionServiceInboundHandler {
    private final PromotionService promotionService;

    public PromotionServiceInboundHandler(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    public void orderAddingOrWithoutGift(String answer, GiftDto dto, Cart cart, DiscountHistory discountHistory) {
        if (answer.equals(YES.getFormat())) {
            promotionService.applyPromotionAddingGift(dto, cart, discountHistory);
        }
        if (answer.equals(NO.getFormat())) {
            promotionService.applyDefaultPromotion(dto.gift(), dto.buyAmount(), discountHistory);
        }
    }

    public void orderWithOrWithoutRegularItems(String answer, OutOfStockPromotionDto dto, Cart cart,
                                               DiscountHistory discountHistory) {
        if (answer.equals(YES.getFormat())) {
            promotionService.applyDefaultPromotion(dto.item(), dto.buyAmount(), discountHistory);
        }
        if (answer.equals(NO.getFormat())) {
            promotionService.applyPromotionWithoutRegularItems(dto, cart, discountHistory);
        }
    }

}
