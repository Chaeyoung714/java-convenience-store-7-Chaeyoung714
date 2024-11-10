package store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.discountPolicy.MembershipPolicy;
import store.discountPolicy.PromotionPolicy;
import store.dto.GiftDto;
import store.dto.OutOfStockPromotionDto;
import store.exceptions.NotAddGiftException;
import store.exceptions.OutOfPromotionStockException;
import store.model.Cart;
import store.model.DiscountHistory;
import store.model.Items;
import store.model.Promotions;
import store.util.FileScanner;

public class PromotionServiceTest {
    private static Promotions defaultPromotions;
    private static PromotionService promotionService;
    private DiscountHistory discountHistory;

    @BeforeAll
    static void setUp() {
        defaultPromotions = Promotions.register(FileScanner.readFile("./src/main/resources/promotions.md"));
        promotionService = new PromotionService(new PromotionPolicy());
    }

    @BeforeEach
    void setUpDiscountHistory() {
        discountHistory = new DiscountHistory();
    }

    @Test
    void 프로모션_상품이_아니면_행사할인_없이_주문이_완료된다() {
        List<String> testItems = new ArrayList<>(Arrays.asList(
                "withPromotion,1000,6,탄산2+1"
                , "withPromotion,1000,6,null"
                , "onlyRegular,1000,3,null"
        ));
        Items items = Items.register(testItems, defaultPromotions);
        Cart cart = Cart.of("[onlyRegular-3]", items);

        promotionService.checkAndApplyPromotion(cart, discountHistory);

        assertThat(discountHistory.getGifts().size()).isEqualTo(0);
        assertThat(discountHistory.getPromotionDiscountAmount()).isEqualTo(0);
    }

    @Test
    @DisplayName("프로모션 상품일때 프로모션 재고가 모자랄때 과정 1")
    void 프로모션_상품일때_프로모션_재고가_모자라면_예외를_발생하여_사용자에게_질문한다() {
        List<String> testItems = new ArrayList<>(Arrays.asList(
                "withPromotion,1000,6,탄산2+1"
                , "withPromotion,1000,6,null"
        ));
        Items items = Items.register(testItems, defaultPromotions);
        Cart cart = Cart.of("[withPromotion-7]", items);

        assertThatThrownBy(() -> promotionService.checkAndApplyPromotion(cart, discountHistory))
                .isInstanceOf(OutOfPromotionStockException.class);
    }

    @Test
    @DisplayName("프로모션 상품일때 프로모션 재고가 모자랄때 과정 2-1")
    void 프로모션_재고가_모자랄때_정가로_계산한다() {
        List<String> testItems = new ArrayList<>(Arrays.asList(
                "withPromotion,1000,6,탄산2+1"
                , "withPromotion,1000,6,null"
        ));
        Items items = Items.register(testItems, defaultPromotions);

        promotionService.applyDefaultPromotion(items.findByName("withPromotion"), 7, discountHistory);

        assertThat(discountHistory.getGifts().size()).isEqualTo(1);
        assertThat(discountHistory.getGifts().get(items.findByName("withPromotion"))).isEqualTo(2);
        assertThat(discountHistory.getPromotionDiscountAmount()).isEqualTo(2000);
    }

    @Test
    // Cart가 달라지는 것으로, 2-1과 비교해 필요 없음.
    @DisplayName("프로모션 상품일때 프로모션 재고가 모자랄때 과정 2-2")
    void 프로모션_재고가_모자랄때_제외하고_계산한다() {
        List<String> testItems = new ArrayList<>(Arrays.asList(
                "withPromotion,1000,6,탄산2+1"
                , "withPromotion,1000,6,null"
        ));
        Items items = Items.register(testItems, defaultPromotions);
        Cart cart = Cart.of("[withPromotion-7]", items);
        OutOfStockPromotionDto testOutOfStockInfo = OutOfStockPromotionDto.from(items.findByName("withPromotion"), 1);

        promotionService.applyPromotionWithoutRegularItems(testOutOfStockInfo, cart, discountHistory);

        assertThat(discountHistory.getGifts().size()).isEqualTo(1);
        assertThat(discountHistory.getGifts().get(items.findByName("withPromotion"))).isEqualTo(2);
        assertThat(discountHistory.getPromotionDiscountAmount()).isEqualTo(2000);
    }


    @Test
    @DisplayName("프로모션 상품일 때 증정품 1개 추가가 가능할 때 과정 1")
    void 프로모션_상품일때_증정품_1개_추가가_가능하면_예외를_발생하여_사용자에게_질문한다() {
        List<String> testItems = new ArrayList<>(Arrays.asList(
                "withPromotion,1000,6,탄산2+1"
                , "withPromotion,1000,6,null"
        ));
        Items items = Items.register(testItems, defaultPromotions);
        Cart cart = Cart.of("[withPromotion-5]", items);

        assertThatThrownBy(() -> promotionService.checkAndApplyPromotion(cart, discountHistory))
                .isInstanceOf(NotAddGiftException.class);
    }

    @Test
    @DisplayName("프로모션 상품일 때 증정품 1개 추가가 가능할 때 과정 2-1")
    void 증정품_1개_추가가_가능할때_추가한다() {
        List<String> testItems = new ArrayList<>(Arrays.asList(
                "withPromotion,1000,6,탄산2+1"
                , "withPromotion,1000,6,null"
        ));
        Items items = Items.register(testItems, defaultPromotions);
        Cart cart = Cart.of("[withPromotion-5]", items);
        GiftDto testGiftInfo = new GiftDto(items.findByName("withPromotion"), 5);

        promotionService.applyPromotionAddingGift(testGiftInfo, cart, discountHistory);

        assertThat(discountHistory.getGifts().size()).isEqualTo(1);
        assertThat(discountHistory.getGifts().get(items.findByName("withPromotion"))).isEqualTo(2);
        assertThat(discountHistory.getPromotionDiscountAmount()).isEqualTo(2000);
    }

    @Test
    @DisplayName("프로모션 상품일 때 증정품 1개 추가가 가능할 때 과정 2-2")
    void 증정품_1개_추가가_가능할때_추가하지_않는다() {
        List<String> testItems = new ArrayList<>(Arrays.asList(
                "withPromotion,1000,6,탄산2+1"
                , "withPromotion,1000,6,null"
        ));
        Items items = Items.register(testItems, defaultPromotions);

        promotionService.applyDefaultPromotion(items.findByName("withPromotion"), 5, discountHistory);

        assertThat(discountHistory.getGifts().size()).isEqualTo(1);
        assertThat(discountHistory.getGifts().get(items.findByName("withPromotion"))).isEqualTo(1);
        assertThat(discountHistory.getPromotionDiscountAmount()).isEqualTo(1000);
    }

}
