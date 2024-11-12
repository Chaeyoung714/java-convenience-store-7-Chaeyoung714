package store.model.item;

import java.util.Optional;
import store.model.promotion.Promotion;

public class ItemFactory {

    public static Item from(final String name, final String price, final String quantity,
                            final Optional<Promotion> promotion) {
        try {
            int promotionQuantity = determinePromotionQuantity(promotion, quantity);
            int regularQuantity = determineRegularQuantity(promotion, quantity);
            boolean hasOngoingPromotion = determinePromotionOngoing(promotion);
            return new Item(
                    name, Integer.parseInt(price), promotionQuantity, regularQuantity, hasOngoingPromotion, promotion);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("[SYSTEM] Wrong quantity or wrong price");
        }
    }

    private static int determineRegularQuantity(final Optional<Promotion> promotion, final String quantity) {
        if (promotion.isEmpty()) {
            return Integer.parseInt(quantity);
        }
        return 0;
    }

    private static int determinePromotionQuantity(final Optional<Promotion> promotion, final String quantity) {
        if (promotion.isPresent()) {
            return Integer.parseInt(quantity);
        }
        return 0;
    }

    private static boolean determinePromotionOngoing(final Optional<Promotion> promotion) {
        if (promotion.isPresent()) {
            return promotion.get().isOngoing();
        }
        return false;
    }

}
