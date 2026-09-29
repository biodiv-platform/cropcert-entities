package cropcert.entities;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;
import org.glassfish.jersey.servlet.ServletContainer;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Injector;
import com.strandls.authentication_utility.filter.InterceptorModule;

import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.ws.rs.core.Application;

@OpenAPIDefinition(info = @Info(title = "Cropcert entities module microServices", version = "1.0", description = "Cropcert entities module microServices"), servers = {
		@Server(url = "http://localhost:8080/entities-api/api/") })
public class MyApplication extends Application {

	public static final Logger logger = LoggerFactory.getLogger(MyApplication.class);

	public static final String JWT_SALT;

	static {
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
		Properties properties = new Properties();
		try {
			if (in != null) {
				properties.load(in);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		JWT_SALT = properties.getProperty("jwtSalt", "12345678901234567890123456789012");
	}

	public MyApplication() {
		logger.info("Initializing MyApplication...");
	}

	@Override
	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<>();

		// Lifecycle listener to bridge Guice & HK2
		singletons.add(new ContainerLifecycleListener() {

			@Override
			public void onStartup(Container container) {
				logger.info("Starting up container and bridging Guice to HK2...");

				ServletContainer servletContainer = (ServletContainer) container;
				ServiceLocator serviceLocator = container.getApplicationHandler().getInjectionManager()
						.getInstance(ServiceLocator.class);

				GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
				GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);

				Injector injector = (Injector) servletContainer.getServletContext()
						.getAttribute(Injector.class.getName());

				guiceBridge.bridgeGuiceInjector(injector);
			}

			@Override
			public void onShutdown(Container container) {
				logger.info("Container shutdown...");
			}

			@Override
			public void onReload(Container container) {
				logger.info("Container reload...");
			}
		});

		singletons.add(new OpenApiResource());
		singletons.add(new InterceptorModule());

		return singletons;
	}

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();
		classes.add(OpenApiResource.class);
		return classes;
	}
}
