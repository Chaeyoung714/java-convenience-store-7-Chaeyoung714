package store;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private Cart cart;
    private List<Product> promotionProduct;
    private int membershipDiscountAmount;

    public Customer(Cart cart) {
        this.cart = cart;
        this.promotionProduct = new ArrayList<>();
        this.membershipDiscountAmount = 0;
    }


    public void applyPromotion() {

    }
}
