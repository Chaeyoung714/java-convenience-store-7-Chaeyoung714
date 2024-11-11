package store.model.item;

import java.util.Optional;
import store.model.promotion.Promotion;

public class Item{
    private String name;
    private int price;
    private int promotionQuantity;
    private int regularQuantity;
    private boolean hasOngoingPromotion;
    private Optional<Promotion> promotion;

    protected Item(String name, int price, int promotionQuantity, int regularQuantity, boolean hasOngoingPromotion,
                   Optional<Promotion> promotion) {
        validatePositiveNumber(price);
        validatePositiveNumber(promotionQuantity);
        validatePositiveNumber(regularQuantity);
        this.name = name;
        this.price = price;
        this.promotionQuantity = promotionQuantity;
        this.regularQuantity = regularQuantity;
        this.hasOngoingPromotion = hasOngoingPromotion;
        this.promotion = promotion;
    }

    public void updateItemInfo(String quantity, Optional<Promotion> promotion) {
        try {
            validatePositiveNumber(Integer.parseInt(quantity));
            if (promotion.isEmpty() && this.promotion.isPresent()) {
                this.regularQuantity = Integer.parseInt(quantity);
                return;
            }
            if (promotion.isPresent() && this.promotion.isEmpty()) {
                updatePromotionItemInfo(quantity, promotion);
                return;
            }
            throw new IllegalStateException("[SYSTEM] Promotion item duplicated or regular item duplicated");
        } catch (NumberFormatException e) {
            throw new IllegalStateException("[SYSTEM] Wrong item quantity");
        }
    }

    private void updatePromotionItemInfo(String quantity, Optional<Promotion> promotion) {
        this.promotionQuantity = Integer.parseInt(quantity);
        this.hasOngoingPromotion = promotion.get().isOngoing();
        this.promotion = promotion;
    }

    public void purchase(int amount) {
        if (promotionQuantity > 0) {
            amount = calculateWithPromotionQuantity(amount);
            if (amount == 0) {
                return;
            }
        }
        if (regularQuantity >= amount) {
            regularQuantity -= amount;
            return;
        }
        throw new IllegalStateException("[SYSTEM] Out of all product stock");
    }

    private int calculateWithPromotionQuantity(int amount) {
        if (promotionQuantity >= amount) {
            promotionQuantity -= amount;
            return 0;
        }
        amount -= promotionQuantity;
        promotionQuantity = 0;
        return amount;
    }

    private void validatePositiveNumber(int number) {
        if (number < 0) {
            throw new IllegalStateException("[SYSTEM] Wrong number in Item");
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
