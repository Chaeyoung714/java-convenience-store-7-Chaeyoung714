package store.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import store.util.FileScanner;

public class Items {
    private static final List<Item> ITEMS = register();

    private Items() {
    }

    public static List<Item> register() {
        try {
            List<String> productFileBody = FileScanner.readFile("./src/main/resources/products.md");
            List<Item> items = new ArrayList<>();
            for (String productBody : productFileBody) {
                String[] product = productBody.split(",");
                Optional<Item> registeredProduct = findByNameOrElseEmpty(product[0], items);
                if (registeredProduct.isEmpty()) {
                    items.add(Item.from(
                            product[0], product[1], product[2], product[3]
                    ));
                    continue;
                }
                registeredProduct.get().update(product[2], product[3]);
            }
            validateNameDuplication(items);
            return items;
        } catch (NullPointerException e) {
            throw new IllegalStateException("[SYSTEM] 잘못된 상품입니다.");
        }
    }

    public static Item findByName(String name) {
        for (Item item : ITEMS) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        throw new IllegalArgumentException("[SYSTEM] No Such Name Of Product");
    }

    private static Optional<Item> findByNameOrElseEmpty(String name, List<Item> registeredItems) {
        for (Item item : registeredItems) {
            if (item.getName().equals(name)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    private static void validateNameDuplication(List<Item> items) {
        Set<String> uniqueNames = new HashSet<>();
        for (Item item : items) {
            if (!uniqueNames.add(item.getName())) {
                throw new IllegalStateException("[SYSTEM] 중복된 상품명입니다.");
            }
        }
    }

    public static List<Item> getProducts() {
        return Collections.unmodifiableList(ITEMS);
    }

}
