package cropcert.entities.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cropcert.entities.dao.CollectionCenterEntityDao;
import cropcert.entities.model.CollectionCenterEntity;
import cropcert.entities.model.CooperativeEntity;
import cropcert.entities.model.UnionEntities;
import cropcert.entities.model.response.CollectionCenterShow;

public class CollectionCenterEntityService extends AbstractService<CollectionCenterEntity> {

	@Inject
	ObjectMapper objectMapper;

	@Inject
	private CooperativeEntityService cooperativeEntityService;

	@Inject
	private UnionEntityService unionEntityService;

	@Inject
	public CollectionCenterEntityService(CollectionCenterEntityDao dao) {
		super(dao);
	}

	public CollectionCenterEntity save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		CollectionCenterEntity collectionCenter = objectMapper.readValue(jsonString, CollectionCenterEntity.class);
		return save(collectionCenter);
	}

	public Map<String, Object> getOriginNames(HttpServletRequest request, String ccCodesString) {
		Long coCode = -1L;
		List<String> ccNames = new ArrayList<String>();

		for (String value : ccCodesString.split(",")) {
			Long ccCode = Long.parseLong(value);
			CollectionCenterEntity collectionCenter = findByPropertyWithCondition("code", ccCode, "=");
			coCode = collectionCenter.getCooperativeId();
			ccNames.add(collectionCenter.getName());
		}

		Map<String, Object> result = new HashMap<String, Object>();
		if (coCode != null && coCode != -1) {
			CooperativeEntity cooperative = cooperativeEntityService.findByCode(coCode);
			if (cooperative != null)
				result.put("cooperativeName", cooperative.getName());
		}
		result.put("ccNames", ccNames);

		return result;
	}

	public List<CollectionCenterShow> findAllByCoCode(HttpServletRequest request, Long coCode) {
		List<CollectionCenterEntity> collectionCenters = getByPropertyWithCondtion("coCode", coCode, "=", -1, -1,
				"name");

		List<CollectionCenterShow> collectionCenterShows = new ArrayList<CollectionCenterShow>();
		if (collectionCenters.isEmpty())
			return collectionCenterShows;

		CooperativeEntity cooperative = cooperativeEntityService.findByCode(coCode);
		UnionEntities union = unionEntityService.findByCode(cooperative.getUnionId());
		String coName = cooperative.getName();
		String unionName = union.getName();
		for (CollectionCenterEntity collectionCenter : collectionCenters) {
			CollectionCenterShow collectionCenterShow = new CollectionCenterShow();

			collectionCenterShow.setId(collectionCenter.getId());
			collectionCenterShow.setType(collectionCenter.getType());
			collectionCenterShow.setCoCode(collectionCenter.getCooperativeId());
			collectionCenterShow.setCode(collectionCenter.getCode());
			collectionCenterShow.setName(collectionCenter.getName());
			collectionCenterShow.setVillage(collectionCenter.getVillage());
			collectionCenterShow.setSubCountry(collectionCenter.getSubCountry());
			collectionCenterShow.setLatitude(collectionCenter.getLatitude());
			collectionCenterShow.setLongitude(collectionCenter.getLongitude());
			collectionCenterShow.setAltitude(collectionCenter.getAltitude());

			collectionCenterShow.setCoName(coName);
			collectionCenterShow.setUnionName(unionName);

			collectionCenterShows.add(collectionCenterShow);
		}
		return collectionCenterShows;
	}

	public CollectionCenterEntity findByName(String name, String code) {
		return ((CollectionCenterEntityDao) dao).findByName(name, code);
	}

}
