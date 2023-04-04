package cropcert.entities.service.impl;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import cropcert.entities.service.UnionEntitiesService;
import cropcert.entities.service.UnionPersonServices;

public class ServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(UnionEntitiesService.class).to(UnionEntitiesServiceImpl.class).in(Scopes.SINGLETON);
		bind(UnionPersonServices.class).to(UnionPersonServiceImpl.class).in(Scopes.SINGLETON);

	}

}
