package razepl.dev.todoapp.config.jwt.interfaces;

import java.security.Key;

public interface RsaKeyService {
    Key buildSignInKey();

    Key buildVerifyKey();
}
