import java.util.*;

public class MainApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        AuthService auth = new AuthService();
        CryptoService crypto = new CryptoService();
        StorageService storage = new StorageService();
        SearchService search = new SearchService();
        LoggerService logger = new LoggerService();

        String currentUser = null;

        try {

            // 🔐 AUTH MENU
            while (currentUser == null) {

                System.out.println("\n1) Register\n2) Login\n3) Exit");
                int ch = sc.nextInt();
                sc.nextLine();

                if (ch == 1) {
                    auth.register();
                }

                else if (ch == 2) {
                    System.out.print("Username: ");
                    String u = sc.nextLine();

                    System.out.print("Password: ");
                    String p = sc.nextLine();

                    currentUser = auth.login(u, p);

                    if (currentUser == null) {
                        System.out.println("Invalid credentials!");
                    } else {
                        System.out.println("Login successful!");
                        logger.log(currentUser, "Logged in");
                    }
                }

                else {
                    return;
                }
            }

            // 📂 MAIN MENU
            while (true) {

                System.out.println("\n1) Upload");
                System.out.println("2) Download");
                System.out.println("3) Delete");
                System.out.println("4) List Files");
                System.out.println("5) Search");
                System.out.println("6) Exit");

                int ch = sc.nextInt();
                sc.nextLine();

                switch (ch) {

                    // ✅ UPLOAD
                    case 1:
                        System.out.print("File name: ");
                        String name = sc.nextLine();

                        System.out.print("Content: ");
                        String content = sc.nextLine();

                        String enc = crypto.encrypt(content);

                        storage.upload(currentUser, name, enc);
                        search.indexContent(currentUser, name, content);

                        logger.log(currentUser, "Uploaded file: " + name);

                        System.out.println("Uploaded successfully");
                        break;

                    // ✅ DOWNLOAD
                    case 2:
                        System.out.print("File name: ");
                        name = sc.nextLine();

                        String encrypted = storage.download(currentUser, name);

                        if (encrypted == null) {
                            System.out.println("File not found");
                            break;
                        }

                        try {
                            String decrypted = crypto.decrypt(encrypted);
                            System.out.println("Content: " + decrypted);

                            logger.log(currentUser, "Downloaded file: " + name);
                        } catch (Exception e) {
                            System.out.println("Decryption failed");
                        }

                        break;

                    // ✅ DELETE
                    case 3:
                        System.out.print("File name: ");
                        name = sc.nextLine();

                        storage.delete(currentUser, name);
                        search.removeFile(currentUser + "_" + name);

                        logger.log(currentUser, "Deleted file: " + name);
                        break;

                    // ✅ LIST FILES
                    case 4:
                        storage.listFiles(currentUser);
                        break;

                    // ✅ SEARCH
                    case 5:
                        System.out.print("Keyword: ");
                        String keyword = sc.nextLine();

                        List<String> results = search.search(keyword, currentUser);

                        if (results.isEmpty()) {
                            System.out.println("No results found");
                        } else {
                            System.out.println("Results:");
                            for (String f : results) {
                                System.out.println(f.replace(currentUser + "_", ""));
                            }
                        }

                        logger.log(currentUser, "Searched: " + keyword);
                        break;

                    // ✅ EXIT
                    case 6:
                        logger.log(currentUser, "Logged out");
                        System.out.println("Goodbye!");
                        return;

                    default:
                        System.out.println("Invalid choice");
                }
            }

        } catch (Exception e) {
            System.out.println("System error occurred");
        }
    }
}