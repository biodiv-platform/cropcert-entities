package cropcert.entities.service;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;

import cropcert.entities.dao.CooperativeEntityDao;
import cropcert.entities.model.CooperativeEntity;

public class CooperativeEntityService extends AbstractService<CooperativeEntity> {

	@Inject
	ObjectMapper objectMapper;

	@Inject
	public CooperativeEntityService(CooperativeEntityDao dao) {
		super(dao);
	}

	public CooperativeEntity save(String jsonString) throws IOException {
		CooperativeEntity coOperative = objectMapper.readValue(jsonString, CooperativeEntity.class);
		return save(coOperative);
	}

	public CooperativeEntity findByCode(Long code) {
		return findByPropertyWithCondition("code", code, "=");
	}

	public List<CooperativeEntity> getByUnion(Long unionId) {
		return getByPropertyWithCondtion("unionCode", unionId, "=", -1, -1, "name");
	}

}
