package store;

import java.util.Optional;

public class Product {
    private String name;
    private int price;
    private int promotionQuantity;
    private int regularQuantity;
    private boolean hasOngoingPromotion;
    private Optional<Promotion> promotion;


    public Product(String name, String price, String quantity, Optional<Promotion> promotion) {
        this.name = name;
        this.price = Integer.parseInt(price);
        if (promotion.isEmpty()) {
            // 최초 들어온 상품이 정가상품임
            this.promotionQuantity = 0;
            this.hasOngoingPromotion = false;
            this.regularQuantity = Integer.parseInt(quantity);
            this.promotion = Optional.empty();
            return;
        }
        // 최초 들어온 상품이 프로모션상품임
        this.promotionQuantity = Integer.parseInt(quantity);
        this.hasOngoingPromotion = promotion.get().isOngoing();
        this.promotion = promotion;
    }

    public void updateProductInfo(String quantity, Optional<Promotion> promotion) {
        if (promotion.isEmpty() && !this.promotion.isEmpty()) {
            // 새로 들어온 상품이 정가 상품임
            this.regularQuantity = Integer.parseInt(quantity);
            return;
        }
        if (!promotion.isEmpty() && this.promotion.isEmpty()) {
            //새로 들어온 상품이 할인 상품임
            this.promotionQuantity = Integer.parseInt(quantity);
            this.promotion = promotion;
            this.hasOngoingPromotion = promotion.get().isOngoing();
            return;
        }
        throw new IllegalStateException("[SYSTEM] Duplicated Promotion Item or Duplicated Regular Item");
    }

    public void buyPromotionProduct(int amount) {
        if (promotionQuantity >= amount) {
            promotionQuantity -= amount;
            return;
        }
        throw new IllegalStateException("out of promotion product stock");
    }

    public void buyRegularProduct(int amount) {
        if (regularQuantity >= amount) {
            regularQuantity -= amount;
            return;
        }
        throw new IllegalStateException("out of regular product stock");
    }

    public void buyPromotionAndRegularProduct(int amount) { //리팩토링하기
        if (amount >= promotionQuantity) {
            amount -= promotionQuantity;
            promotionQuantity = 0;
        }
        if (amount > 0) {
            buyRegularProduct(amount);
        }
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public int getRegularQuantity() {
        return regularQuantity;
    }

    public boolean hasOngoingPromotion() {
        return hasOngoingPromotion;
    }

    public Optional<Promotion> getPromotion() {
        return promotion;
    }
}
