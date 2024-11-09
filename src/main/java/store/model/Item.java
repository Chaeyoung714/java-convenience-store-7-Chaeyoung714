package store.model;

import java.util.Optional;

public class Item {
    private String name;
    private int price;
    private int promotionQuantity;
    private int regularQuantity;
    private boolean hasOngoingPromotion;
    private Optional<Promotion> promotion;

    private Item(String name, int price, int promotionQuantity, int regularQuantity, boolean hasOngoingPromotion,
                 Optional<Promotion> promotion) {
        this.name = name;
        this.price = price;
        this.promotionQuantity = promotionQuantity;
        this.regularQuantity = regularQuantity;
        this.hasOngoingPromotion = hasOngoingPromotion;
        this.promotion = promotion;
    }

    public static Item from(String name, String price, String quantity, String promotionName, Promotions promotions) {
        try {
            int promotionQuantity = 0;
            int regularQuantity = 0;
            boolean hasOngoingPromotion = false;

            Optional<Promotion> promotion = promotions.findByName(promotionName);
            if (promotion.isEmpty()) { //프로모션 없음
                regularQuantity = Integer.parseInt(quantity);
            }
            if (promotion.isPresent()) {
                promotionQuantity = Integer.parseInt(quantity);
                hasOngoingPromotion = promotion.get().isOngoing();
            }
            return new Item(
                    name
                    , Integer.parseInt(price)
                    , promotionQuantity
                    , regularQuantity
                    , hasOngoingPromotion
                    , promotion
            );
        } catch (NumberFormatException e) {
            throw new IllegalStateException("[SYSTEM] 잘못된 가격 또는 수량입니다.");
        }
    }

    public void update(String quantity, String promotionName, Promotions promotions) {
        try {
            Optional<Promotion> promotion = promotions.findByName(promotionName);
            if (promotion.isEmpty() && this.promotion.isPresent()) {
                this.regularQuantity = Integer.parseInt(quantity);
                return;
            }
            if (promotion.isPresent() && this.promotion.isEmpty()) {
                this.promotionQuantity = Integer.parseInt(quantity);
                this.hasOngoingPromotion = promotion.get().isOngoing();
                return;
            }
            throw new IllegalStateException("[SYSTEM] 프로모션이 중복 적용되거나 정가제품이 중복 적용되었습니다.");
        } catch (NumberFormatException e) {
            throw new IllegalStateException("[SYSTEM] 잘못된 수량입니다.");
        }
    }

    public void purchase(int amount) {
        if (promotionQuantity > 0) {
            // amount <= promotionQuality
            if (promotionQuantity >= amount) {
                promotionQuantity -= amount;
                return;
            }
            // promotionQuality < amount <= pro+regularQuality
            amount -= promotionQuantity;
            promotionQuantity = 0;
        }
        if (regularQuantity >= amount) {
            regularQuantity -= amount;
            return;
        }
        throw new IllegalStateException("Out of all product stock");
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
