package razepl.dev.todoapp.config.jwt;

import org.springframework.stereotype.Service;
import razepl.dev.todoapp.config.jwt.interfaces.RsaKeyService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class RsaKeyServiceImpl implements RsaKeyService {
    private Key privateKey = null;
    private Key publicKey = null;

    @Override
    public final Key buildSignInKey() {
        if (privateKey == null) {
            privateKey = buildPrivateKey();
        }
        return privateKey;
    }

    @Override
    public final Key buildVerifyKey() {
        if (publicKey == null) {
            publicKey = buildPublicKey();
        }
        return publicKey;
    }

    private Key buildPublicKey() {
        String publicKeyString = readPublicKey();

        return deserializePublicKey(publicKeyString);
    }

    private Key deserializePublicKey(String publicKeyString) {
        try {
            byte[] encodedKey = Base64.getDecoder().decode(publicKeyString);

            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(encodedKey));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Key buildPrivateKey() {
        String privateKeyString = readPrivateKey();

        return deserializePrivateKey(privateKeyString);
    }

    private String readPublicKey() {
        try {
            return Files
                    .readString(Path.of("src/main/resources/public.pem"))
                    .replace("-----BEGIN PUBLIC KEY-----\n", "")
                    .replace("-----END PUBLIC KEY-----\n", "")
                    .replace("\n", "");
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private String readPrivateKey() {
        try {
            return Files
                    .readString(Path.of("src/main/resources/private.pem"))
                    .replace("-----BEGIN PRIVATE KEY-----\n", "")
                    .replace("-----END PRIVATE KEY-----\n", "")
                    .replace("\n", "");
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private PrivateKey deserializePrivateKey(String serializedPublicKey) {
        try {
            byte[] encodedKey = Base64.getDecoder().decode(serializedPublicKey);

            return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
