package store.model.promotion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Promotions {
    private static final String PROMOTIONS_FILE_DELIMITER = ",";
    private static final int NAME = 0;
    private static final int BUY_AMOUNT = 1;
    private static final int GET_AMOUNT = 2;
    private static final int START_DATE = 3;
    private static final int END_DATE = 4;

    private final List<Promotion> promotions;

    private Promotions(final List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public static Promotions of(final List<String> promotionFileData) {
        try {
            List<Promotion> promotions = parsePromotions(promotionFileData);
            validateNameDuplication(promotions);
            return new Promotions(promotions);
        } catch (NullPointerException e) {
            throw new IllegalStateException("[SYSTEM] Wrong promotion type");
        }
    }

    public Optional<Promotion> findByName(final String name) {
        for (Promotion promotion : promotions) {
            if (promotion.getName().equals(name)) {
                return Optional.of(promotion);
            }
        }
        return Optional.empty();
    }

    private static List<Promotion> parsePromotions(final List<String> promotionFileData) {
        List<Promotion> promotions = new ArrayList<>();
        for (String promotionData : promotionFileData) {
            String[] promotion = promotionData.split(PROMOTIONS_FILE_DELIMITER);
            promotions.add(Promotion.from(promotion[NAME], promotion[BUY_AMOUNT], promotion[GET_AMOUNT]
                    , promotion[START_DATE], promotion[END_DATE]));
        }
        return promotions;
    }

    private static void validateNameDuplication(final List<Promotion> promotions) {
        Set<String> uniqueNames = new HashSet<>();
        for (Promotion promotion : promotions) {
            if (addDuplicatedName(uniqueNames, promotion.getName())) {
                throw new IllegalStateException("[SYSTEM] Duplicated name of promotion");
            }
        }
    }

    private static boolean addDuplicatedName(final Set<String> uniqueNames, final String name) {
        return !uniqueNames.add(name);
    }

    public List<Promotion> getPromotions() {
        return Collections.unmodifiableList(promotions);
    }
}
