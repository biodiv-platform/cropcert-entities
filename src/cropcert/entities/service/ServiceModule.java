package cropcert.entities.service;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class ServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(AdminService.class).in(Scopes.SINGLETON);
		bind(CollectionCenterPersonService.class).in(Scopes.SINGLETON);
		bind(CollectionCenterService.class).in(Scopes.SINGLETON);
		bind(CooperativeService.class).in(Scopes.SINGLETON);
		bind(CooperativePersonService.class).in(Scopes.SINGLETON);
		bind(FactoryPersonService.class).in(Scopes.SINGLETON);
		bind(FarmerService.class).in(Scopes.SINGLETON);
		bind(UnionPersonService.class).in(Scopes.SINGLETON);
		bind(UnionService.class).in(Scopes.SINGLETON);
		bind(UserService.class).in(Scopes.SINGLETON);
		bind(CropcertEntityService.class).in(Scopes.SINGLETON);
	}
}
