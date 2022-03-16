package cropcert.entities;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import cropcert.entities.api.APIModule;
import cropcert.entities.dao.DaoModule;
import cropcert.entities.filter.FilterModule;
import cropcert.entities.util.AuthUtility;
import cropcert.entities.util.Utility;

public class UserServletContextListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		
		Injector injector = Guice.createInjector(new JerseyServletModule() {
			@Override
			protected void configureServlets() {
				
				Configuration configuration = new Configuration();
				
				try {
					for (Class<?> cls : Utility.getEntityClassesFromPackage("cropcert")) {
						configuration.addAnnotatedClass(cls);
					}
				} catch (ClassNotFoundException | IOException | URISyntaxException e) {
					e.printStackTrace();
				}
				
				configuration = configuration.configure();
				SessionFactory sessionFactory = configuration.buildSessionFactory();
				
				bind(SessionFactory.class).toInstance(sessionFactory);
				bind(ObjectMapper.class).in(Scopes.SINGLETON);
				bind(AuthUtility.class).in(Scopes.SINGLETON);
				
				Map<String, String> props = new HashMap<String, String>();
				props.put("javax.ws.rs.Application", MyApplication.class.getName());
				props.put("jersey.config.server.wadl.disableWadl", "true");
				
				serve("/api/*").with(GuiceContainer.class, props);
			}
		}, new DaoModule(), new APIModule(), new FilterModule());
		
		return injector; 
	}
}
