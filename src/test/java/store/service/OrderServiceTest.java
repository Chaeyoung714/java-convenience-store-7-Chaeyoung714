package store.service;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.exceptions.ExceptionMessages;
import store.model.consumer.Cart;
import store.model.consumer.DiscountHistory;
import store.model.consumer.DiscountHistoryTest;
import store.model.consumer.PurchaseCost;
import store.model.item.Item;
import store.model.item.Items;
import store.model.item.ItemsFactory;
import store.model.promotion.Promotion;
import store.model.promotion.Promotions;

public class OrderServiceTest {
    private static Promotions defaultPromotions;
    private static OrderService orderService;

    private Item defaultItem1;
    private Item defaultItem2;
    private Items defaultItems;

    @BeforeAll
    static void setUp() {
        defaultPromotions = Promotions.register(new ArrayList<>(Arrays.asList(
                "testPromo2+1,2,1,2024-01-01,2024-12-31"
        )));
        orderService = new OrderService();
    }

    @BeforeEach
    void setUpDiscountHistory() {
        defaultItems = ItemsFactory.of(new ArrayList<>(Arrays.asList(
                "test1,1000,5,testPromo2+1", "test2,2000,10,null"
        )), defaultPromotions);
        defaultItem1 = defaultItems.findByName("test1");
        defaultItem2 = defaultItems.findByName("test2");
    }

    @Test
    @DisplayName("[success] 주문을 등록한다.")
    void createOrder() {
        String testOrder = "[test1-3],[test2-5]";

        Cart cart = orderService.registerOrder(testOrder, defaultItems);

        assertThat(cart.getCart().size()).isEqualTo(2);
        assertThat(cart.getCart().get(defaultItem1)).isEqualTo(3);
        assertThat(cart.getCart().get(defaultItem2)).isEqualTo(5);
    }

    @Test
    @DisplayName("[success] 주문 상품의 재고가 구매 수량보다 같거나 많은지 확인한다.")
    void checkStockAmountIsSameOrMoreThanBuyAmount() {
        Map<Item, Integer> carMap = new HashMap<>();
        carMap.put(defaultItem1, 3);
        carMap.put(defaultItem2, 10);
        Cart cart = Cart.of(carMap, defaultItems);

        assertThatCode(() -> orderService.checkStock(cart))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("[fail] 재고 수량보다 구매 수량이 큰 상품이 있다면 예외가 발생한다.")
    void fail_ifBuyAmountIsMoreThanStockAmount() {
        Map<Item, Integer> carMap = new HashMap<>();
        carMap.put(defaultItem1, 6);
        carMap.put(defaultItem2, 10);
        Cart cart = Cart.of(carMap, defaultItems);

        assertThatIllegalArgumentException().isThrownBy(
                        () -> orderService.checkStock(cart))
                .withMessage(ExceptionMessages.ORDER_EXCEEDS_STOCK_QUANTITY.getMessage());
    }

    @Test
    @DisplayName("[success] 담아놓은 모든 구매 상품을 구매 수량만큼 차감한다.")
    void purchaseAllItemsInCart() {
        Map<Item, Integer> carMap = new HashMap<>();
        carMap.put(defaultItem1, 3);
        carMap.put(defaultItem2, 10);
        Cart cart = Cart.of(carMap, defaultItems);

        orderService.orderItems(cart);

        assertThat(defaultItem1.getPromotionQuantity()).isEqualTo(2);
        assertThat(defaultItem2.getRegularQuantity()).isEqualTo(0);

    }

    @Test
    @DisplayName("[success] 전체 구매 금액과 할인 후 최종 구매 금액을 계산한다.")
    void calculateTotalCost() {
        Map<Item, Integer> carMap = new HashMap<>();
        carMap.put(defaultItem1, 3);
        carMap.put(defaultItem2, 10);
        Cart cart = Cart.of(carMap, defaultItems);

        DiscountHistory discountHistory = new DiscountHistory();
        discountHistory.addGift(defaultItem1, 1);
        discountHistory.setMembershipDiscount(6000);

        PurchaseCost purchaseCost = orderService.calculateCost(cart, discountHistory);

        assertThat(purchaseCost.getPurchaseCost()).isEqualTo(16000);
        assertThat(purchaseCost.getTotalItemCost()).isEqualTo(23000);
    }
}
