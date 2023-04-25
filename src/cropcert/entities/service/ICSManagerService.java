package cropcert.entities.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;

import cropcert.entities.dao.ICSManagerDao;
import cropcert.entities.filter.Permissions;
import cropcert.entities.model.ICSManager;

public class ICSManagerService extends AbstractService<ICSManager> {

	@Inject
	ObjectMapper objectMapper;

	private static Set<String> defaultPermissions;
	static {
		defaultPermissions = new HashSet<>();
		defaultPermissions.add(Permissions.ICS_MANAGER);
	}

	@Inject
	public ICSManagerService(ICSManagerDao icsManagerDao) {
		super(icsManagerDao);
	}

	public ICSManager save(String jsonString) throws IOException {
		ICSManager icsManager = objectMapper.readValue(jsonString, ICSManager.class);
		return save(icsManager);
	}

	public ICSManager findByUserId(Long userId) {
		return findByPropertyWithCondition("userId", userId, "=");
	}

}
