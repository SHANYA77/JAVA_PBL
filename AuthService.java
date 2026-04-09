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
            System.out.println("Weak password! Use 8+ chars, number & special char.");
            return;
        }

        File userFile = new File(FILE);
        if (userFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",", 2);
                    if (data.length == 2 && data[0].equals(user)) {
                        System.out.println("Username already exists.");
                        return;
                    }
                }
            }
        }

        String hashed = CryptoService.hashPassword(pass);

        try (FileWriter fw = new FileWriter(FILE, true)) {
            fw.write(user + "," + hashed + "\n");
        }

        System.out.println("Registered!");
    }

    public static boolean login(String user, String pass) throws Exception {
        String hashed = CryptoService.hashPassword(pass);

        File userFile = new File(FILE);
        if (!userFile.exists()) {
            System.out.println("No users found. Please register first.");
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", 2);
                if (data.length == 2 && data[0].equals(user) && data[1].equals(hashed)) {
                    return true;
                }
            }
        }

        return false;
    }
}
