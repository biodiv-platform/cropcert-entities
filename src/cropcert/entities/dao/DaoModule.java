package cropcert.entities.dao;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class DaoModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(AdminDao.class).in(Scopes.SINGLETON);
		bind(CollectionCenterPersonDao.class).in(Scopes.SINGLETON);
		bind(CooperativePersonDao.class).in(Scopes.SINGLETON);
		bind(FactoryPersonDao.class).in(Scopes.SINGLETON);
		bind(FarmerDao.class).in(Scopes.SINGLETON);
		bind(UnionPersonDao.class).in(Scopes.SINGLETON);
		bind(UserDao.class).in(Scopes.SINGLETON);
		bind(UnionEntityDao.class).in(Scopes.SINGLETON);
		bind(CooperativeEntityDao.class).in(Scopes.SINGLETON);
		bind(CollectionCenterEntityDao.class).in(Scopes.SINGLETON);

	}
}
