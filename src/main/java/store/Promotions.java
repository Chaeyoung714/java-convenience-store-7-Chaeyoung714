package store;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Promotions {
    private final List<Promotion> promotions;

    public Promotions(List<Promotion> promotions) {
        this.promotions = promotions;
        promotions.add(new Promotion("null", "0", "0", null, null));
    }

    public Promotion findByName(String name) {
        for (Promotion promotion : promotions) {
            if (promotion.getName().equals(name)) {
                return promotion;
            }
        }
        throw new IllegalStateException("[SYSTEM] Can't find such name of Promotion");
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public List<Promotion> checkOngoingPromotionsOf(LocalDate date) {
        List<Promotion> ongoingPromotions = new ArrayList<>();
        for (Promotion promotion : getAvailablePromotions()) {
            if ((promotion.getStartDate().isEqual(date) || promotion.getStartDate().isBefore(date))
                    && (promotion.getEndDate().isEqual(date) || promotion.getEndDate().isAfter(date))) {
                ongoingPromotions.add(promotion);
            }
        }
        return ongoingPromotions;
    }

    private List<Promotion> getAvailablePromotions() {
        List<Promotion> availablePromotions = new ArrayList<>();
        for (Promotion promotion : promotions) {
            if (promotion.isAvailable()) {
                availablePromotions.add(promotion);
            }
        }
        return availablePromotions;
    }
}
