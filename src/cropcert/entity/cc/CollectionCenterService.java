package cropcert.entity.cc;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.entity.common.AbstractService;

public class CollectionCenterService extends AbstractService<CollectionCenter>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	public CollectionCenterService(CollectionCenterDao dao) {
		super(dao);
	}
	
	public CollectionCenter save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		CollectionCenter collectionCenter = objectMapper.readValue(jsonString, CollectionCenter.class);
		return save(collectionCenter);
	}

}
