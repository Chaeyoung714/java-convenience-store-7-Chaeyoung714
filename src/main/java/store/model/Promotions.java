package store.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import store.util.FileScanner;

public class Promotions {
    private final List<Promotion> promotions;

    private Promotions(List<Promotion> promotions) {
        validateNameDuplication(promotions);
        this.promotions = promotions;
    }

    public static Promotions register() {
        try {
            List<String> promotionFileBody = FileScanner.readFile("./src/main/resources/promotions.md");
            List<Promotion> promotions = new ArrayList<>();
            for (String promotionBody : promotionFileBody) {
                String[] promotion = promotionBody.split(",");
                promotions.add(Promotion.from(
                        promotion[0], promotion[1], promotion[2], promotion[3], promotion[4]
                ));
            }
            return new Promotions(promotions);
        } catch (NullPointerException e) {
            throw new IllegalStateException("[SYSTEM] 잘못된 프로모션입니다.");
        }
    }

    private void validateNameDuplication(List<Promotion> promotions) {
        Set<String> uniqueNames = new HashSet<>();
        for (Promotion promotion : promotions) {
            if (!uniqueNames.add(promotion.getName())) {
                throw new IllegalStateException("[SYSTEM] 중복된 프로모션입니다.");
            }
        }
    }

    public List<Promotion> getPromotions() {
        return Collections.unmodifiableList(promotions);
    }
}
