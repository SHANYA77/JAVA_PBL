import java.io.*;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Scanner;

public class AuthService {

    private final String FILE = "users.txt";

    public void register() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE, true))) {

            Scanner sc = new Scanner(System.in);

            System.out.print("Username: ");
            String u = sc.nextLine().trim();

            System.out.print("Password: ");
            String p = hash(sc.nextLine().trim());

            bw.write(u + "," + p);
            bw.newLine();

            System.out.println("Registered!");
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public String login(String username, String password) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader(FILE));
        String line;

        String hashedInput = hash(password);

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");

            if (parts[0].equals(username) &&
                parts[1].equals(hashedInput)) {

                return username;
            }
        }

        return null;
    }

    private String hash(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return Base64.getEncoder().encodeToString(md.digest(input.getBytes()));
    }
}