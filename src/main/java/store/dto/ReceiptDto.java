package store.dto;

import store.model.consumer.Cart;
import store.model.consumer.DiscountHistory;
import store.model.consumer.PurchaseCost;

public class ReceiptDto {
    private final PurchaseHistoryDtos purchaseHistoryDtos;
    private final PromotionHistoryDtos promotionHistoryDtos;
    private final CostResultDto costResultDto;

    public ReceiptDto(Cart cart, DiscountHistory discountHistory, PurchaseCost purchaseCost) {
        this.purchaseHistoryDtos = PurchaseHistoryDtos.of(cart);
        this.promotionHistoryDtos = PromotionHistoryDtos.of(discountHistory);
        this.costResultDto = CostResultDto.from(cart, discountHistory, purchaseCost);
    }

    public PurchaseHistoryDtos getPurchaseHistoryDtos() {
        return purchaseHistoryDtos;
    }

    public PromotionHistoryDtos getPromotionHistoryDtos() {
        return promotionHistoryDtos;
    }

    public CostResultDto getCostResultDto() {
        return costResultDto;
    }
}
