package store;

import camp.nextstep.edu.missionutils.DateTimes;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현

        List<Promotion> promotions = registerPromotions();

    }

    public static List<Promotion> registerPromotions() {
        try {
            List<Promotion> promotions = new ArrayList<>();
            Scanner scanner = new Scanner(new File(
                    "./src/main/resources/promotions.md"));
            String ignore = scanner.next();
            while (scanner.hasNext()) {
                String[] promotionInput = scanner.next().split(",");
                System.out.println("startyear = " + promotionInput[3]);
                System.out.println("endyear = " + promotionInput[4]);
                promotions.add(new Promotion(
                        promotionInput[0], promotionInput[1], promotionInput[2], promotionInput[3], promotionInput[4]
                ));
            }
            for (Promotion promotion : promotions) {
                System.out.println(promotion.getStartDate().getYear());
                System.out.println(promotion.getEndDate().getYear());
            }
            return promotions;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }
}
