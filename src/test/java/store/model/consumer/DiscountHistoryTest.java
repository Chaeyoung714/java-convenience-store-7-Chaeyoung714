package store.model.consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.item.Item;
import store.model.item.ItemFactory;
import store.model.promotion.Promotion;

public class DiscountHistoryTest {
    private static Promotion defaultPromotion;
    private static Item defaultItem1;
    private static Item defaultItem2;
    private DiscountHistory discountHistory;

    @BeforeAll
    static void setUp() {
        defaultPromotion = Promotion.from("testPromo2+1", "2", "1", "2024-01-01", "2024-12-31");
        defaultItem1 = ItemFactory.from("test1", "1000", "5", Optional.of(defaultPromotion));
        defaultItem2 = ItemFactory.from("test2", "2000", "10", Optional.of(defaultPromotion));
    }

    @BeforeEach
    void setUpDiscountHistory() {
        discountHistory = new DiscountHistory();
        discountHistory.addGift(defaultItem1, 2);
        discountHistory.addGift(defaultItem2, 3);
    }

    @Test
    @DisplayName("[프로모션][success] 받은 증정품의 종류와 개수, 총 할인 금액을 누적해서 저장한다.")
    void addGiftItemListAndDiscountAmount() {
        assertThat(discountHistory.getGifts().size()).isEqualTo(2);
        assertThat(discountHistory.getGifts().get(defaultItem1)).isEqualTo(2);
        assertThat(discountHistory.getGifts().get(defaultItem2)).isEqualTo(3);
    }

    @Test
    @DisplayName("[프로모션][fail] 이미 받은 증정품을 중복해서 받는다면 예외가 발생한다.")
    void fail_ifGiftItemDuplicates() {
        assertThatThrownBy(() -> discountHistory.addGift(defaultItem1, 1))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("[프로모션][fail] 받은 증정품의 종류가 프로모션이 없는 상품이라면 예외가 발생한다.")
    void fail_ifGiftItemDoesNotHaveOngoingPromotion() {
        Item wrongItem = ItemFactory.from("wrong", "1000", "10", Optional.empty());

        assertThatThrownBy(() -> discountHistory.addGift(wrongItem, 1))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("[프로모션][success] 받은 증정품의 총 금액을 합산한다.")
    void calculatePromotionDiscountAmount() {
        assertThat(discountHistory.getPromotionDiscountAmount()).isEqualTo(8000);
    }

    @Test
    @DisplayName("[프로모션][success] 증정품을 받기 위한 구매 금액까지 합산하여 프로모션 영향을 받은 총 금액을 합산한다.")
    void calculateTotalPromotionAppliedAmount() {
        assertThat(discountHistory.getPromotionAppliedAmount()).isEqualTo(24000);
    }

    @Test
    @DisplayName("[멤버십][success] 멤버십 할인 금액을 저장한다.")
    void saveMembershipDiscountAmount() {
        discountHistory.setMembershipDiscount(2000);

        assertThat(discountHistory.getMembershipDiscountAmount()).isEqualTo(2000);
    }

    @Test
    @DisplayName("[멤버십][success] 멤버십을 2번 이상 적용하면 예외가 발생한다.")
    void fail_ifMembershipApplyDuplicates() {
        discountHistory.setMembershipDiscount(2000);

        assertThatThrownBy(() -> discountHistory.setMembershipDiscount(2000))
                .isInstanceOf(IllegalStateException.class);
    }
}
