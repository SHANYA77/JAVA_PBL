import java.io.*;

public class AuthService {

    private static final String FILE = "users.txt";

    public static boolean isStrongPassword(String password) {
        return password.length() >= 8 &&
               password.matches(".*[0-9].*") &&
               password.matches(".*[!@#$%^&*].*");
    }

    public static void register(String user, String pass) throws Exception {

        if (!isStrongPassword(pass)) {
            System.out.println("❌ Weak password! Use 8+ chars, number & special char.");
            return;
        }

        String hashed = CryptoService.hashPassword(pass);

        FileWriter fw = new FileWriter(FILE, true);
        fw.write(user + "," + hashed + "\n");
        fw.close();

        System.out.println("✅ Registered!");
    }

    public static boolean login(String user, String pass) throws Exception {
        String hashed = CryptoService.hashPassword(pass);

        BufferedReader br = new BufferedReader(new FileReader(FILE));
        String line;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data[0].equals(user) && data[1].equals(hashed)) {
                return true;
            }
        }
        return false;
    }
}
