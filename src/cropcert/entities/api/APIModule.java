package cropcert.entities.api;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class APIModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(AdminApi.class).in(Scopes.SINGLETON);
		bind(AuthenticateApi.class).in(Scopes.SINGLETON);
		bind(CollectionCenterPersonApi.class).in(Scopes.SINGLETON);
		bind(CooperativePersonApi.class).in(Scopes.SINGLETON);
		bind(FactoryPersonApi.class).in(Scopes.SINGLETON);
		bind(FarmerApi.class).in(Scopes.SINGLETON);
		bind(UnionPersonApi.class).in(Scopes.SINGLETON);
		bind(UserApi.class).in(Scopes.SINGLETON);
		bind(UnionEntitiesController.class).in(Scopes.SINGLETON);

	}
}
