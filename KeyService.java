import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.Base64;

public class KeyService {
    private static SecretKey secretKey;
    private static final String KEY_FILE = "key.txt";

    public static void generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        secretKey = keyGen.generateKey();
    }

    public static void saveKey() throws Exception {
        FileOutputStream fos = new FileOutputStream(KEY_FILE);
        fos.write(Base64.getEncoder().encode(secretKey.getEncoded()));
        fos.close();
    }

    public static void loadKey() throws Exception {
        File file = new File(KEY_FILE);

        if (!file.exists()) {
            generateKey();
            saveKey();
            return;
        }

        byte[] data = java.nio.file.Files.readAllBytes(file.toPath());
        byte[] decoded = Base64.getDecoder().decode(data);

        secretKey = new SecretKeySpec(decoded, "AES");
    }

    public static SecretKey getKey() {
        return secretKey;
    }
}
