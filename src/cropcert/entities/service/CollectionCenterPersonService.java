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

import cropcert.entities.dao.CollectionCenterPersonDao;
import cropcert.entities.filter.Permissions;
import cropcert.entities.model.CollectionCenterPerson;

public class CollectionCenterPersonService extends AbstractService<CollectionCenterPerson> {

	@Inject
	ObjectMapper objectMapper;

	private static Set<String> defaultPermissions;
	static {
		defaultPermissions = new HashSet<>();
		defaultPermissions.add(Permissions.CC_PERSON);
		defaultPermissions.add(Permissions.FARMER);
	}

	@Inject
	public CollectionCenterPersonService(CollectionCenterPersonDao ccPersonDao) {
		super(ccPersonDao);
	}

	public CollectionCenterPerson save(String jsonString)
			throws JsonParseException, JsonMappingException, IOException, JSONException {
		CollectionCenterPerson ccPerson = objectMapper.readValue(jsonString, CollectionCenterPerson.class);
		return save(ccPerson);
	}

	public CollectionCenterPerson findByUserId(Long userId) {
		return findByPropertyWithCondition("userId", userId, "=");
	}

	public List<CollectionCenterPerson> findByCollectionCenterId(Long ccCode, int limit, int offset, String orderBy) {
		return getByPropertyWithCondtion("ccCode", ccCode, "=", limit, limit, orderBy);
	}

	public CollectionCenterPerson deleteByUserId(Long userId) {
		return deleteByPropertyWithCondition("userId", userId, "=");
	}

}
