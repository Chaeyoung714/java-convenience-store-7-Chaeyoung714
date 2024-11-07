package store;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestForDevelopment {
    private Promotions promotions = Application.registerPromotions();
    private List<Promotion> promotionList = promotions.getPromotions();
    private List<Product> products = Application.registerProducts(promotions);
    private static int productsIndex = 0; //리팩토링 예정

    @ParameterizedTest
    @CsvSource(value = {"0,탄산2+1,2,1,2024-01-01,2024-12-31", "1,MD추천상품,1,1,2024-01-01,2024-12-31",
            "2,반짝할인,1,1,2024-11-01,2024-11-30"}
            , delimiter = ',')
    void 프로모션_목록을_등록한다(int index, String name, int buy, int get, String startDateStr, String endDateStr) {
        String[] startDateArray = startDateStr.split("-");
        int startYear = Integer.parseInt(startDateArray[0]);
        int startMonth = Integer.parseInt(startDateArray[1]);
        int startDay = Integer.parseInt(startDateArray[2]);

        String[] endDateArray = endDateStr.split("-");
        int endYear = Integer.parseInt(endDateArray[0]);
        int endMonth = Integer.parseInt(endDateArray[1]);
        int endDay = Integer.parseInt(endDateArray[2]);

        assertThat(promotionList.get(index).getName()).isEqualTo(name);
        assertThat(promotionList.get(index).getBuyAmount()).isEqualTo(buy);
        assertThat(promotionList.get(index).getGetAmount()).isEqualTo(get);
        assertThat(promotionList.get(index).getStartDate().getYear()).isEqualTo(startYear);
        assertThat(promotionList.get(index).getStartDate().getMonthValue()).isEqualTo(startMonth);
        assertThat(promotionList.get(index).getStartDate().getDayOfMonth()).isEqualTo(startDay);
        assertThat(promotionList.get(index).getEndDate().getYear()).isEqualTo(endYear);
        assertThat(promotionList.get(index).getEndDate().getMonthValue()).isEqualTo(endMonth);
        assertThat(promotionList.get(index).getEndDate().getDayOfMonth()).isEqualTo(endDay);
    }

    @Test
    void 비할인_프로모션_객체_등록() {
        assertThat(promotionList.get(3).getName()).isEqualTo("null");
        assertThat(promotionList.get(3).getBuyAmount()).isEqualTo(0);
        assertThat(promotionList.get(3).getGetAmount()).isEqualTo(0);
        assertThat(promotionList.get(3).getStartDate()).isNull();
        assertThat(promotionList.get(3).getEndDate()).isNull();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "콜라,1000,10,탄산2+1", "콜라,1000,10,null", "사이다,1000,8,탄산2+1", "사이다,1000,7,null", "오렌지주스,1800,9,MD추천상품",
            "탄산수,1200,5,탄산2+1", "물,500,10,null", "비타민워터,1500,6,null", "감자칩,1500,5,반짝할인", "감자칩,1500,5,null",
            "초코바,1200,5,MD추천상품", "초코바,1200,5,null", "에너지바,2000,5,null", "정식도시락,6400,8,null", "컵라면,1700,1,MD추천상품",
            "컵라면,1700,10,null"}
            , delimiter = ','
    )
    void 상품_목록을_등록한다(String name, int price, int quantity, String promotionName) {
        assertThat(products.get(productsIndex).getName()).isEqualTo(name);
        assertThat(products.get(productsIndex).getPrice()).isEqualTo(price);
        assertThat(products.get(productsIndex).getQuantity()).isEqualTo(quantity);
        assertThat(products.get(productsIndex).getPromotion().getName()).isEqualTo(promotionName);
        productsIndex++; //이러면 테스트가 절차적이어진다. 연쇄적으로 영향을 받음 -> 리팩토링 필요
    }
}
