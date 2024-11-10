package store.service;

import static store.view.Answer.YES;

import store.discountPolicy.MembershipPolicy;
import store.model.Cart;
import store.model.DiscountHistory;

public class MembershipService {
    private final MembershipPolicy membershipPolicy;

    public MembershipService(MembershipPolicy membershipPolicy) {
        this.membershipPolicy = membershipPolicy;
    }

    public void applyMemberShip(String answer, Cart cart, DiscountHistory discountHistory) {
        if (answer.equals(YES.getFormat())) {
            int totalCost = cart.calculateTotalCost();
            int promotionAppliedAmount = discountHistory.getPromotionAppliedAmount();
            int membershipAmount = membershipPolicy.applyMembership(totalCost - promotionAppliedAmount);
            discountHistory.addMembershipDiscount(membershipAmount);
        }
    }

}
