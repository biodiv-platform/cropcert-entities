package cropcert.entity.farm;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class FarmModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(FarmDao.class).in(Scopes.SINGLETON);
		bind(FarmService.class).in(Scopes.SINGLETON);
		bind(FarmEndPoint.class).in(Scopes.SINGLETON);
	}
}
