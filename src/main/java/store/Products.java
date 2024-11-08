package store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Products {
    private final List<Product> products;

    public Products(List<Product> products) {
        this.products = products;
    }

    public boolean hasName(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public List<Product> findByName(String name) {
        List<Product> productsByName = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().equals(name)) {
                productsByName.add(product);
            }
        }
        return productsByName;
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }
}
