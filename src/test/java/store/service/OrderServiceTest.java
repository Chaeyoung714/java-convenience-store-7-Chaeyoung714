package store.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import store.model.Cart;

public class OrderServiceTest {
    private final OrderService orderService = new OrderService();

    @Test
    void 주문_상품의_재고를_확인한다() {
        String testCart = "[콜라-19],[사이다-10]";
        Cart cart = Cart.of(testCart);

        assertThatCode(() -> orderService.checkStock(cart))
                .doesNotThrowAnyException();
    }

    @Test
    void 재고_수량_초과시_예외가_발생한다() {
        String testCart = "[콜라-21],[사이다-10]";
        Cart cart = Cart.of(testCart);

        assertThatIllegalArgumentException().isThrownBy(
                () -> orderService.checkStock(cart))
                .withMessage("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
    }
}