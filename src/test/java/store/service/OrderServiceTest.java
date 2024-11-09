package store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import store.discountPolicy.PromotionPolicy;
import store.model.Cart;
import store.model.Item;
import store.model.Items;
import store.model.Promotions;

public class OrderServiceTest {
    private static OrderService orderService;
    private static Items defaultItems;
    private static Promotions defaultPromotions;

    @BeforeAll
    static void setUp() {
        defaultPromotions = Promotions.register();
        defaultItems = Items.register(defaultPromotions);
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

//    @Test
//    void 프로모션_상품이_없으면_행사할인이_없다() {
//        List<String> testItems = new ArrayList<>(Arrays.asList(
//                "withPromotion,1000,5,탄산2+1"
//                , "withPromotion,1000,5,null"
//                , "onlyRegular,1000,3,null"
//        ));
//        Items.register(testItems); // 매번 하나를 돌려쓰는 늑김.
//
//        Cart cart = Cart.of("[onlyRegular-3]");
//        PromotionPolicy promotionPolicy = new PromotionPolicy();
//
//        orderService.applyPromotion(promotionPolicy, cart);
//
//        assertThat(promotionPolicy.getGift().size()).isEqualTo(0);
//        assertThat(promotionPolicy.getDiscountAmount()).isEqualTo(0);
//    }
}
