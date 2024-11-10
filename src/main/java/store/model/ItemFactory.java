package store.model;

import java.util.Optional;

public class ItemFactory {

    public static Item from(String name, String price, String quantity, Optional<Promotion> promotion) {
        try {
            int promotionQuantity = determinePromotionQuantity(promotion, quantity);
            int regularQuantity = determineRegularQuantity(promotion, quantity);
            boolean hasOngoingPromotion = determinePromotionOngoing(promotion);
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

    private static int determineRegularQuantity(Optional<Promotion> promotion, String quantity) {
        if (promotion.isEmpty()) {
            return Integer.parseInt(quantity);
        }
        return 0;
    }

    private static int determinePromotionQuantity(Optional<Promotion> promotion, String quantity) {
        if (promotion.isPresent()) {
            return Integer.parseInt(quantity);
        }
        return 0;
    }

    private static boolean determinePromotionOngoing(Optional<Promotion> promotion) {
        if (promotion.isPresent()) {
            return promotion.get().isOngoing();
        }
        return false;
    }

}
