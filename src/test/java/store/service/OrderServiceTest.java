package store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.discountPolicy.PromotionPolicy;
import store.exceptions.DidNotBringPromotionGiveProductException;
import store.exceptions.OutOfPromotionStockException;
import store.model.Cart;
import store.model.Items;
import store.model.Promotions;
import store.util.FileScanner;

public class OrderServiceTest {
    private static Promotions defaultPromotions;
    private static Items defaultItems;
    private static OrderService orderService;

    @BeforeAll
    static void setUp() {
        defaultPromotions = Promotions.register(FileScanner.readFile("./src/main/resources/promotions.md"));
        defaultItems = Items.register(FileScanner.readFile("./src/main/resources/products.md"), defaultPromotions);
        orderService = new OrderService();
    }

    @Test
    void 주문_상품의_재고를_확인한다() {
        String testCart = "[콜라-19],[사이다-10]";
        Cart cart = Cart.of(testCart, defaultItems);

        assertThatCode(() -> orderService.checkStock(cart))
                .doesNotThrowAnyException();
    }

    @Test
    void 재고_수량_초과시_예외가_발생한다() {
        String testCart = "[콜라-21],[사이다-10]";
        Cart cart = Cart.of(testCart, defaultItems);

        assertThatIllegalArgumentException().isThrownBy(
                        () -> orderService.checkStock(cart))
                .withMessage("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
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
        PromotionPolicy promotionPolicy = new PromotionPolicy();

        orderService.applyPromotion(cart);

        assertThat(promotionPolicy.getGift().size()).isEqualTo(0);
        assertThat(promotionPolicy.getDiscountAmount()).isEqualTo(0);
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

        assertThatThrownBy(() -> orderService.applyPromotion(cart))
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
        Cart cart = Cart.of("[withPromotion-7]", items);
        PromotionPolicy promotionPolicy = new PromotionPolicy();

        orderService.orderWithOrWithoutRegularItems(
                "Y", promotionPolicy, items.findByName("withPromotion"), 7, cart);

        assertThat(promotionPolicy.getGift().size()).isEqualTo(1);
        assertThat(promotionPolicy.getGift().get(items.findByName("withPromotion"))).isEqualTo(2);
        assertThat(promotionPolicy.getDiscountAmount()).isEqualTo(2000);
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
        PromotionPolicy promotionPolicy = new PromotionPolicy();

        orderService.orderWithOrWithoutRegularItems(
                "N", promotionPolicy, items.findByName("withPromotion"), 7, cart);

        assertThat(promotionPolicy.getGift().size()).isEqualTo(1);
        assertThat(promotionPolicy.getGift().get(items.findByName("withPromotion"))).isEqualTo(2);
        assertThat(promotionPolicy.getDiscountAmount()).isEqualTo(2000);
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

        assertThatThrownBy(() -> orderService.applyPromotion(cart))
                .isInstanceOf(DidNotBringPromotionGiveProductException.class);
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
        PromotionPolicy promotionPolicy = new PromotionPolicy();

        orderService.orderAddingOrWithoutGift(
                "Y", promotionPolicy, items.findByName("withPromotion"), 5, cart);

        assertThat(promotionPolicy.getGift().size()).isEqualTo(1);
        assertThat(promotionPolicy.getGift().get(items.findByName("withPromotion"))).isEqualTo(2);
        assertThat(promotionPolicy.getDiscountAmount()).isEqualTo(2000);
    }

    @Test
    @DisplayName("프로모션 상품일 때 증정품 1개 추가가 가능할 때 과정 2-2")
    void 증정품_1개_추가가_가능할때_추가하지_않는다() {
        List<String> testItems = new ArrayList<>(Arrays.asList(
                "withPromotion,1000,6,탄산2+1"
                , "withPromotion,1000,6,null"
        ));
        Items items = Items.register(testItems, defaultPromotions);
        Cart cart = Cart.of("[withPromotion-5]", items);
        PromotionPolicy promotionPolicy = new PromotionPolicy();

        orderService.orderAddingOrWithoutGift(
                "N", promotionPolicy, items.findByName("withPromotion"), 5, cart);

        assertThat(promotionPolicy.getGift().size()).isEqualTo(1);
        assertThat(promotionPolicy.getGift().get(items.findByName("withPromotion"))).isEqualTo(1);
        assertThat(promotionPolicy.getDiscountAmount()).isEqualTo(1000);
    }
}
