package store.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import store.util.FileScanner;

public class Promotions {
    //ENUM처럼 사용하기!
    private static final List<Promotion> promotions = register(
            FileScanner.readFile("./src/main/resources/promotions.md"));

    private Promotions() {
    }

    public static List<Promotion> register(List<String> promotionFileData) {
        try {
            List<Promotion> promotions = new ArrayList<>();
            for (String promotionData : promotionFileData) {
                String[] promotion = promotionData.split(",");
                promotions.add(Promotion.from(
                        promotion[0], promotion[1], promotion[2], promotion[3], promotion[4]
                ));
            }
            validateNameDuplication(promotions);
            return promotions;
        } catch (NullPointerException e) {
            throw new IllegalStateException("[SYSTEM] 잘못된 프로모션입니다.");
        }
    }

    public static Optional<Promotion> findByName(String name) {
        for (Promotion promotion : promotions) {
            if (promotion.getName().equals(name)) {
                return Optional.of(promotion);
            }
        }
        return Optional.empty();
    }

    private static void validateNameDuplication(List<Promotion> promotions) {
        Set<String> uniqueNames = new HashSet<>();
        for (Promotion promotion : promotions) {
            if (!uniqueNames.add(promotion.getName())) {
                throw new IllegalStateException("[SYSTEM] 중복된 프로모션입니다.");
            }
        }
    }

    public static List<Promotion> getPromotions() {
        return Collections.unmodifiableList(promotions);
    }
}
