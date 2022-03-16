package cropcert.entities.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.entities.dao.CollectionCenterDao;
import cropcert.entities.model.CollectionCenter;
import cropcert.entities.model.Cooperative;
import cropcert.entities.model.Union;
import cropcert.entities.model.response.CollectionCenterShow;

public class CollectionCenterService extends AbstractService<CollectionCenter>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	private CooperativeService cooperativeService;
	
	@Inject
	private UnionService unionService;
	
	@Inject
	public CollectionCenterService(CollectionCenterDao dao) {
		super(dao);
	}
	
	public CollectionCenter save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		CollectionCenter collectionCenter = objectMapper.readValue(jsonString, CollectionCenter.class);
		return save(collectionCenter);
	}

	public Map<String, Object> getOriginNames(HttpServletRequest request, String ccCodesString) {
		Long coCode = -1L;
		List<String> ccNames = new ArrayList<String>();
		
		for(String value : ccCodesString.split(",")) {
			Long ccCode = Long.parseLong(value);
			CollectionCenter collectionCenter = findByPropertyWithCondition("code", ccCode, "=");
			coCode = collectionCenter.getCoCode();
			ccNames.add(collectionCenter.getName());
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		if(coCode != null && coCode != -1) {
			Cooperative cooperative = cooperativeService.findByCode(coCode);
			if(cooperative != null)
				result.put("cooperativeName", cooperative.getName());
		}
		result.put("ccNames", ccNames);
		
		return result;
	}

	public List<CollectionCenterShow> findAllByCoCode(HttpServletRequest request, Long coCode) {
		List<CollectionCenter> collectionCenters = getByPropertyWithCondtion("coCode", coCode,
				"=", -1, -1, "name");
		
		List<CollectionCenterShow> collectionCenterShows = new ArrayList<CollectionCenterShow>();
		if(collectionCenters.isEmpty()) return collectionCenterShows;
		
		Cooperative cooperative = cooperativeService.findByCode(coCode);
		Union union = unionService.findByCode(cooperative.getUnionCode());
		String coName = cooperative.getName();
		String unionName = union.getName();
		for(CollectionCenter collectionCenter : collectionCenters) {
			CollectionCenterShow collectionCenterShow = new CollectionCenterShow(collectionCenter, coName, unionName);
			collectionCenterShows.add(collectionCenterShow);
		}
		return collectionCenterShows;
	}

	public CollectionCenter findByName(String name, String code) {
		return ((CollectionCenterDao)dao).findByName(name, code);
	}

}
