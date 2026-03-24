import java.io.*;
import java.time.LocalDateTime;

public class LoggerService {

    private final String FILE = "log.txt";

    public void log(String user, String action) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE, true))) {

            String entry = LocalDateTime.now() + " | " + user + " | " + action;

            bw.write(entry);
            bw.newLine();

        } catch (Exception e) {
            System.out.println("Logging failed");
        }
    }
}