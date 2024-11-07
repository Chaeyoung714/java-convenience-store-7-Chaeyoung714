package store;

import java.util.Date;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestForDevelopment {
    private List<Promotion> promotions = Application.registerPromotions();

    @ParameterizedTest
    @CsvSource(value = {"0,탄산2+1,2,1,2024-01-01,2024-12-31", "1,MD추천상품,1,1,2024-01-01,2024-12-31", "2,반짝할인,1,1,2024-11-01,2024-11-30"}
            , delimiter = ',')
    void 프로모션_목록을_등록한다(int index, String name, int buy, int get, String startDateStr, String endDateStr) {
        String[] startDateArray =  startDateStr.split("-");
        int startYear = Integer.parseInt(startDateArray[0]);
        int startMonth = Integer.parseInt(startDateArray[1]);
        int startDay = Integer.parseInt(startDateArray[2]);
        String[] endDateArray =  endDateStr.split("-");
        int endYear = Integer.parseInt(endDateArray[0]);
        int endMonth = Integer.parseInt(endDateArray[1]);
        int endDay = Integer.parseInt(endDateArray[2]);
        Assertions.assertThat(promotions.get(index).getName()).isEqualTo(name);
        Assertions.assertThat(promotions.get(index).getBuyAmount()).isEqualTo(buy);
        Assertions.assertThat(promotions.get(index).getGetAmount()).isEqualTo(get);
        Assertions.assertThat(promotions.get(index).getStartDate().getYear()).isEqualTo(startYear);
        Assertions.assertThat(promotions.get(index).getStartDate().getMonthValue()).isEqualTo(startMonth);
        Assertions.assertThat(promotions.get(index).getStartDate().getDayOfMonth()).isEqualTo(startDay);
        Assertions.assertThat(promotions.get(index).getEndDate().getYear()).isEqualTo(endYear);
        Assertions.assertThat(promotions.get(index).getEndDate().getMonthValue()).isEqualTo(endMonth);
        Assertions.assertThat(promotions.get(index).getEndDate().getDayOfMonth()).isEqualTo(endDay);
    }
}
