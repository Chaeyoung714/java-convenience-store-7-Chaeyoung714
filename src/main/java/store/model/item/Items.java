package store.model.item;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import store.exceptions.NotFoundByNameException;

public class Items {
    private final List<Item> items;

    protected Items(List<Item> items) {
        validateNameDuplication(items);
        this.items = items;
    }

    public Item findByName(String name) {
        for (Item item : items) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        throw new NotFoundByNameException();
    }

    private static void validateNameDuplication(List<Item> items) {
        Set<String> uniqueNames = new HashSet<>();
        for (Item item : items) {
            if (!uniqueNames.add(item.getName())) {
                throw new IllegalStateException("[SYSTEM] 중복된 상품명입니다.");
            }
        }
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }
}
