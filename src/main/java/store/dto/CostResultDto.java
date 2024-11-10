package store.dto;

import store.model.consumer.Cart;
import store.model.consumer.DiscountHistory;
import store.model.consumer.PurchaseCost;

public record CostResultDto(int totalItemCost, int totalBuyAmount, int promotionDiscountAmount,
                            int membershipDiscountAmount, int finalCost) {

    public static CostResultDto from(Cart cart, DiscountHistory discountHistory, PurchaseCost purchaseCost) {
        return new CostResultDto(
                cart.calculateTotalCost()
                , cart.calculateTotalBuyAmount()
                , discountHistory.getPromotionDiscountAmount()
                , discountHistory.getMembershipDiscountAmount()
                , purchaseCost.getPurchaseCost()
        );
    }
}
