package cropcert.entities.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.inject.Inject;

import cropcert.entities.dao.UnionPersonDao;
import cropcert.entities.filter.Permissions;
import cropcert.entities.model.UnionPerson;

public class UnionPersonService extends AbstractService<UnionPerson> {

	@Inject
	ObjectMapper objectMapper;

	private static Set<String> defaultPermissions;
	static {
		defaultPermissions = new HashSet<String>();
		defaultPermissions.add(Permissions.UNION);
	}

	@Inject
	public UnionPersonService(UnionPersonDao coPersonDao) {
		super(coPersonDao);
	}

	public UnionPerson save(String jsonString)
			throws JsonParseException, JsonMappingException, IOException, JSONException {
		UnionPerson ccPerson = objectMapper.readValue(jsonString, UnionPerson.class);
		return save(ccPerson);
	}

	public UnionPerson findByUserId(Long userId) {
		return findByPropertyWithCondition("userId", userId, "=");
	}

}
