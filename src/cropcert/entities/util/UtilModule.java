package cropcert.entities.util;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class UtilModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(MessageDigestPasswordEncoder.class).in(Scopes.SINGLETON);
		bind(SimpleUsernamePasswordAuthenticator.class).in(Scopes.SINGLETON);
	}
}
