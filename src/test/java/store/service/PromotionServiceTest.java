package store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.discountPolicy.PromotionPolicy;
import store.dto.promotion.GiftDto;
import store.dto.promotion.OutOfStockPromotionDto;
import store.exceptions.NotAddGiftException;
import store.exceptions.OutOfPromotionStockException;
import store.model.consumer.Cart;
import store.model.consumer.DiscountHistory;
import store.model.item.Item;
import store.model.item.Items;
import store.model.item.ItemsFactory;
import store.model.promotion.Promotions;

public class PromotionServiceTest {
    private static Promotions defaultPromotions;
    private static PromotionService promotionService;
    private static List<String> defaultItemList;
    private static Items defaultItems;
    private static Item promotionItem;
    private static Item regularItem;


    private DiscountHistory discountHistory;

    @BeforeAll
    static void setUp() {
        defaultPromotions = Promotions.of(new ArrayList<>(Arrays.asList(
                "testPromo2+1,2,1,2024-01-01,2024-12-31"
        )));
        promotionService = new PromotionService(new PromotionPolicy());
        defaultItemList = new ArrayList<>(Arrays.asList(
                "withPromotion,1000,6,testPromo2+1"
                , "withPromotion,1000,6,null"
                , "onlyRegular,1000,3,null"
        ));
        defaultItems = ItemsFactory.of(defaultItemList, defaultPromotions);
        promotionItem = defaultItems.findByName("withPromotion");
        regularItem = defaultItems.findByName("onlyRegular");
    }

    @BeforeEach
    void setUpDiscountHistory() {
        discountHistory = new DiscountHistory();
    }

    @Test
    @DisplayName("[success] 정가 상품만 있다면 행사할인 적용을 하지 않는다.")
    void notApplyPromotionIfNotHavingOngoingPromotion() {
        promotionService.checkAndApplyPromotion(regularItem, 3, discountHistory);

        assertThat(discountHistory.getGifts().size()).isEqualTo(0);
        assertThat(discountHistory.getPromotionDiscountAmount()).isEqualTo(0);
    }

    @Test
    @DisplayName("[success] 프로모션 상품이 있다면 행사할인을 적용한다.")
    void applyPromotionWhenHaveOngoingPromotion() {
        promotionService.checkAndApplyPromotion(promotionItem, 4, discountHistory);

        assertThat(discountHistory.getGifts().size()).isEqualTo(1);
        assertThat(discountHistory.getPromotionDiscountAmount()).isEqualTo(1000);
    }

    @Test
    @DisplayName("[success][프로모션 재고가 부족할 때] (1) 예외가 발생하여 사용자에게 질문한다.")
    void generateException_whenOutOfPromotionStock() {
        assertThatThrownBy(() -> promotionService.checkAndApplyPromotion(promotionItem, 7, discountHistory))
                .isInstanceOf(OutOfPromotionStockException.class);
    }

    @Test
    @DisplayName("[success][프로모션 재고가 부족할 때] (2-1) 부족한 수량은 정가로 계산한다.")
    void calculateWithRegularItems_whenOutOfPromotionStock() {
        promotionService.applyDefaultPromotion(promotionItem, 7, discountHistory);

        assertThat(discountHistory.getGifts().size()).isEqualTo(1);
        assertThat(discountHistory.getGifts().get(promotionItem)).isEqualTo(2);
        assertThat(discountHistory.getPromotionDiscountAmount()).isEqualTo(2000);
    }

    @Test
    @DisplayName("[success][프로모션 재고가 모자랄 때] (2-2) 부족한 수량을 제외하고 계산하여 구매 수량을 부족한 수량만큼 차감한다.")
    void calculateWithoutRegularItems_whenOutOfPromotionStock() {
        int buyAmount = 7;
        int outOfStockAmount = 1;
        Map<Item, Integer> carMap = new HashMap<>();
        carMap.put(promotionItem, buyAmount);
        Cart cart = Cart.of(carMap, defaultItems);
        OutOfStockPromotionDto testOutOfStockInfo = new OutOfStockPromotionDto(
                promotionItem, buyAmount, outOfStockAmount);

        promotionService.applyPromotionWithoutRegularItems(testOutOfStockInfo, cart, discountHistory);

        assertThat(discountHistory.getGifts().size()).isEqualTo(1);
        assertThat(discountHistory.getGifts().get(promotionItem)).isEqualTo(2);
        assertThat(discountHistory.getPromotionDiscountAmount()).isEqualTo(2000);
        assertThat(cart.getCart().get(promotionItem)).isEqualTo(buyAmount - outOfStockAmount);
    }


    @Test
    @DisplayName("[success][증정품 1개 추가가 가능할 때] (1) 예외가 발생하여 사용자에게 질문한다.")
    void generateException_whenCanAddGift() {
        assertThatThrownBy(() -> promotionService.checkAndApplyPromotion(promotionItem, 5, discountHistory))
                .isInstanceOf(NotAddGiftException.class);
    }

    @Test
    @DisplayName("[success][증정품 1개 추가가 가능할 때] (2-1) 증정품 1개를 추가한다.")
    void calculateAddingGift_whenCanAddGift() {
        int buyAmount = 5;
        int addingGiftAmount = 1;
        Map<Item, Integer> carMap = new HashMap<>();
        carMap.put(promotionItem, buyAmount);
        Cart cart = Cart.of(carMap, defaultItems);
        GiftDto testGiftInfo = new GiftDto(promotionItem, buyAmount);

        promotionService.applyPromotionAddingGift(testGiftInfo, cart, discountHistory);

        assertThat(discountHistory.getGifts().size()).isEqualTo(1);
        assertThat(discountHistory.getGifts().get(promotionItem)).isEqualTo(2);
        assertThat(discountHistory.getPromotionDiscountAmount()).isEqualTo(2000);
        assertThat(cart.getCart().get(promotionItem)).isEqualTo(buyAmount + addingGiftAmount);
    }

    @Test
    @DisplayName("[success][증정품 1개 추가가 가능할 때] (2-2) 증정품을 추가하지 않는다.")
    void calculateWithoutGift_whenCanAddGift() {
        promotionService.applyDefaultPromotion(promotionItem, 5, discountHistory);

        assertThat(discountHistory.getGifts().size()).isEqualTo(1);
        assertThat(discountHistory.getGifts().get(promotionItem)).isEqualTo(1);
        assertThat(discountHistory.getPromotionDiscountAmount()).isEqualTo(1000);
    }

}
