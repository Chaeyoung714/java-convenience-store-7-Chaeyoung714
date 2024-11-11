package store.discountPolicy;

public class DefaultMembershipPolicy implements MembershipPolicy{
    private static final int PERCENTAGE_MULTIPLIER = 100;

    private final int discountPercent;
    private final int maxDiscountAmount;

    public DefaultMembershipPolicy() {
        this.discountPercent = 30;
        this.maxDiscountAmount = 8000;
    }

    @Override
    public int applyMembership(int costExceptPromotionAppliedAmount) {
        int discountAmount = (costExceptPromotionAppliedAmount * discountPercent) / PERCENTAGE_MULTIPLIER;
        return Math.min(maxDiscountAmount, discountAmount);
    }
}
