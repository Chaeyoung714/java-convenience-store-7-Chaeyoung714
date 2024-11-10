package store.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.exceptions.ExceptionMessages;
import store.model.consumer.Cart;
import store.model.item.Item;
import store.model.item.ItemFactory;
import store.model.item.Items;
import store.model.promotion.Promotions;
import store.util.FileScanner;

public class CartTest {
    private static List<String> defaultPromotion;
    private static Promotions defaultPromotions;
    private static Item defaultItem;
    private static Items defaultItems;

    @BeforeAll
    static void setUp() {
        defaultPromotion = new ArrayList<>(Arrays.asList("testPromo2+1,2,1,2024-01-01,2024-12-31"));
        defaultPromotions = Promotions.register(defaultPromotion);
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
        Item wrongItem = ItemFactory.from("코카콜라", "1000", "1", Optional.empty());
        carMap.put(wrongItem, 3);

        assertThatIllegalArgumentException().isThrownBy(
                        () -> Cart.of(carMap, defaultItems))
                .withMessage(ExceptionMessages.ITEM_NOT_EXISTS.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void 구매수량_양의정수_아닐시_Application에서_예외를_반환한다(int wrongAmount) {
        Map<Item, Integer> carMap = new HashMap<>();
        carMap.put(defaultItems.findByName("콜라"), wrongAmount);

        assertThatIllegalArgumentException().isThrownBy(
                        () -> Cart.of(carMap, defaultItems))
                .withMessage(ExceptionMessages.WRONG_ORDER_FORMAT.getMessage());
    }
}
