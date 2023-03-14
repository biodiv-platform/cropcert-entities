package cropcert.entities.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.inject.Inject;

import cropcert.entities.dao.UnionNewDao;
import cropcert.entities.model.UnionEntities;

public class UnionServiceNew extends AbstractService<UnionEntities> {

	@Inject
	ObjectMapper objectMapper;

	@Inject
	public UnionServiceNew(UnionNewDao dao) {
		super(dao);
	}

	public UnionEntities save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		UnionEntities union = objectMapper.readValue(jsonString, UnionEntities.class);
		return save(union);
	}

	public UnionEntities findByCode(Long code) {
		return findByPropertyWithCondition("code", code, "=");
	}

}
