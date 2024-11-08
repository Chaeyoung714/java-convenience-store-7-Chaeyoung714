package store.exceptions;

import store.Product;

public class DidNotBringPromotionGiveProductException extends RuntimeException{
    private final Product promotionGetProduct;
    private final int buyAmount;

    public DidNotBringPromotionGiveProductException(Product promotionGetProduct, int buyAmount) {
        this.promotionGetProduct = promotionGetProduct;
        this.buyAmount = buyAmount;
    }

    public Product getPromotionGetProduct() {
        return promotionGetProduct;
    }

    public int getBuyAmount() {
        return buyAmount;
    }
}
