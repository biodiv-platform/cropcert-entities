package cropcert.entities.service.impl;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import cropcert.entities.service.UnionEntitiesService;

public class ServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(UnionEntitiesService.class).to(UnionEntitiesServiceImpl.class).in(Scopes.SINGLETON);

	}

}
