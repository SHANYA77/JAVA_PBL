import java.io.*;

public class SearchService {

    public static void search(String user, String keyword) throws Exception {

        File folder = new File("data/" + user);
        File[] files = folder.listFiles();

        if (files == null) {
            System.out.println("No data.");
            return;
        }

        for (File file : files) {

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();

            if (line != null) {
                String[] parts = line.split("\\|");
                String decrypted = CryptoService.decrypt(parts[1]);

                if (decrypted.toLowerCase().contains(keyword.toLowerCase())) {
                    System.out.println("📄 Found in: " + file.getName());
                    System.out.println(decrypted);
                }
            }

            br.close();
        }
    }
}
