package store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.discountPolicy.MembershipPolicy;
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
        orderService = new OrderService(new MembershipPolicy());
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


    @Test
    void 프로모션_없이_멤버십할인을_받는다() {
        List<String> testItems = new ArrayList<>(Arrays.asList(
                "test,1000,6,null"
        ));
        Items items = Items.register(testItems, defaultPromotions);
        Cart cart = Cart.of("[test-5]", items);

        orderService.applyMemberShip("Y", cart, discountHistory);

        assertThat(discountHistory.getMembershipDiscountAmount()).isEqualTo(1500);
    }

    @Test
    void 프로모션이_적용됐을때_프로모션_적용가를_제외하고_멤버십_할인을_받는다() {
        List<String> testItems = new ArrayList<>(Arrays.asList(
                "test,1000,6,탄산2+1"
        ));
        Items items = Items.register(testItems, defaultPromotions);
        Cart cart = Cart.of("[test-6]", items);

        discountHistory.addGift(items.findByName("test"), 2);
        orderService.orderItems(cart);
        orderService.applyMemberShip("Y", cart, discountHistory);

        assertThat(discountHistory.getMembershipDiscountAmount()).isEqualTo(0);
    }

    @Test
    void 멤버십_할인금액이_최대금액_8000원_이상이면_8000원_할인을_받는다() {
        List<String> testItems = new ArrayList<>(Arrays.asList(
                "test,10000,6,null"
        ));
        Items items = Items.register(testItems, defaultPromotions);
        Cart cart = Cart.of("[test-5]", items);

        orderService.applyMemberShip("Y", cart, discountHistory);

        assertThat(discountHistory.getMembershipDiscountAmount()).isEqualTo(8000);
    }


}
