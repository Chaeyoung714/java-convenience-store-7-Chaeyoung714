package store.util;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;

public class DateMonitor {

    public static LocalDate today() {
        return DateTimes.now().toLocalDate();
    }
}
