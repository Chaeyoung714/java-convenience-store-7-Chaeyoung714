package store.service;

import store.discountPolicy.MembershipPolicy;
import store.model.Cart;
import store.model.DiscountHistory;

public class MembershipService {
    private final MembershipPolicy membershipPolicy;

    public MembershipService(MembershipPolicy membershipPolicy) {
        this.membershipPolicy = membershipPolicy;
    }

    public void applyMemberShip(String answer, Cart cart, DiscountHistory discountHistory) {
        if (answer.equals("Y")) {
            int totalCost = cart.getTotalCost();
            int promotionAppliledAmount = discountHistory.getPromotionAppliedAmount();
            int membershipAmount = membershipPolicy.applyMembership(totalCost - promotionAppliledAmount);
            discountHistory.addMembershipDiscount(membershipAmount);
        }
    }

}
