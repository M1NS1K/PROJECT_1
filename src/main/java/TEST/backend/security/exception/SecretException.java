package TEST.backend.security.exception;

import org.springframework.security.core.AuthenticationException;
public class SecretException extends AuthenticationException {
	public SecretException(String message) {
		super(message);
	}
}
