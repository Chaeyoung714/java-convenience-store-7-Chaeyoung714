package store;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        int sumOfBuyAndGetAmountOfPromotionProduct = buyAmountOfPromotionProduct + 1; //N+1 행사에서 1 증정품 개수
        int promotionNeedAmount = buyAmount - (buyAmount % buyAmountOfPromotionProduct); //프로모션 제품으로 받아야 하는 제품 개수

        if (buyAmount % sumOfBuyAndGetAmountOfPromotionProduct == 0) {
            //프로모션 증정품까지 딱 맞게 가져온 경우
            if (product.getPromotionQuantity() < promotionNeedAmount) { //프로모션 상품은 재고가 떨어지면 일반으로 가므로 미리 분기처리 체크
                //프로모션 재고가 모자란 경우
                int outOfStockAmount = buyAmount - product.getPromotionQuantity();
                throw new OutOfPromotionStockException(product, outOfStockAmount, buyAmount);
            }
            //프로모션 재고가 충분한 경우
            product.buyPromotionAndRegularProduct(buyAmount);
            return buyAmount / sumOfBuyAndGetAmountOfPromotionProduct; // 증정품 개수
        }
        if (buyAmount % sumOfBuyAndGetAmountOfPromotionProduct == buyAmountOfPromotionProduct) {
            // 증정품만 안가져온 경우
            int buyAmountIfIncludesPromotionGetProduct = buyAmount + 1;
            if (product.getPromotionQuantity() < promotionNeedAmount) {
                //기존 프로모션도 제공을 못한다면
                int outOfStockAmount = buyAmount - product.getPromotionQuantity();
                throw new OutOfPromotionStockException(product, outOfStockAmount, buyAmount);
            }
            if (product.getPromotionQuantity() >= buyAmountIfIncludesPromotionGetProduct) {
                //증정품까지 줄 수 있는 재고라면
                throw new DidNotBringPromotionGiveProductException(product, buyAmount);
            }
            //기존 프로모션 제공까진 가능하다면
            product.buyPromotionAndRegularProduct(buyAmount);
            return buyAmount / sumOfBuyAndGetAmountOfPromotionProduct;
        }
        //그냥 프로모션이랑 섞어서 가져온 경우 (사실은 buyAmount > buyAmountOfPromotionProduct)
        if (buyAmount >= buyAmountOfPromotionProduct) {
            //기존 프로모션 적용이 필요한 경우
            if (product.getPromotionQuantity() < promotionNeedAmount) {
                //기존 프로모션도 제공을 못한다면
                int outOfStockAmount = buyAmount - product.getPromotionQuantity();
                throw new OutOfPromotionStockException(product, outOfStockAmount, buyAmount);
            }
            product.buyPromotionAndRegularProduct(buyAmount);
            return buyAmount / sumOfBuyAndGetAmountOfPromotionProduct;
        }
        //프로모션 적용이 필요없는 경우 포함 (buyAmount < buyAmountOfPromotionProduct)
        product.buyPromotionAndRegularProduct(buyAmount);
        return 0;
    }

    public void buyIncludingOutOfStockAmount(Product product, int outOfStockAmount,
                                             int buyAmount) {
        //위랑 많이 중복됨
        //includesOutOfStock이면, buy는 똑같이 하되, 증정품만 하나 줄어든다. (6개 중 3개에 대해서만 증정을 받는다)
        int sumOfBuyAndGetAmountOfPromotionProduct = product.getPromotion().get().getBuyAmount() + 1;
        product.buyPromotionAndRegularProduct(buyAmount);
        int promotionGetAmount = (buyAmount - outOfStockAmount) / sumOfBuyAndGetAmountOfPromotionProduct;
        if (promotionGetAmount > 0) {
            for (int i = 0; i < promotionGetAmount; i++) {
                promotionGetProducts.add(product);
            }
        }
    }

    public void buyExcludingOutOfStockAmount(Product product, int outOfStockAmount,
                                             int buyAmount) {
        //excludesOutOfStock이면, buy가 달라진다. (6개 중 3개만 산다.)
        buyAmount -= outOfStockAmount;
        int sumOfBuyAndGetAmountOfPromotionProduct = product.getPromotion().get().getBuyAmount() + 1;
        product.buyPromotionAndRegularProduct(buyAmount);
        int promotionGetAmount = buyAmount / sumOfBuyAndGetAmountOfPromotionProduct;
        if (promotionGetAmount > 0) {
            for (int i = 0; i < promotionGetAmount; i++) {
                promotionGetProducts.add(product);
            }
        }
    }

    public void buyIncludingPromotionGetProduct(Product product, int buyAmount) {
        // includingPromotion이면, buyAmount가 하나 추가되고, 증정품도 추가된다.
        int sumOfBuyAndGetAmountOfPromotionProduct = product.getPromotion().get().getBuyAmount() + 1;
        buyAmount ++;
        product.buyPromotionAndRegularProduct(buyAmount);
        int promotionGetAmount = buyAmount / sumOfBuyAndGetAmountOfPromotionProduct;
        if (promotionGetAmount > 0) {
            for (int i = 0; i < promotionGetAmount; i++) {
                promotionGetProducts.add(product);
            }
        }
    }

    public void buyExcludingPromotionGetProduct(Product product, int buyAmount) {
        int sumOfBuyAndGetAmountOfPromotionProduct = product.getPromotion().get().getBuyAmount() + 1;
        product.buyPromotionAndRegularProduct(buyAmount);
        int promotionGetAmount = buyAmount / sumOfBuyAndGetAmountOfPromotionProduct;
        if (promotionGetAmount > 0) {
            for (int i = 0; i < promotionGetAmount; i++) {
                promotionGetProducts.add(product);
            }
        }
    }

    public List<Product> getPromotionGetProducts() {
        return promotionGetProducts;
    }
}
