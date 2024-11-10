package store.model.item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.promotion.Promotion;

public class ItemTest {
    private static Promotion defaultPromotion;

    @BeforeAll
    static void setUp() {
        defaultPromotion = Promotion.from("testPromo2+1", "2", "1", "2024-01-01", "2024-12-31");
    }

    @Test
    @DisplayName("[success] 프로모션 상품이 있을 때 정가 상품 정보를 업데이트한다.")
    void updateRegularItemInfoOnPromotionItem() {
        Item promotionItem = new Item("test", 1000, 3, 0, true, Optional.of(defaultPromotion));

        promotionItem.updateItemInfo("5", Optional.empty());

        assertThat(promotionItem.getRegularQuantity()).isEqualTo(5);
    }

    @Test
    @DisplayName("[success] 정가 상품이 있을 때 프로모션 상품 정보를 업데이트한다.")
    void updatePromotionItemInfoOnRegularItem() {
        Item promotionItem = new Item("test", 1000, 0, 3, false, Optional.empty());

        promotionItem.updateItemInfo("5", Optional.of(defaultPromotion));

        assertThat(promotionItem.getPromotionQuantity()).isEqualTo(5);
        assertThat(promotionItem.getPromotion().get().getName()).isEqualTo("testPromo2+1");
        assertThat(promotionItem.hasOngoingPromotion()).isTrue();
    }

    @Test
    @DisplayName("[fail] 정가 상품이 있을 때 정가 상품 정보를 업데이트하면 예외가 발생한다.")
    void fail_ifUpdateRegularItemInfoOnRegularItem() {
        Item promotionItem = new Item("test", 1000, 0, 3, false, Optional.empty());

        assertThatThrownBy(() -> promotionItem.updateItemInfo("5", Optional.empty()))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("[fail] 정가 상품이 있을 때 정가 상품 정보를 업데이트하면 예외가 발생한다.")
    void fail_ifUpdatePromotionItemInfoOnPromotionItem() {
        Item promotionItem = new Item("test", 1000, 0, 3, false, Optional.of(defaultPromotion));

        assertThatThrownBy(() -> promotionItem.updateItemInfo("5", Optional.of(defaultPromotion)))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("[success] 프로모션 상품 수량 이하의 수량을 구매한다.")
    void purchaseAmountLessThanPromotionQuantity() {
        Item promotionItem = new Item("test", 1000, 0, 5, false, Optional.empty());
        promotionItem.updateItemInfo("5", Optional.of(defaultPromotion));

        promotionItem.purchase(3);

        assertThat(promotionItem.getPromotionQuantity()).isEqualTo(2);
    }

    @Test
    @DisplayName("[success] 프로모션 상품 수량 이상의 수량을 구매한다.")
    void purchaseAmountMoreThanPromotionQuantity() {
        Item promotionItem = new Item("test", 1000, 0, 5, false, Optional.empty());
        promotionItem.updateItemInfo("5", Optional.of(defaultPromotion));

        promotionItem.purchase(7);

        assertThat(promotionItem.getPromotionQuantity()).isEqualTo(0);
        assertThat(promotionItem.getRegularQuantity()).isEqualTo(3);
    }

    @Test
    @DisplayName("[success] 프로모션 상품 수량이 없으면 정가 상품 수량으로 구매한다.")
    void purchaseAmountWhenPromotionQuantityIsZero() {
        Item promotionItem = new Item("test", 1000, 0, 5, false, Optional.empty());
        promotionItem.updateItemInfo("0", Optional.of(defaultPromotion));

        promotionItem.purchase(3);

        assertThat(promotionItem.getPromotionQuantity()).isEqualTo(0);
        assertThat(promotionItem.getRegularQuantity()).isEqualTo(2);
    }

    @Test
    @DisplayName("[fail] 상품 수량의 총합보다 많이 구매하면 예외가 발생한다.")
    void fail_ifPurchaseAmountOverItemTotalAmount() {
        Item promotionItem = new Item("test", 1000, 0, 5, false, Optional.empty());
        promotionItem.updateItemInfo("5", Optional.of(defaultPromotion));

        assertThatThrownBy(() -> promotionItem.purchase(11))
                .isInstanceOf(IllegalStateException.class);
    }
}
