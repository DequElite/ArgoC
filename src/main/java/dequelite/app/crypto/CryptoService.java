package dequelite.app.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Base64;

public class CryptoService {
    public String encrypt(String plainText, String password) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, getSignKey(password));

            byte[] bytes = cipher.doFinal(plainText.getBytes());

            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String decode(String hash, String password) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, getSignKey(password));

            byte[] decoded = Base64.getDecoder().decode(hash);
            byte[] bytes = cipher.doFinal(decoded);

            return new String(bytes);

        } catch (BadPaddingException | IllegalBlockSizeException e) {
            throw new SecurityException("Invalid password or corrupted data");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public SecretKey getSignKey(String password) {
        byte[] keyBytes = password.getBytes();
        byte[] key = Arrays.copyOf(keyBytes, 16);
        return new SecretKeySpec(key, "AES");
    }
}
