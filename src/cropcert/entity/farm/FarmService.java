package cropcert.entity.farm;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.entity.common.AbstractService;

public class FarmService extends AbstractService<Farm>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	public FarmService(FarmDao dao) {
		super(dao);
	}
	
	public Farm save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		Farm farm = objectMapper.readValue(jsonString, Farm.class);
		return save(farm);
	}

}
