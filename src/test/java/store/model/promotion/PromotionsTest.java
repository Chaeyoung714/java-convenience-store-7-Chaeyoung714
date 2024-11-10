package store.model.promotion;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.util.FileScanner;

public class PromotionsTest {
    private static Promotions defaultPromotions;
    private static List<Promotion> defaultpromotionList;

    @BeforeAll
    static void setUp() {
        defaultPromotions = Promotions.register(FileScanner.readFile("./src/main/resources/promotions.md"));
        defaultpromotionList = defaultPromotions.getPromotions();
    }

    @ParameterizedTest
    @DisplayName("[success] src/main/resources/promotions.md 파일의 프로모션 목록을 등록한다.")
    @CsvSource(value = {"0,탄산2+1,2,1,2024,01,01,2024,12,31", "1,MD추천상품,1,1,2024,01,01,2024,12,31",
            "2,반짝할인,1,1,2024,11,01,2024,11,30"}
            , delimiter = ',')
    void registerPromotionsInPromotions(int index, String name, int buy, int get, int startYear, int startMonth, int startDay,
                       int endYear, int endMonth, int endDay) {
        assertThat(defaultpromotionList.get(index).getName()).isEqualTo(name);
        assertThat(defaultpromotionList.get(index).getBuyAmount()).isEqualTo(buy);
        assertThat(defaultpromotionList.get(index).getGetAmount()).isEqualTo(get);
        assertThat(defaultpromotionList.get(index).getStartDate().getYear()).isEqualTo(startYear);
        assertThat(defaultpromotionList.get(index).getStartDate().getMonthValue()).isEqualTo(startMonth);
        assertThat(defaultpromotionList.get(index).getStartDate().getDayOfMonth()).isEqualTo(startDay);
        assertThat(defaultpromotionList.get(index).getEndDate().getYear()).isEqualTo(endYear);
        assertThat(defaultpromotionList.get(index).getEndDate().getMonthValue()).isEqualTo(endMonth);
        assertThat(defaultpromotionList.get(index).getEndDate().getDayOfMonth()).isEqualTo(endDay);
    }

    @ParameterizedTest
    @DisplayName("[successs] 오늘 날짜(2024-11-09~2024-11-11 기준) 에 맞는 프로모션을 반환하다.")
    @CsvSource(value = {"2024-11-09,2024-11-11,true", "2024-11-01,2024-11-30,true", "2024-01-01,2024-10-31,false",
            "2024-12-01,2025-07-14,false"}
            , delimiter = ',')
    void findOngoingPromotions(String startDate, String endDate, boolean expectedOngoing) {
        Promotion promotion = Promotion.from("test", "2", "1", startDate, endDate);

        assertThat(promotion.isOngoing()).isEqualTo(expectedOngoing);
    }

}
