package cropcert.entity.co;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class CoOperativeModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(CoOperativeDao.class).in(Scopes.SINGLETON);
		bind(CoOperativeService.class).in(Scopes.SINGLETON);
		bind(CoOperativeEndPoint.class).in(Scopes.SINGLETON);
	}
}
