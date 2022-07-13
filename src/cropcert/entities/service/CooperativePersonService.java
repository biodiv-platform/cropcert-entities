package cropcert.entities.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.entities.dao.CooperativePersonDao;
import cropcert.entities.filter.Permissions;
import cropcert.entities.model.Cooperative;
import cropcert.entities.model.CooperativePerson;
import cropcert.entities.util.MessageDigestPasswordEncoder;

public class CooperativePersonService extends AbstractService<CooperativePerson>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	private MessageDigestPasswordEncoder passwordEncoder;
	
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

	public CooperativePerson save(String jsonString) throws JsonParseException, JsonMappingException, IOException, JSONException {
		CooperativePerson coPerson = objectMapper.readValue(jsonString, CooperativePerson.class);
//		JSONObject jsonObject = new JSONObject(jsonString);
//		String password = jsonObject.getString("password");
//		password = passwordEncoder.encodePassword(password, null);
//		coPerson.setPassword(password);
//		coPerson.setPermissions(defaultPermissions);
		return save(coPerson);
	}
	
	public CooperativePerson findByUserId(Long userId) {
		return findByPropertyWithCondition("user_id", userId, "=");
	}


}
