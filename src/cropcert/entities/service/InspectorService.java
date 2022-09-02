package cropcert.entities.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.json.JSONException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cropcert.entities.dao.InspectorDao;
import cropcert.entities.filter.Permissions;
import cropcert.entities.model.Inspector;

public class InspectorService extends AbstractService<Inspector> {

	@Inject
	ObjectMapper objectMapper;

	private static Set<String> defaultPermissions;
	static {
		defaultPermissions = new HashSet<String>();
		defaultPermissions.add(Permissions.INSPECTOR);
	}

	@Inject
	public InspectorService(InspectorDao inspectorDao) {
		super(inspectorDao);
	}

	public Inspector save(String jsonString)
			throws JsonParseException, JsonMappingException, IOException, JSONException {
		Inspector inspector = objectMapper.readValue(jsonString, Inspector.class);
		return save(inspector);
	}

	public Inspector findByUserId(Long userId) {
		return findByPropertyWithCondition("userId", userId, "=");
	}

}
