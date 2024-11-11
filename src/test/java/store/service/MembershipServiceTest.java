package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.discountPolicy.MembershipPolicy;
import store.model.consumer.Cart;
import store.model.consumer.DiscountHistory;
import store.model.item.Item;
import store.model.item.ItemFactory;
import store.model.item.Items;
import store.model.item.ItemsFactory;
import store.model.promotion.Promotion;
import store.model.promotion.Promotions;

public class MembershipServiceTest {
    private static Promotions defaultPromotions;
    private static Promotion defaultPromotion;
    private static MembershipService membershipService;
    private DiscountHistory discountHistory;

    @BeforeAll
    static void setUp() {
        defaultPromotions = Promotions.register(new ArrayList<>(Arrays.asList(
                "testPromo2+1,2,1,2024-01-01,2024-12-31"
        )));
        defaultPromotion = Promotion.from("testPromo2+1", "2", "1", "2024-01-01", "2024-12-31");
        membershipService = new MembershipService(new MembershipPolicy());
    }

    @BeforeEach
    void setUpDiscountHistory() {
        discountHistory = new DiscountHistory();
    }


    @Test
    @DisplayName("[success] 프로모션 적용 없이 멤버십 할인을 받는다.")
    void applyMembershipOnly() {
        List<String> testItems = new ArrayList<>(Arrays.asList(
                "test,1000,6,null"
        ));
        Cart cart = registerCart(testItems, 5);

        membershipService.applyMemberShip("Y", cart, discountHistory);

        assertThat(discountHistory.getMembershipDiscountAmount()).isEqualTo(1500);
    }

    @Test
    @DisplayName("[success] 프로모션 적용이 있다면 프로모션 적용가를 제외하고 멤버십 할인을 받는다.")
    void applyMembershipExcludingPromotionAppliedAmount() {
        List<String> testItems = new ArrayList<>(Arrays.asList(
                "test,1000,6,탄산2+1"
        ));
        Item testItem = ItemFactory.from("test", "1000", "6", Optional.of(defaultPromotion));
        Cart cart = registerCart(testItems, 6);
        discountHistory.addGift(testItem, 2);

        membershipService.applyMemberShip("Y", cart, discountHistory);

        assertThat(discountHistory.getMembershipDiscountAmount()).isEqualTo(0);
    }
    @Test
    @DisplayName("[success] 멤버십 할인금액이 8000원을 넘기면 8000원만 할인받는다.")
    void applyMembershipUntilMaxAmount() {
        List<String> testItems = new ArrayList<>(Arrays.asList(
                "test,10000,6,null"
        ));
        Cart cart = registerCart(testItems, 5);

        membershipService.applyMemberShip("Y", cart, discountHistory);

        assertThat(discountHistory.getMembershipDiscountAmount()).isEqualTo(8000);
    }

    private Cart registerCart(List<String> itemList, int buyAmount) {
        Items items = ItemsFactory.of(itemList, defaultPromotions);
        Map<Item, Integer> carMap = new HashMap<>();
        carMap.put(items.findByName("test"), buyAmount);
        return Cart.of(carMap, items);
    }
}
