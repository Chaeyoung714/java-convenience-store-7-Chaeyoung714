package store.exceptions;

import store.Product;

public class OutOfPromotionStockException extends RuntimeException{
    private final Product product;
    private final int outOfStockAmount;
    private final int buyAmount;

    public OutOfPromotionStockException(Product product, int outOfStockAmount, int buyAmount) {
        this.product = product;
        this.outOfStockAmount = outOfStockAmount;
        this.buyAmount = buyAmount;
    }

    public Product getProduct() {
        return product;
    }

    public int getOutOfStockAmount() {
        return outOfStockAmount;
    }

    public int getBuyAmount() {
        return buyAmount;
    }
}
