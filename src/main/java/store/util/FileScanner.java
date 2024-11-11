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
            List<String> fileBody = new ArrayList<>();
            Scanner scanner = new Scanner(new File(absoluteFilePath));
            ignoreNext(scanner);
            while (scanner.hasNext()) {
                fileBody.add(scanner.next());
            }
            return fileBody;
        } catch (IOException e) {
            System.out.println("File not found");
            return null;
        }
    }

    private static void ignoreNext(Scanner scanner) {
        scanner.next();
    }
}
