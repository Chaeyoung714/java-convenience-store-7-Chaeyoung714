package store.model.promotion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PromotionTest {

    @Test
    @DisplayName("[success] 프로모션을 저장한다.")
    void createPromotion() {
        Promotion promotion = Promotion.from("test", "2", "1", "2024-01-01", "2025-12-31");
        assertThat(promotion.getName()).isEqualTo("test");
        assertThat(promotion.getBuyAmount()).isEqualTo(2);
        assertThat(promotion.getGetAmount()).isEqualTo(1);
        assertThat(promotion.getBundleAmount()).isEqualTo(3);
        assertThat(promotion.isOngoing()).isTrue();
    }

    @Test
    @DisplayName("[fail] 프로모션의 구매 수량이 양수가 아니면 예외가 발생한다.")
    void fail_ifPromotionAmountIsNotPositiveInteger() {
        assertThatThrownBy(() ->
                Promotion.from("test", "0", "1", "2024-01-01", "2024-12-31"))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("[fail] 프로모션의 날짜 구분자가 잘못되면 예외가 발생한다.")
    void fail_ifPromotionDateDelimiterIsWRong() {
        assertThatThrownBy(() ->
                        Promotion.from("test", "2", "1", "2024~01-01", "2024-12-31"))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("[fail] 프로모션의 날짜 숫자가 잘못되면 예외가 발생한다.")
    void fail_ifPromotionDateNumberIsWRong() {
        assertThatThrownBy(() ->
                        Promotion.from("test", "2", "1", "2024-13-01", "2024-12-31"))
                .isInstanceOf(IllegalStateException.class);
    }

}

