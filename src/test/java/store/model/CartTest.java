package store.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.model.consumer.Cart;
import store.model.item.Item;
import store.model.item.Items;
import store.model.promotion.Promotions;
import store.util.FileScanner;

public class CartTest {
    private static Promotions defaultPromotions;
    private static Items defaultItems;

    @BeforeAll
    static void setUp() {
        defaultPromotions = Promotions.register(FileScanner.readFile("./src/main/resources/promotions.md"));
        defaultItems = Items.register(FileScanner.readFile("./src/main/resources/products.md"), defaultPromotions);
    }

    @Test
    void 상품명과_수량_문자열을_분리한다() {
        Map<Item, Integer> carMap = new HashMap<>();
        carMap.put(defaultItems.findByName("콜라"), 3);
        carMap.put(defaultItems.findByName("에너지바"), 5);
        Cart cart = Cart.of(carMap, defaultItems);

        assertThat(cart.getCart().size()).isEqualTo(2);
        assertThat(cart.getCart().get(defaultItems.findByName("콜라"))).isEqualTo(3);
        assertThat(cart.getCart().get(defaultItems.findByName("에너지바"))).isEqualTo(5);
    }

    @Test
    void 잘못된_상품명_입력시_Application에서_예외를_반환한다() {
        Map<Item, Integer> carMap = new HashMap<>();
        Item wrongItem = new Item("코카콜라", 1000, 1, 1, true, Optional.empty());
        carMap.put(wrongItem, 3);

        assertThatIllegalArgumentException().isThrownBy(
                        () -> Cart.of(carMap, defaultItems))
                .withMessage("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void 구매수량_양의정수_아닐시_Application에서_예외를_반환한다(int wrongAmount) {
        Map<Item, Integer> carMap = new HashMap<>();
        carMap.put(defaultItems.findByName("콜라"), wrongAmount);

        assertThatIllegalArgumentException().isThrownBy(
                        () -> Cart.of(carMap, defaultItems))
                .withMessage("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
    }

    /**
     * Deprecated - 다른 객체 책임임
     */
//    @ParameterizedTest
//    @ValueSource(strings = {"", "{콜라-3],[에너지바-5]", "[콜라~3],[에너지바-5]", "[콜라-3]&[에너지바-5]"})
//    void 올바르지_않은_구분자_입력시_Application에서_예외를_반환한다(String testCart) {
//        assertThatIllegalArgumentException().isThrownBy(
//                        () -> Cart.of(testCart, defaultItems))
//                .withMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
//    }
}
