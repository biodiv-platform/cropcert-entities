package cropcert.entities.dao;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class DaoModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(AdminDao.class).in(Scopes.SINGLETON);
		bind(CollectionCenterPersonDao.class).in(Scopes.SINGLETON);
		bind(CollectionCenterDao.class).in(Scopes.SINGLETON);
		bind(CooperativeDao.class).in(Scopes.SINGLETON);
		bind(CooperativePersonDao.class).in(Scopes.SINGLETON);
		bind(FactoryPersonDao.class).in(Scopes.SINGLETON);
		bind(FarmerDao.class).in(Scopes.SINGLETON);
		bind(UnionDao.class).in(Scopes.SINGLETON);
		bind(UnionPersonDao.class).in(Scopes.SINGLETON);
		bind(UserDao.class).in(Scopes.SINGLETON);
		bind(CropcertEntityDao.class).in(Scopes.SINGLETON);
		bind(UnionNewDao.class).in(Scopes.SINGLETON);

	}
}
