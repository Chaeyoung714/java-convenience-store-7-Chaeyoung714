package store.discountPolicy;

public class MembershipPolicy {
    private int discountAmount;

    public void applyMembership(int costExceptPromotionAppliedCost) {
        int discountAmount = (costExceptPromotionAppliedCost * 3) / 10;
        if (discountAmount > 8000) {
            this.discountAmount = 8000;
            return;
        }
        this.discountAmount = discountAmount;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }
}
