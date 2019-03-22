package cropcert.entity.cc;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class CollectionCenterModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(CollectionCenterDao.class).in(Scopes.SINGLETON);
		bind(CollectionCenterService.class).in(Scopes.SINGLETON);
		bind(CollectionCenterEndPoint.class).in(Scopes.SINGLETON);
	}
}
