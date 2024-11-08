package store;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Customer {
    private final Cart cart;
    private final List<Product> promotionProducts;
    private int membershipDiscountAmount;

    public Customer(Cart cart) {
        this.cart = cart;
        this.promotionProducts = new ArrayList<>();
        this.membershipDiscountAmount = 0;
    }

    //cart에 들어가도 될것같긴한데... 역할은 customer가..
    public void applyPromotion() {
        Map<Product, Integer> cartMap = cart.getCart();
        for (Product product : cartMap.keySet()) {
            if (!product.hasOngoingPromotion()) {
                product.buyRegularProduct(cartMap.get(product)); //재고 점검 앞에서 했고 & buyProduct에서 함
                continue;
            }
            buyPromotionProduct(product, cartMap.get(product));
            promotionProducts.add(product);
        }
    }

    private void buyPromotionProduct(Product product, int buyAmount) {

    }

    public List<Product> getPromotionProducts() {
        return promotionProducts;
    }
}
