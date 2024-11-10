package store.model.item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.promotion.Promotion;

public class ItemFactoryTest {
    private static Promotion defaultPromotion;

    @BeforeAll
    static void setUp() {
        defaultPromotion = Promotion.from("testPromo2+1", "2", "1", "2024-01-01", "2024-12-31");
    }

    @Test
    @DisplayName("[success] 비프로모션 상품의 이름, 가격, 수량을 받아서 저장한다.")
    void createRegularItem() {
        Item item = ItemFactory.from("regular", "1000", "1", Optional.empty());

        assertThat(item.getName()).isEqualTo("regular");
        assertThat(item.getPrice()).isEqualTo(1000);
        assertThat(item.getPromotionQuantity()).isEqualTo(0);
        assertThat(item.getRegularQuantity()).isEqualTo(1);
        assertThat(item.hasOngoingPromotion()).isFalse();
        assertThat(item.getPromotion()).isEmpty();
    }

    @Test
    @DisplayName("[success] 현재 진행중인 프로모션 상품의 이름, 가격, 수량, 프로모션 행사를 받아서 저장한다.")
    void createOngoingPromotionItem() {
        Item item = ItemFactory.from("promotion", "1000", "1", Optional.of(defaultPromotion));

        assertThat(item.getName()).isEqualTo("promotion");
        assertThat(item.getPrice()).isEqualTo(1000);
        assertThat(item.getPromotionQuantity()).isEqualTo(1);
        assertThat(item.getRegularQuantity()).isEqualTo(0);
        assertThat(item.hasOngoingPromotion()).isTrue();
        assertThat(item.getPromotion().get()).isEqualTo(defaultPromotion);
    }

    @Test
    @DisplayName("[success] 현재 진행중이지 않은 프로모션 상품의 이름, 가격, 수량, 프로모션 행사를 받아서 저장한다.")
    void createNotOngoingPromotionItem() {
        Promotion notOngoingPromotion = Promotion.from(
                "testPromo2+1", "2", "1", "2023-01-01", "2023-12-31");
        Item item = ItemFactory.from("promotion", "1000", "1", Optional.of(notOngoingPromotion));

        assertThat(item.getName()).isEqualTo("promotion");
        assertThat(item.getPrice()).isEqualTo(1000);
        assertThat(item.getPromotionQuantity()).isEqualTo(1);
        assertThat(item.getRegularQuantity()).isEqualTo(0);
        assertThat(item.hasOngoingPromotion()).isFalse();
        assertThat(item.getPromotion().get()).isEqualTo(notOngoingPromotion);
    }

    @Test
    @DisplayName("[fail] 상품의 가격이 정수가 아니면 예외가 발생한다.")
    void fail_ifPriceIsNotInteger() {
        assertThatThrownBy(() -> ItemFactory.from("promotion", "10.00", "1", Optional.of(defaultPromotion)))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("[fail] 상품의 가격이 정수가 아니면 예외가 발생한다.")
    void fail_ifQuantityIsNotInteger() {
        assertThatThrownBy(() -> ItemFactory.from("promotion", "1000", "1.00", Optional.of(defaultPromotion)))
                .isInstanceOf(IllegalStateException.class);
    }
}
