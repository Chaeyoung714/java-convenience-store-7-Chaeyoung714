package store.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ItemsTest {
    @ParameterizedTest
    @CsvSource(value = {
            "0,콜라,1000,10,10,탄산2+1", "1,사이다,1000,8,7,탄산2+1", "2,오렌지주스,1800,9,0,MD추천상품", "3,탄산수,1200,5,0,탄산2+1"
            , "4,물,500,0,10,null", "5,비타민워터,1500,0,6,null", "6,감자칩,1500,5,5,반짝할인", "7,초코바,1200,5,5,MD추천상품"
            , "8,에너지바,2000,0,5,null", "9,정식도시락,6400,0,8,null", "10,컵라면,1700,1,10,MD추천상품"}
            , delimiter = ','
    )
    void 상품_목록을_등록한다(int index, String name, int price, int promotionQuantity, int regularQuantity,
                     String promotionName) {
        assertThat(Items.getProducts().get(index).getName()).isEqualTo(name);
        assertThat(Items.getProducts().get(index).getPrice()).isEqualTo(price);
        assertThat(Items.getProducts().get(index).getPromotionQuantity()).isEqualTo(promotionQuantity);
        assertThat(Items.getProducts().get(index).getRegularQuantity()).isEqualTo(regularQuantity);
        if (promotionName.equals("null")) {
            assertThatThrownBy(() -> Items.getProducts().get(index).getPromotion().orElseThrow())
                    .isInstanceOf(NoSuchElementException.class);
        } else {
            assertThat(Items.getProducts().get(index).getPromotion().orElseThrow().getName()).isEqualTo(
                    promotionName);
        }
    }

    @Test
    void 상품_저장_시_프로모션_상품과_일반_상품을_통합해_저장한다() {
        assertThat(Items.findByName("사이다").hasOngoingPromotion()).isTrue();
        assertThat(Items.findByName("사이다").getPromotionQuantity()).isEqualTo(8);
        assertThat(Items.findByName("사이다").getRegularQuantity()).isEqualTo(7);
    }
}
