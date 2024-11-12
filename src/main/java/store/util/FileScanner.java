package store.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileScanner {

    private FileScanner() {
    }

    public static List<String> readFile(String absoluteFilePath) {
        try {
            Scanner scanner = new Scanner(new File(absoluteFilePath));
            List<String> fileBody = new ArrayList<>();
            ignoreNext(scanner);
            while (scanner.hasNext()) {
                fileBody.add(scanner.next());
            }
            return fileBody;
        } catch (IOException e) {
            throw new IllegalStateException("[SYSTEM] File not found");
        }
    }

    private static void ignoreNext(Scanner scanner) {
        scanner.next();
    }
}
