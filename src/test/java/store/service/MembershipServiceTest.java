package store.service;

import static org.assertj.core.api.Assertions.assertThat;

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

public class MembershipServiceTest {
    private static Promotions defaultPromotions;
    private static MembershipService membershipService;
    private DiscountHistory discountHistory;

    @BeforeAll
    static void setUp() {
        defaultPromotions = Promotions.register(FileScanner.readFile("./src/main/resources/promotions.md"));
        membershipService = new MembershipService(new MembershipPolicy());
    }

    @BeforeEach
    void setUpDiscountHistory() {
        discountHistory = new DiscountHistory();
    }


    @Test
    void 프로모션_없이_멤버십할인을_받는다() {
        List<String> testItems = new ArrayList<>(Arrays.asList(
                "test,1000,6,null"
        ));
        Items items = Items.register(testItems, defaultPromotions);
        Cart cart = Cart.of("[test-5]", items);

        membershipService.applyMemberShip("Y", cart, discountHistory);

        assertThat(discountHistory.getMembershipDiscountAmount()).isEqualTo(1500);
    }

    @Test
    void 프로모션이_적용됐을때_프로모션_적용가를_제외하고_멤버십_할인을_받는다() {
        List<String> testItems = new ArrayList<>(Arrays.asList(
                "test,1000,6,탄산2+1"
        ));
        Items items = Items.register(testItems, defaultPromotions);
        Cart cart = Cart.of("[test-6]", items);

        discountHistory.addGift(items.findByName("test"), 2); //프로모션 적용
        membershipService.applyMemberShip("Y", cart, discountHistory);

        assertThat(discountHistory.getMembershipDiscountAmount()).isEqualTo(0);
    }
    @Test
    void 멤버십_할인금액이_최대금액_8000원_이상이면_8000원_할인을_받는다() {
        List<String> testItems = new ArrayList<>(Arrays.asList(
                "test,10000,6,null"
        ));
        Items items = Items.register(testItems, defaultPromotions);
        Cart cart = Cart.of("[test-5]", items);

        membershipService.applyMemberShip("Y", cart, discountHistory);

        assertThat(discountHistory.getMembershipDiscountAmount()).isEqualTo(8000);
    }
}
