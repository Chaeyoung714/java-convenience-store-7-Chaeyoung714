package store.model.item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.model.promotion.Promotions;
import store.util.FileScanner;

public class ItemsFactoryTest {
    private static Promotions defaultPromotions;
    private static Items defaultItems;

    @BeforeAll
    static void setUp() {
        defaultPromotions = Promotions.of(FileScanner.readFile("./src/main/resources/promotions.md"));
        defaultItems = ItemsFactory.of(FileScanner.readFile("./src/main/resources/products.md"), defaultPromotions);
    }

    @ParameterizedTest
    @DisplayName("[success] src/main/resources/products.md의 상품 목록을 등록한다.")
    @CsvSource(value = {
            "0,콜라,1000,10,10,탄산2+1", "1,사이다,1000,8,7,탄산2+1", "2,오렌지주스,1800,9,0,MD추천상품", "3,탄산수,1200,5,0,탄산2+1"
            , "4,물,500,0,10,null", "5,비타민워터,1500,0,6,null", "6,감자칩,1500,5,5,반짝할인", "7,초코바,1200,5,5,MD추천상품"
            , "8,에너지바,2000,0,5,null", "9,정식도시락,6400,0,8,null", "10,컵라면,1700,1,10,MD추천상품"}
            , delimiter = ','
    )
    void registerProductsInItems(int index, String name, int price, int promotionQuantity, int regularQuantity,
                                 String promotionName) {
        assertThat(defaultItems.getItems().get(index).getName()).isEqualTo(name);
        assertThat(defaultItems.getItems().get(index).getPrice()).isEqualTo(price);
        assertThat(defaultItems.getItems().get(index).getPromotionQuantity()).isEqualTo(promotionQuantity);
        assertThat(defaultItems.getItems().get(index).getRegularQuantity()).isEqualTo(regularQuantity);
        if (promotionName.equals("null")) {
            assertThatThrownBy(() -> defaultItems.getItems().get(index).getPromotion().orElseThrow())
                    .isInstanceOf(NoSuchElementException.class);
            return;
        }
        assertThat(defaultItems.getItems().get(index).getPromotion().orElseThrow().getName()).isEqualTo(
                promotionName);
    }

    @Test
    @DisplayName("[success] 상품 목록 저장 시 동일한 이름의 프로모션 상품과 일반 상품은 하나로 저장한다.")
    void integratePromotonItemAndRegularItemInOneItem() {
        assertThat(defaultItems.findByName("사이다").hasOngoingPromotion()).isTrue();
        assertThat(defaultItems.findByName("사이다").getPromotionQuantity()).isEqualTo(8);
        assertThat(defaultItems.findByName("사이다").getRegularQuantity()).isEqualTo(7);
    }

    @Test
    @DisplayName("[fail] 상품의 가격이 정수가 아닐 시 예외가 발생한다.")
    void fail_ifItemPriceNotInteger() {
        List<String> wrongItem = new ArrayList<>(Arrays.asList("wrongItem,10.00,1,null"));
        assertThatThrownBy(() -> ItemsFactory.of(wrongItem, defaultPromotions))
                .isInstanceOf(IllegalStateException.class);
    }
}
