package store.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import store.util.FileScanner;

public class Products {
    private static final List<Product> products = register();

    private Products() {
    }

    public static List<Product> register() {
        try {
            List<String> productFileBody = FileScanner.readFile("./src/main/resources/products.md");
            List<Product> products = new ArrayList<>();
            for (String productBody : productFileBody) {
                String[] product = productBody.split(",");
                Optional<Product> registeredProduct = findByNameOrElseEmpty(product[0], products);
                if (registeredProduct.isEmpty()) {
                    products.add(Product.from(
                            product[0], product[1], product[2], product[3]
                    ));
                    continue;
                }
                registeredProduct.get().update(product[2], product[3]);
            }
            validateNameDuplication(products);
            return products;
        } catch (NullPointerException e) {
            throw new IllegalStateException("[SYSTEM] 잘못된 상품입니다.");
        }
    }

    public static Product findByName(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        throw new IllegalArgumentException("[SYSTEM] No Such Name Of Product");
    }

    private static Optional<Product> findByNameOrElseEmpty(String name, List<Product> registeredProducts) {
        for (Product product : registeredProducts) {
            if (product.getName().equals(name)) {
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }

    private static void validateNameDuplication(List<Product> products) {
        Set<String> uniqueNames = new HashSet<>();
        for (Product product : products) {
            if (!uniqueNames.add(product.getName())) {
                throw new IllegalStateException("[SYSTEM] 중복된 상품명입니다.");
            }
        }
    }

    public static List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

}
