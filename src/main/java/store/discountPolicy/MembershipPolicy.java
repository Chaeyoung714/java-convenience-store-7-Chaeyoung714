package store.discountPolicy;

public class MembershipPolicy {
    private static final int DISCOUNT_PERCENT = 30;
    private static final int MAX_DISCOUNT_AMOUNT = 8000;

    public int applyMembership(int costExceptPromotionAppliedAmount) {
        int discountAmount = (costExceptPromotionAppliedAmount * DISCOUNT_PERCENT) / 100;
        if (discountAmount > MAX_DISCOUNT_AMOUNT) {
            return MAX_DISCOUNT_AMOUNT;
        }
        return discountAmount;
    }
}
