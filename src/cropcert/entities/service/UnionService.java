package cropcert.entities.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.inject.Inject;

import cropcert.entities.dao.UnionDao;
import cropcert.entities.model.Union;

public class UnionService extends AbstractService<Union> {

	@Inject
	ObjectMapper objectMapper;

	@Inject
	public UnionService(UnionDao dao) {
		super(dao);
	}

	public Union save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		Union union = objectMapper.readValue(jsonString, Union.class);
		return save(union);
	}

	public Union findByCode(Long code) {
		return findByPropertyWithCondition("code", code, "=");
	}
}
