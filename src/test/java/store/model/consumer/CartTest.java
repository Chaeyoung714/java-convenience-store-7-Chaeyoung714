package store.model.consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.exceptions.ExceptionMessages;
import store.model.item.Item;
import store.model.item.ItemFactory;
import store.model.item.Items;
import store.model.item.ItemsFactory;
import store.model.promotion.Promotions;

public class CartTest {
    private static Item defaultItem1;
    private static Item defaultItem2;
    private static Items defaultItems;
    private static Promotions defaultPromotions;

    private Map<Item, Integer> cartMap;

    @BeforeAll
    static void setUp() {
        defaultPromotions = Promotions.of(new ArrayList<>(Arrays.asList(
                "testPromo2+1,2,1,2024-01-01,2024-12-31"
        )));
        defaultItem1 = ItemFactory.from("test1", "1000", "5", Optional.empty());
        defaultItem2 = ItemFactory.from("test2", "2000", "10", Optional.empty());
        defaultItems = ItemsFactory.of(new ArrayList<>(Arrays.asList(
                "test1,1000,5,null", "test2,2000,10,null"
        )), defaultPromotions);
    }

    @BeforeEach
    void setUpCartMap() {
        cartMap = new HashMap<>();
    }

    @Test
    @DisplayName("[success] 구매할 상품과 구매 수량을 저장한다.")
    void saveItemAndBuyAmount() {
        cartMap.put(defaultItem1, 3);
        cartMap.put(defaultItem2, 5);
        Cart cart = Cart.of(cartMap, defaultItems);

        assertThat(cart.getCart().size()).isEqualTo(2);
        assertThat(cart.getCart().get(defaultItem1)).isEqualTo(3);
        assertThat(cart.getCart().get(defaultItem2)).isEqualTo(5);
    }

    @Test
    @DisplayName("[fail] 구매하는 상품명이 상품리스트에 없으면 예외가 발생한다.")
    void fail_ifItemNotExistsInItems() {
        Item wrongItem = ItemFactory.from("우테코카콜라", "1000", "1", Optional.empty());
        cartMap.put(wrongItem, 3);

        assertThatIllegalArgumentException().isThrownBy(
                        () -> Cart.of(cartMap, defaultItems))
                .withMessage(ExceptionMessages.ITEM_NOT_EXISTS.getMessage());
    }

    @ParameterizedTest
    @DisplayName("[fail] 구매수량이 양의 정수가 아니면 예외가 발생한다.")
    @ValueSource(ints = {0, -1, -100})
    void fail_ifBuyAmountNotPositiveInteger(int wrongAmount) {
        cartMap.put(defaultItem1, wrongAmount);

        assertThatIllegalArgumentException().isThrownBy(
                        () -> Cart.of(cartMap, defaultItems))
                .withMessage(ExceptionMessages.WRONG_ORDER_FORMAT.getMessage());
    }

    @Test
    @DisplayName("[success] 특정 상품의 구매 수량을 차감한다.")
    void deductBuyAmountOfItem() {
        cartMap.put(defaultItem1, 10);
        Cart cart = Cart.of(cartMap, defaultItems);

        cart.deductBuyAmountOf(defaultItem1, 5);

        assertThat(cart.getCart().get(defaultItem1)).isEqualTo(5);
    }

    @Test
    @DisplayName("[fail] 특정 상품의 구매 수량보다 많은 양을 차감하면 예외가 발생한다.")
    void fail_ifDeductExceedsBuyAmount() {
        cartMap.put(defaultItem1, 10);
        Cart cart = Cart.of(cartMap, defaultItems);

        assertThatThrownBy(() -> cart.deductBuyAmountOf(defaultItem1, 20))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("[success] 특정 상품의 구매 수량을 더한다.")
    void addBuyAmountOfItem() {
        cartMap.put(defaultItem1, 10);
        Cart cart = Cart.of(cartMap, defaultItems);

        cart.addBuyAmountOf(defaultItem1, 5);

        assertThat(cart.getCart().get(defaultItem1)).isEqualTo(15);
    }

    @Test
    @DisplayName("[success] 총 구매 상품 가격의 합을 구한다.")
    void calculateTotalCartItemsCost() {
        cartMap.put(defaultItem1, 10);
        cartMap.put(defaultItem2, 10);
        Cart cart = Cart.of(cartMap, defaultItems);

        assertThat(cart.calculateTotalCost()).isEqualTo(30000);
    }

    @Test
    @DisplayName("[success] 총 구매 상품 개수의 합을 구한다.")
    void calculateTotalCartItemAmount() {
        cartMap.put(defaultItem1, 10);
        cartMap.put(defaultItem2, 10);
        Cart cart = Cart.of(cartMap, defaultItems);

        assertThat(cart.calculateTotalBuyAmount()).isEqualTo(20);
    }
}
