package cropcert.entities.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.json.JSONException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cropcert.entities.dao.CooperativePersonDao;
import cropcert.entities.filter.Permissions;
import cropcert.entities.model.CooperativePerson;

public class CooperativePersonService extends AbstractService<CooperativePerson> {

	@Inject
	ObjectMapper objectMapper;

	@Inject
	public CooperativePersonService(CooperativePersonDao coPersonDao) {
		super(coPersonDao);
	}

	private static Set<String> defaultPermissions;
	static {
		defaultPermissions = new HashSet<String>();
		defaultPermissions.add(Permissions.CO_PERSON);
		defaultPermissions.add(Permissions.CC_PERSON);
		defaultPermissions.add(Permissions.FARMER);
	}

	public CooperativePerson save(String jsonString)
			throws JsonParseException, JsonMappingException, IOException, JSONException {
		CooperativePerson coPerson = objectMapper.readValue(jsonString, CooperativePerson.class);
		return save(coPerson);
	}

	public CooperativePerson findByUserId(Long userId) {
		return findByPropertyWithCondition("userId", userId, "=");
	}

	public List<CooperativePerson> findByCooperativeId(Long coCode, int limit, int offset, String orderBy) {
		return getByPropertyWithCondtion("coCode", coCode, "=", limit, limit, orderBy);
	}

	public CooperativePerson deleteByUserId(Long userId) {
		return deleteByPropertyWithCondition("userId", userId, "=");
	}


}
