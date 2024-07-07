package TEST.backend.security.exception;

import javax.naming.AuthenticationException;

public class SecretException extends AuthenticationException {
	public SecretException(String message) {
		super(message);
	}
}
