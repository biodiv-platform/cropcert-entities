package cropcert.entities.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.inject.Inject;


import cropcert.entities.dao.UnionPersonDao;
import cropcert.entities.filter.Permissions;
import cropcert.entities.model.Inspector;
import cropcert.entities.model.UnionPerson;
import cropcert.entities.util.MessageDigestPasswordEncoder;

public class UnionPersonService extends AbstractService<UnionPerson>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	private MessageDigestPasswordEncoder passwordEncoder;
	
	private static Set<String> defaultPermissions;
	static {
		defaultPermissions = new HashSet<String>();
		defaultPermissions.add(Permissions.UNION);
		//defaultPermissions.add(Permissions.FACTORY);
		//defaultPermissions.add(Permissions.CO_PERSON);
		//defaultPermissions.add(Permissions.CC_PERSON);
		//defaultPermissions.add(Permissions.FARMER);
	}
	
	@Inject
	public UnionPersonService(UnionPersonDao coPersonDao) {
		super(coPersonDao);
	}

	public UnionPerson save(String jsonString) throws JsonParseException, JsonMappingException, IOException, JSONException {
		UnionPerson ccPerson = objectMapper.readValue(jsonString, UnionPerson.class);
//		JSONObject jsonObject = new JSONObject(jsonString);
//		String password = jsonObject.getString("password");
//		password = passwordEncoder.encodePassword(password, null);
//		ccPerson.setPassword(password);
//		ccPerson.setPermissions(defaultPermissions);
		return save(ccPerson);
	}
	
	public UnionPerson findByUserId(Long userId) {
		return findByPropertyWithCondition("user_id", userId, "=");
	}

}
