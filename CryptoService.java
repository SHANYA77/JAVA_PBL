import java.util.Base64;

public class CryptoService {

    public String encrypt(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes());
    }

    public String decrypt(String encrypted) {
        return new String(Base64.getDecoder().decode(encrypted));
    }
}