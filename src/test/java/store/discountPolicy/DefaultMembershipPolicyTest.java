package store.discountPolicy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class DefaultMembershipPolicyTest {
    private static DefaultMembershipPolicy membershipPolicy;

    @BeforeAll
    static void setUp() {
        membershipPolicy = new DefaultMembershipPolicy();
    }

    @Test
    @DisplayName("[success] 주어진 금액의 30%를 계산해 멤버십 할인액을 구한다.")
    void applyFixedPercentOfMemberShipDiscount() {
        int testPrice = 10000;

        int membershipDiscountAmount = membershipPolicy.applyMembership(testPrice);

        assertThat(membershipDiscountAmount).isEqualTo(3000);
    }

    @ParameterizedTest
    @DisplayName("[success] 계산한 멤버십 할인액이 8000원을 초과하면 8000원을 반환한다.")
    @ValueSource(ints = {26670, 30000, 1000000})
    void notExceedFixedMaxDiscountAmount(int testPrice) {
        int membershipDiscountAmount = membershipPolicy.applyMembership(testPrice);

        assertThat(membershipDiscountAmount).isEqualTo(8000);
    }
}
