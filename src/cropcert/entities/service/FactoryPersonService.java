package cropcert.entities.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.json.JSONException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cropcert.entities.dao.FactoryPersonDao;
import cropcert.entities.filter.Permissions;
import cropcert.entities.model.FactoryPerson;

public class FactoryPersonService extends AbstractService<FactoryPerson> {

	@Inject
	ObjectMapper objectMapper;

	private static Set<String> defaultPermissions;
	static {
		defaultPermissions = new HashSet<String>();
		defaultPermissions.add(Permissions.FACTORY);
	}

	@Inject
	public FactoryPersonService(FactoryPersonDao factoryPersonDao) {
		super(factoryPersonDao);
	}

	public FactoryPerson save(String jsonString)
			throws JsonParseException, JsonMappingException, IOException, JSONException {
		FactoryPerson factoryPerson = objectMapper.readValue(jsonString, FactoryPerson.class);
		return save(factoryPerson);
	}

	public FactoryPerson findByUserId(Long userId) {
		return findByPropertyWithCondition("userId", userId, "=");
	}

}
