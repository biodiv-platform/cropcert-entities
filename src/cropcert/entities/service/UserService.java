package cropcert.entities.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.pac4j.core.profile.CommonProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.strandls.authentication_utility.util.AuthUtil;
import com.strandls.user.controller.UserServiceApi;
import com.strandls.user.pojo.Role;
import com.strandls.user.pojo.User;

import cropcert.entities.api.CollectionCenterEntitiesApi;
import cropcert.entities.api.CooperativeEntitiesApi;
import cropcert.entities.model.CollectionCenter;
import cropcert.entities.model.CollectionCenterPerson;
import cropcert.entities.model.Cooperative;
import cropcert.entities.model.CooperativePerson;
import cropcert.entities.model.ICSManager;
import cropcert.entities.model.Inspector;
import cropcert.entities.model.UnionPerson;

public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	public static final String ROOTPATH = System.getProperty("user.home") + File.separatorChar + "cropcert-image";
	
	public static final String UNION_CODE = "unionCode";

	@Inject
	private CooperativeEntitiesApi cooperativeEntitiesApi;

	@Inject
	private CollectionCenterEntitiesApi collectionCenterEntitiesApi;

	@Inject
	private CollectionCenterPersonService collectionCenterPersonApi;

	@Inject
	private InspectorService inspectorSerciveApi;

	@Inject
	private UnionPersonService unionPersonServiceApi;

	@Inject
	private ICSManagerService icsManagerServiceApi;

	@Inject
	private CooperativePersonService cooperativePersonServiceApi;

	@Inject
	private UserServiceApi userServiceApi;

	public Map<String, Object> getMyData(HttpServletRequest request) {
		CommonProfile profile = AuthUtil.getProfileFromRequest(request);

		Map<String, Object> userData = new HashMap<>();

		try {
			User user = userServiceApi.getUser(profile.getId());

			if (user == null) {
				logger.error("Error retrieving user data: ");
				return Collections.emptyMap();

			}

			userData.put("user", user);

			if (containsRole(user.getRoles(), "UNION_PERSON")) {
				setUnionPersonData(userData, user.getId());
			} else if (containsRole(user.getRoles(), "INSPECTOR")) {
				setInspectorData(userData, user.getId());
			} else if (containsRole(user.getRoles(), "ICS_MANAGER")) {
				setICSManagerData(userData, user.getId());
			} else if (containsRole(user.getRoles(), "COOPERATIVE_PERSON")) {
				setCooperativePersonData(userData, user.getId(), request);
			} else if (containsRole(user.getRoles(), "COLLECTION_CENTER_PERSON")) {
				setCollectionCenterPersonData(userData, user.getId(), request);
			}

		} catch (Exception e) {
			logger.error("Error retrieving user data: " + e.getMessage(), e);
			return Collections.emptyMap();
		}

		return userData;
	}

	public static boolean containsRole(List<Role> list, String roleName) {
		for (Role role : list) {
			if (role.getAuthority().equals(roleName)) {
				return true;
			}
		}
		return false;
	}

	private void setUnionPersonData(Map<String, Object> userData, Long userId) {
		UnionPerson unionPerson = unionPersonServiceApi.findByUserId(userId);
		userData.put(UNION_CODE, unionPerson.getUnionCode());
	}

	private void setInspectorData(Map<String, Object> userData, Long userId) {
		Inspector inspector = inspectorSerciveApi.findByUserId(userId);
		userData.put(UNION_CODE, inspector.getUnionCode());
	}

	private void setICSManagerData(Map<String, Object> userData, Long userId) {
		ICSManager icsManager = icsManagerServiceApi.findByUserId(userId);
		userData.put(UNION_CODE, icsManager.getUnionCode());
	}

	private void setCooperativePersonData(Map<String, Object> userData, Long userId, HttpServletRequest request) {
		CooperativePerson coPerson = cooperativePersonServiceApi.findByUserId(userId);
		Long coCode = coPerson.getCoCode();
		userData.put("coCode", coCode);

		Response coResponse = cooperativeEntitiesApi.findByCode(request, coCode);
		Cooperative cooperative = (Cooperative) coResponse.getEntity();
		userData.put(UNION_CODE, cooperative.getUnionCode());
	}

	private void setCollectionCenterPersonData(Map<String, Object> userData, Long userId, HttpServletRequest request) {
		CollectionCenterPerson ccPerson = collectionCenterPersonApi.findByUserId(userId);
		Long ccCode = ccPerson.getCcCode();
		userData.put("ccCode", ccCode);

		Response ccResponse = collectionCenterEntitiesApi.findByCode(request, ccCode);
		CollectionCenter collectionCenter = (CollectionCenter) ccResponse.getEntity();
		Long coCode = collectionCenter.getCoCode();
		userData.put("coCode", coCode);

		Response coResponse = cooperativeEntitiesApi.findByCode(request, coCode);
		Cooperative cooperative = (Cooperative) coResponse.getEntity();
		userData.put(UNION_CODE, cooperative.getUnionCode());
	}

	public String addImage(InputStream inputStream, FormDataContentDisposition fileDetails,
			HttpServletRequest request) {
		String fileName = fileDetails.getFileName();

		UUID uuid = UUID.randomUUID();
		String dirPath = ROOTPATH + File.separator + uuid.toString();
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdir();
		}

		String fileLocation = dirPath + File.separatorChar + fileName;

		boolean uploaded = writeToFile(inputStream, fileLocation);

		if (uploaded) {
			return request.getRequestURI() + "/" + uuid + "/" + fileName;
		} else {
			return null;
		}
	}

	private boolean writeToFile(InputStream inputStream, String fileLocation) {
		try (OutputStream out = new FileOutputStream(new File(fileLocation))) {
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
