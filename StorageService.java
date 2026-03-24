import java.io.*;

public class StorageService {

    private final String DIR = "storage/";

    public void upload(String user, String name, String content) throws Exception {
        new File(DIR).mkdirs();

        String fileName = user + "_" + name + ".txt";

        BufferedWriter bw = new BufferedWriter(new FileWriter(DIR + fileName));
        bw.write(content.trim());
        bw.close();
    }

    public String download(String user, String name) throws Exception {
        String fileName = user + "_" + name + ".txt";

        File file = new File(DIR + fileName);
        if (!file.exists()) return null;

        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder content = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            content.append(line);
        }

        return content.toString().trim();
    }

    public void delete(String user, String name) {
        File file = new File(DIR + user + "_" + name + ".txt");

        if (file.exists() && file.delete()) {
            System.out.println("Deleted successfully");
        } else {
            System.out.println("File not found");
        }
    }

    public void listFiles(String user) {
        File folder = new File(DIR);

        for (File f : folder.listFiles()) {
            if (f.getName().startsWith(user + "_")) {
                System.out.println(f.getName().replace(user + "_", "").replace(".txt", ""));
            }
        }
    }
}