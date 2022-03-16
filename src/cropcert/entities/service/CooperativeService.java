package cropcert.entities.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.entities.dao.CooperativeDao;
import cropcert.entities.model.Cooperative;

public class CooperativeService extends AbstractService<Cooperative> {

	@Inject
	ObjectMapper objectMapper;

	@Inject
	public CooperativeService(CooperativeDao dao) {
		super(dao);
	}

	public Cooperative save(String jsonString) throws IOException {
		Cooperative coOperative = objectMapper.readValue(jsonString, Cooperative.class);
		return save(coOperative);
	}

	public Cooperative findByCode(Long code) {
		return findByPropertyWithCondition("code", code, "=");
	}

	public List<Cooperative> getByUnion(Long unionCode) {
		return getByPropertyWithCondtion("unionCode", unionCode, "=", -1, -1, "name");
	}
}
