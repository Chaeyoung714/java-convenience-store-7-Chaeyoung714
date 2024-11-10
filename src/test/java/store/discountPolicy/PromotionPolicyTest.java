package store.discountPolicy;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.model.item.Item;
import store.model.item.ItemFactory;
import store.model.promotion.Promotion;

public class PromotionPolicyTest {
    private static PromotionPolicy promotionPolicy;
    private static Promotion defaultPromotion;

    @BeforeAll
    static void setUp() {
        promotionPolicy = new PromotionPolicy();
        defaultPromotion = Promotion.from("testPromo2+1", "2", "1", "2024-01-01", "2024-12-31");
    }

    @ParameterizedTest
    @DisplayName("[success] 구매 수량에 맞는 프로모션 증정품 총 수량을 구한다.")
    @CsvSource(value = {"1:0", "2:0", "3:1", "4:1", "5:1", "6:2", "7:2", "8:2", "9:2", "10:2"}
            , delimiter = ':')
    void calculateGiftAmount(int buyAmount, int expectedGiftAmount) {
        Item testItem = ItemFactory.from("test", "1000", "7", Optional.of(defaultPromotion));

        int giftAmount = promotionPolicy.calculateGift(testItem, buyAmount);

        assertThat(giftAmount).isEqualTo(expectedGiftAmount);
    }

    @Test
    @DisplayName("[success] 모든 프로모션의 증정품 기본 수량은 1이다.")
    void getFixedDefaultGiftAmount() {
        assertThat(promotionPolicy.getGiftAmount()).isEqualTo(1);
    }
}
