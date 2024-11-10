package store.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.model.Cart;
import store.model.DiscountHistory;
import store.model.Items;
import store.model.Promotions;
import store.util.FileScanner;

public class OrderServiceTest {
    private static Promotions defaultPromotions;
    private static Items defaultItems;
    private static OrderService orderService;
    private DiscountHistory discountHistory;

    @BeforeAll
    static void setUp() {
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


}
