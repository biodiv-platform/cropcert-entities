package cropcert.entities.util;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;

public class Utility {

	private Utility() {

	}

	public static List<Class<?>> getApiAnnotatedClassesFromPackage(String packageName)
			throws ClassNotFoundException, IOException, URISyntaxException {
		List<String> classNames = getClassNamesFromPackage(packageName);
		List<Class<?>> classes = new ArrayList<>();
		for (String className : classNames) {
			Class<?> cls = Class.forName(className);
			Annotation[] annotations = cls.getAnnotations();

			for (Annotation annotation : annotations) {
				if (annotation instanceof Api || annotation instanceof ApiModel) {
					classes.add(cls);
				}
			}
		}

		return classes;
	}

	public static List<Class<?>> getEntityClassesFromPackage(String packageName)
			throws ClassNotFoundException, IOException, URISyntaxException {
		List<String> classNames = getClassNamesFromPackage(packageName);
		List<Class<?>> classes = new ArrayList<>();
		for (String className : classNames) {
			Class<?> cls = Class.forName(className);
			Annotation[] annotations = cls.getAnnotations();

			for (Annotation annotation : annotations) {
				if (annotation instanceof javax.persistence.Entity) {
					classes.add(cls);
				}
			}
		}

		return classes;
	}

	private static ArrayList<String> getClassNamesFromPackage(final String packageName)
			throws IOException, URISyntaxException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		ArrayList<String> names = new ArrayList<>();

		URL packageURL = classLoader.getResource(packageName);

		URI uri = new URI(packageURL.toString());
		File folder = new File(uri.getPath());

		try (Stream<Path> files = Files.find(Paths.get(folder.getAbsolutePath()), 999,
				(p, bfa) -> bfa.isRegularFile())) {
			files.forEach(file -> {
				String name = file.toFile().getAbsolutePath()
						.replaceAll(folder.getAbsolutePath() + File.separatorChar, "").replace(File.separatorChar, '.');
				if (name.indexOf('.') != -1) {
					name = packageName + '.' + name.substring(0, name.lastIndexOf('.'));
					names.add(name);
				}
			});
		}

		return names;
	}

}
