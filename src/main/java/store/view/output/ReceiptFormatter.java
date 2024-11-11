package store.view.output;

public class ReceiptFormatter {
    private static final int LEFT_ONE_THIRD_WIDTH = 19;
    private static final int MIDDLE_ONE_THIRD_WIDTH = 9;
    private static final int RIGHT_ONE_THIRD_WITH = 7;

    public static final String PROMOTION_ITEM_STOCK = "- %s %,d원 %s %s";
    public static final String REGULAR_ITEM_STOCK = "- %s %,d원 %s";
    public static final String MINUS_NUMBER = "-%,d";

    public static final String RECEIPT_LEFT_PART = "%-" + LEFT_ONE_THIRD_WIDTH + "s";
    public static final String RECEIPT_MIDDLE_PART_STRING = "%-" + MIDDLE_ONE_THIRD_WIDTH + "s";
    public static final String RECEIPT_MIDDLE_PART_NUMBER = "%-," + MIDDLE_ONE_THIRD_WIDTH + "d";
    public static final String RECEIPT_RIGHT_PART_STRING_ALIGN_RIGHT = "%" + RIGHT_ONE_THIRD_WITH + "s";
    public static final String RECEIPT_RIGHT_PART_STRING_ALIGN_LEFT = "%-" + RIGHT_ONE_THIRD_WITH + "s";
    public static final String RECEIPT_RIGHT_PART_NUMBER = "%," + RIGHT_ONE_THIRD_WITH + "d";
}
