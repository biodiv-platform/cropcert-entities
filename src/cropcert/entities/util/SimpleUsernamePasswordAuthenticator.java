package cropcert.entities.util;

import org.pac4j.core.exception.CredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleUsernamePasswordAuthenticator {

	@SuppressWarnings("unused")
	private final Logger log = LoggerFactory.getLogger(getClass());

	protected void throwsException(final String message) throws CredentialsException {
		throw new CredentialsException(message);
	}
}
