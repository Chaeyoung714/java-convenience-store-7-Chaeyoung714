package store.model.item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.exceptions.NotFoundByNameException;


public class ItemsTest {
    private static Item defaultItem1;
    private static Item defaultItem2;

    @BeforeAll
    static void setUp() {
        defaultItem1 = ItemFactory.from("test1", "1000", "5", Optional.empty());
        defaultItem2 = ItemFactory.from("test2", "2000", "10", Optional.empty());
    }

    @Test
    @DisplayName("[success] 이름에 맞는 상품을 반환한다.")
    void findItemByName() {
        String testName = "test1";
        Items items = new Items(new ArrayList<>(Arrays.asList(
                defaultItem1, defaultItem2
        )));

        Item item = items.findByName(testName);

        assertThat(item.getName()).isEqualTo(testName);
    }

    @Test
    @DisplayName("[fail] 이름에 맞는 상품이 없으면 예외가 발생한다.")
    void fail_ifCannotFindItemByName() {
        String wrongName = "wrongName";
        Items items = new Items(new ArrayList<>(Arrays.asList(
                defaultItem1, defaultItem2
        )));

        assertThatThrownBy(() -> items.findByName(wrongName))
                .isInstanceOf(NotFoundByNameException.class);
    }

    @Test
    @DisplayName("[fail] 중복된 상품명을 저장하면 예외가 발생한다.")
    void fail_ifItemNameDuplicates() {
        Item duplicatedItem = ItemFactory.from("test1", "2000", "5", Optional.empty());
        assertThatThrownBy(() -> new Items(new ArrayList<>(Arrays.asList(
                defaultItem1, defaultItem2, duplicatedItem))))
                .isInstanceOf(IllegalStateException.class);
    }
}
