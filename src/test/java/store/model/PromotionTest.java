package store.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PromotionTest {
    private static Promotions promotions;
    private static List<Promotion> promotionList;

    @BeforeAll
    static void setUp() {
        promotions = Promotions.register();
        promotionList = promotions.getPromotions();
    }

    @ParameterizedTest
    @CsvSource(value = {"0,탄산2+1,2,1,2024,01,01,2024,12,31", "1,MD추천상품,1,1,2024,01,01,2024,12,31",
            "2,반짝할인,1,1,2024,11,01,2024,11,30"}
            , delimiter = ',')
    void 프로모션_목록을_등록한다(int index, String name, int buy, int get, int startYear, int startMonth, int startDay,
                       int endYear, int endMonth, int endDay) {
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

    // 예외 테스트 고민중.
}