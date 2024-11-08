package store;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.bytebuddy.pool.TypePool.Resolution.NoSuchTypeException;
import store.exceptions.DidNotBringPromotionGiveProductException;
import store.exceptions.OutOfPromotionStockException;

public class Customer {
    private final Cart cart;
    private final List<Product> promotionGetProducts;
    private int membershipDiscountAmount;

    public Customer(Cart cart) {
        this.cart = cart;
        this.promotionGetProducts = new ArrayList<>();
        this.membershipDiscountAmount = 0;
    }

    //cart에 들어가도 될것같긴한데... 역할은 customer가..
    public void applyPromotion() {
        Map<Product, Integer> cartMap = cart.getCart();
        for (Product product : cartMap.keySet()) {
            if (!product.hasOngoingPromotion()) {
                product.buyRegularProduct(cartMap.get(product)); //재고 점검 앞에서 했고 & buyProduct에서 함
                continue;
            }
            int promotionGetAmount = buyPromotionProduct(product, cartMap.get(product));
            if (promotionGetAmount > 0) {
                for (int i = 0; i < promotionGetAmount; i++) {
                    promotionGetProducts.add(product);
                }
            }
        }
    }

    private int buyPromotionProduct(Product product, int buyAmount) {
        int buyAmountOfPromotionProduct = product.getPromotion().get().getBuyAmount();
        int getAmountOfPromotionProduct = product.getPromotion().get().getGetAmount();
        int sumOfBuyAndGetAmountOfPromotionProduct = buyAmountOfPromotionProduct + getAmountOfPromotionProduct;

        if (buyAmount % sumOfBuyAndGetAmountOfPromotionProduct == 0) { //프로모션 증정품까지 딱 맞게 가져온 경우
            if (product.getPromotionQuantity() >= buyAmount) { //프로모션 상품은 재고가 떨어지면 일반으로 가므로 미리 분기처리 체크
                product.buyPromotionProduct(buyAmount);
                return buyAmount / sumOfBuyAndGetAmountOfPromotionProduct; // 증정품 개수
            }
            throw new OutOfPromotionStockException();
        }
        if (buyAmount % sumOfBuyAndGetAmountOfPromotionProduct == buyAmountOfPromotionProduct) {
            // 증정품만 안가져온 경우
            int buyAmountIfIncludesPromotionGetProduct = buyAmount + getAmountOfPromotionProduct;
            if (product.getPromotionQuantity() >= buyAmountIfIncludesPromotionGetProduct) {
                throw new DidNotBringPromotionGiveProductException();
            }
            product.buyPromotionAndRegularProduct(buyAmount);
            return buyAmount / sumOfBuyAndGetAmountOfPromotionProduct;
        }
        //그냥 프로모션이랑 섞어서 가져온 경우
        product.buyPromotionAndRegularProduct(buyAmount);
        return buyAmount / sumOfBuyAndGetAmountOfPromotionProduct;
    }

    public List<Product> getPromotionGetProducts() {
        return promotionGetProducts;
    }
}
