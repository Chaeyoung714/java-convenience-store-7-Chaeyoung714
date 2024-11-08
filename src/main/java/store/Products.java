package store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Products {
    private final List<Product> products;

    public Products() {
        products = new ArrayList<>(); //이름중복검증필요
    }

    public void registerProduct(String name, String price, String quantity, Optional<Promotion> promotion) {
        if (hasName(name)) {
            Product registeredProduct = findByName(name);
            registeredProduct.updateProductInfo(quantity, promotion);
            return;
        }
        products.add(new Product(name, price, quantity, promotion));
    }

    public boolean hasName(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public Product findByName(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        throw new IllegalStateException("[SYSTEM] No Such Name Of Product");
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }
}
