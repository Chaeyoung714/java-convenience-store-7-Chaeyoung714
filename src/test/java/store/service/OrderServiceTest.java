package store.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.model.consumer.Cart;
import store.model.consumer.DiscountHistory;
import store.model.item.Item;
import store.model.item.Items;
import store.model.promotion.Promotions;
import store.util.FileScanner;

public class OrderServiceTest {
    private static Promotions defaultPromotions;
    private static Items defaultItems;
    private static OrderService orderService;
    private DiscountHistory discountHistory;

    @BeforeAll
    static void setUp() {
        //테스트데이터로 수정하기
        defaultPromotions = Promotions.register(FileScanner.readFile("./src/main/resources/promotions.md"));
        defaultItems = Items.register(FileScanner.readFile("./src/main/resources/products.md"), defaultPromotions);
        orderService = new OrderService();
    }

    @BeforeEach
    void setUpDiscountHistory() {
        discountHistory = new DiscountHistory();
    }

    @Test
    void 주문_상품의_재고를_확인한다() {
        Map<Item, Integer> carMap = new HashMap<>();
        carMap.put(defaultItems.findByName("콜라"), 19);
        carMap.put(defaultItems.findByName("사이다"), 10);
        Cart cart = Cart.of(carMap, defaultItems);

        assertThatCode(() -> orderService.checkStock(cart))
                .doesNotThrowAnyException();
    }

    @Test
    void 재고_수량_초과시_예외가_발생한다() {
        Map<Item, Integer> carMap = new HashMap<>();
        carMap.put(defaultItems.findByName("콜라"), 21);
        carMap.put(defaultItems.findByName("사이다"), 10);
        Cart cart = Cart.of(carMap, defaultItems);

        assertThatIllegalArgumentException().isThrownBy(
                        () -> orderService.checkStock(cart))
                .withMessage("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
    }


}
