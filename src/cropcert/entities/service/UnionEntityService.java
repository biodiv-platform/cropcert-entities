package cropcert.entities.service;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.inject.Inject;

import cropcert.entities.dao.UnionEntityDao;
import cropcert.entities.model.UnionEntities;

public class UnionEntityService extends AbstractService<UnionEntities> {

	@Inject
	ObjectMapper objectMapper;

	@Inject
	public UnionEntityService(UnionEntityDao dao) {
		super(dao);
	}

	public UnionEntities save(String jsonString) throws IOException {
		UnionEntities union = objectMapper.readValue(jsonString, UnionEntities.class);
		return save(union);
	}

	public UnionEntities findByCode(Long code) {
		return findByPropertyWithCondition("code", code, "=");
	}

}
