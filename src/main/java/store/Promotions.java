package store;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Promotions {
    private final List<Promotion> promotions;

    private Promotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public Optional<Promotion> findByName(String name) {
        for (Promotion promotion : promotions) {
            if (promotion.getName().equals(name)) {
                return Optional.of(promotion);
            }
        }
        return Optional.empty();
    }

    public List<Promotion> getPromotions() {
        return Collections.unmodifiableList(promotions);
    }
}
