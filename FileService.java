import java.io.*;

public class FileService {

    private static String getUserFolder(String user) {
        String folder = "data/" + user;
        new File(folder).mkdirs(); // create folder if not exists
        return folder;
    }
    // ================= EDIT FILE =================
public static void editFile(String user, String fileName, String newText) throws Exception {

    File file = new File("data/" + user + "/" + fileName);

    if (!file.exists()) {
        System.out.println("❌ File not found.");
        return;
    }

    String encrypted = CryptoService.encrypt(newText);
    long time = System.currentTimeMillis();

    FileWriter fw = new FileWriter(file);
    fw.write(time + "|" + encrypted);
    fw.close();

    System.out.println("✏️ File updated successfully!");
}
// ================= EXPORT FILE =================
public static void exportFile(String user, String fileName) throws Exception {

    File file = new File("data/" + user + "/" + fileName);

    if (!file.exists()) {
        System.out.println("❌ File not found.");
        return;
    }

    BufferedReader br = new BufferedReader(new FileReader(file));
    String line = br.readLine();
    br.close();

    if (line == null) {
        System.out.println("❌ Empty file.");
        return;
    }

    String[] parts = line.split("\\|");
    String decrypted = CryptoService.decrypt(parts[1]);

    String exportName = "decrypted_" + fileName;

    FileWriter fw = new FileWriter(exportName);
    fw.write(decrypted);
    fw.close();

    System.out.println("📤 File exported as: " + exportName);
}
public static boolean listFiles(String user) {

    File folder = new File("data/" + user);
    File[] files = folder.listFiles();

    if (files == null || files.length == 0) {
        System.out.println("❌ No files found.");
        return false;
    }

    System.out.println("📂 Your Files:");
    for (File f : files) {
        System.out.println("- " + f.getName());
    }

    return true;
}
public static void deleteOne(String user, String fileName) {

    File file = new File("data/" + user + "/" + fileName);

    if (file.exists()) {
        if (file.delete()) {
            System.out.println("🗑️ Deleted: " + fileName);
        } else {
            System.out.println("❌ Unable to delete file.");
        }
    } else {
        System.out.println("❌ File not found.");
    }
}
    public static void store(String user, String fileName, String text) throws Exception {

    // add .txt automatically if user forgets
    if (!fileName.endsWith(".txt")) {
        fileName += ".txt";
    }

    String encrypted = CryptoService.encrypt(text);
    long time = System.currentTimeMillis();

    String path = getUserFolder(user) + "/" + fileName;

    FileWriter fw = new FileWriter(path);
    fw.write(time + "|" + encrypted);
    fw.close();

    System.out.println("✅ Stored as: " + fileName);
}

    // ================= VIEW =================
    public static void view(String user) throws Exception {

        File folder = new File(getUserFolder(user));

        File[] files = folder.listFiles();

        if (files == null || files.length == 0) {
            System.out.println("No notes found.");
            return;
        }

        for (File file : files) {

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();

            if (line != null) {
                String[] parts = line.split("\\|");

                long time = Long.parseLong(parts[0]);
                String decrypted = CryptoService.decrypt(parts[1]);

                System.out.println("📄 File: " + file.getName());
                System.out.println("Time: " + new java.util.Date(time));
                System.out.println("Data: " + decrypted);
                System.out.println("---------------------");
            }

            br.close();
        }
    }

    // ================= DELETE ALL =================
    public static void deleteAll(String user) {

        File folder = new File(getUserFolder(user));
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }

        System.out.println("🗑️ All notes deleted!");
    }
}
