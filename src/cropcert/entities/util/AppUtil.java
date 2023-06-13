package cropcert.entities.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppUtil {

	public static final Map<MODULE, List<String>> ALLOWED_CONTENT_TYPES = new HashMap<>();

	static {
		ALLOWED_CONTENT_TYPES.put(MODULE.ROLES, Arrays.asList("INSPECTOR", "ICS_MANAGER", "FARMER",
				"COLLECTION_CENTER_PERSON", "COOPERATIVE_PERSON", "FACTORY_PERSON", "UNION_PERSON", "ADMIN"));
	};

	public enum MODULE {
		ROLES, INSPECTOR, ICS_MANAGER, FARMER, COLLECTION_CENTER_PERSON, COOPERATIVE_PERSON, FACTORY_PERSON,
		UNION_PERSON, ADMIN;
	}

	public static MODULE getModule(String moduleName) {
		if (moduleName == null || moduleName.isEmpty()) {
			return null;
		}
		for (MODULE module : MODULE.values()) {
			if (module.name().equalsIgnoreCase(moduleName.toLowerCase())) {
				return module;
			}
		}
		return null;
	}

}