package store.view.output;

public class ExceptionMessageOutputView {

    private ExceptionMessageOutputView() {
    }

    public static void printErrorMessage(String message) {
        System.out.println(message);
    }
}
