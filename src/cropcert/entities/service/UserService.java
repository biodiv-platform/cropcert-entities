package cropcert.entities.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.pac4j.core.profile.CommonProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.strandls.authentication_utility.util.AuthUtil;
import com.strandls.user.controller.UserServiceApi;
import com.strandls.user.pojo.Role;
import com.strandls.user.pojo.User;

import cropcert.entities.model.CollectionCenterEntity;
import cropcert.entities.model.CollectionCenterPerson;
import cropcert.entities.model.CooperativeEntity;
import cropcert.entities.model.CooperativePerson;
import cropcert.entities.model.ICSManager;
import cropcert.entities.model.Inspector;
import cropcert.entities.model.UnionEntities;
import cropcert.entities.model.UnionPerson;
import net.minidev.json.JSONArray;

public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	public static final String ROOTPATH = System.getProperty("user.home") + File.separatorChar + "cropcert-image";
	public static final String UNION_CODE = "unionCode";

	@Inject
	private CooperativeEntityService cooperativeEntityService;

	@Inject
	private CollectionCenterEntityService collectionCenterEntityService;

	@Inject
	private CollectionCenterPersonService collectionCenterPersonApi;

	@Inject
	private InspectorService inspectorSerciveApi;

	@Inject
	private UnionPersonService unionPersonServiceApi;

	@Inject
	private UnionEntityService unionEntitiesServiceApi;

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

			if (containsRole(user.getRoles(), "ROLE_ADMIN")) {
				setAdminPersonData(userData, user.getId());
			} else if (containsRole(user.getRoles(), "UNION_PERSON")) {
				setUnionPersonData(userData, user.getId());
			} else if (containsRole(user.getRoles(), "INSPECTOR")) {
				setInspectorData(userData, user.getId());
			} else if (containsRole(user.getRoles(), "ICS_MANAGER")) {
				setICSManagerData(userData, user.getId());
			} else if (containsRole(user.getRoles(), "COOPERATIVE_PERSON")) {
				setCooperativePersonData(userData, user.getId());
			} else if (containsRole(user.getRoles(), "COLLECTION_CENTER_PERSON")) {
				setCollectionCenterPersonData(userData, user.getId());
			}

		} catch (Exception e) {
			logger.error("Error retrieving user data: " + e.getMessage(), e);
			return Collections.emptyMap();
		}

		return userData;
	}

	public List<UnionEntities> getMyUnionData(HttpServletRequest request) {
		CommonProfile profile = AuthUtil.getProfileFromRequest(request);
		JSONArray roles = (JSONArray) profile.getAttribute("roles");

		try {
			User user = userServiceApi.getUser(profile.getId());
			if (user == null) {
				logger.error("User not found for profile ID: " + profile.getId());
				return Collections.emptyList();
			}

			List<UnionPerson> unionPersonList;
			if (roles.contains("ROLE_ADMIN")) {
				unionPersonList = unionPersonServiceApi.findAll();
			} else if (roles.contains("UNION_PERSON")) {
				unionPersonList = unionPersonServiceApi.getByPropertyWithCondtion("userId",
						Long.parseLong(profile.getId()), "=", 0, 0, "unionCode desc");
			} else {
				return Collections.emptyList();
			}
			List<Long> unionCodeList = unionPersonList.stream().map(UnionPerson::getUnionCode)
					.collect(Collectors.toList());

			if (unionCodeList.isEmpty()) {
				return Collections.emptyList();
			}
			return unionEntitiesServiceApi.getByMultipleValuesWithCondition("code", unionCodeList, 0, 0);

		} catch (Exception e) {
			logger.error("Error retrieving union data: " + e.getMessage(), e);
			return Collections.emptyList();
		}
	}

	public List<CooperativeEntity> getMyCoopertiveData(HttpServletRequest request) {

		CommonProfile profile = AuthUtil.getProfileFromRequest(request);
		JSONArray roles = (JSONArray) profile.getAttribute("roles");

		try {
			User user = userServiceApi.getUser(profile.getId());
			if (user == null) {
				logger.error("User not found for profile ID: " + profile.getId());
				return Collections.emptyList();
			}

			List<CooperativePerson> cooperativePersonList;
			if (roles.contains("ROLE_ADMIN")) {
				cooperativePersonList = cooperativePersonServiceApi.findAll();
			} else if (roles.contains("COOPERATIVE_PERSON")) {
				cooperativePersonList = cooperativePersonServiceApi.getByPropertyWithCondtion("userId",
						Long.parseLong(profile.getId()), "=", 0, 0, "coCode desc");
			} else {
				return Collections.emptyList();
			}
			List<Long> cooperativeCodeList = cooperativePersonList.stream().map(CooperativePerson::getCoCode)
					.collect(Collectors.toList());

			if (cooperativeCodeList.isEmpty()) {
				return Collections.emptyList();
			}
			return cooperativeEntityService.getByMultipleValuesWithCondition("code", cooperativeCodeList, 0, 0);

		} catch (Exception e) {
			logger.error("Error retrieving cooperative data: " + e.getMessage(), e);
			return Collections.emptyList();
		}
	}

	public List<CollectionCenterEntity> getMyCollectionCenterData(HttpServletRequest request) {

		CommonProfile profile = AuthUtil.getProfileFromRequest(request);
		JSONArray roles = (JSONArray) profile.getAttribute("roles");

		try {
			User user = userServiceApi.getUser(profile.getId());
			if (user == null) {
				logger.error("User not found for profile ID: " + profile.getId());
				return Collections.emptyList();
			}

			List<CollectionCenterPerson> collectionCenterPersonList;
			if (roles.contains("ROLE_ADMIN")) {
				collectionCenterPersonList = collectionCenterPersonApi.findAll();
			} else if (roles.contains("COLLECTION_CENTER_PERSON")) {
				collectionCenterPersonList = collectionCenterPersonApi.getByPropertyWithCondtion("userId",
						Long.parseLong(profile.getId()), "=", 0, 0, "ccCode desc");
			} else {
				return Collections.emptyList();
			}
			List<Long> collectionCenterList = collectionCenterPersonList.stream().map(CollectionCenterPerson::getCcCode)
					.collect(Collectors.toList());

			if (collectionCenterList.isEmpty()) {
				return Collections.emptyList();
			}
			return collectionCenterEntityService.getByMultipleValuesWithCondition("code", collectionCenterList, 0, 0);

		} catch (Exception e) {
			logger.error("Error retrieving collectionCenter data: " + e.getMessage(), e);
			return Collections.emptyList();
		}
	}

	public List<CooperativeEntity> getCooperativesByUnion(HttpServletRequest request, String unionCodes) {
		CommonProfile profile = AuthUtil.getProfileFromRequest(request);
		JSONArray roles = (JSONArray) profile.getAttribute("roles");
		List<Long> unionCodeList = Arrays.stream(unionCodes.split(",")).map(Long::valueOf).collect(Collectors.toList());

		try {
			User user = userServiceApi.getUser(profile.getId());
			if (user == null) {
				logger.error("User not found for profile ID: {}", profile.getId());
				return Collections.emptyList();
			}

			if (roles.contains("COOPERATIVE_PERSON")) {
				return getMyCoopertiveData(request);
			}

			List<UnionEntities> unionPersonList = getMyUnionData(request);

			List<Long> userUnionCodeList = unionPersonList.stream().map(UnionEntities::getCode)
					.collect(Collectors.toList());

			List<Long> commonUnionCodes = userUnionCodeList.stream().filter(unionCodeList::contains)
					.collect(Collectors.toList());

			if (commonUnionCodes.isEmpty()) {
				return Collections.emptyList();
			}

			return cooperativeEntityService.getByMultipleValuesWithCondition("unionCode", commonUnionCodes, 0, 0);

		} catch (Exception e) {
			logger.error("Error retrieving cooperative data for profile ID {}: {}", profile.getId(), e.getMessage(), e);
			return Collections.emptyList();
		}
	}

	public List<CollectionCenterEntity> getCollectionCenterByCooperative(HttpServletRequest request,
			String cooperativeCodes) {
		CommonProfile profile = AuthUtil.getProfileFromRequest(request);
		JSONArray roles = (JSONArray) profile.getAttribute("roles");
		List<Long> cooperativeCodeList = Arrays.stream(cooperativeCodes.split(",")).map(Long::valueOf)
				.collect(Collectors.toList());

		try {
			User user = userServiceApi.getUser(profile.getId());
			if (user == null) {
				logger.error("User not found for profile ID: {}", profile.getId());
				return Collections.emptyList();
			}

			List<CooperativePerson> cooperativePersonList;

			if (roles.contains("ROLE_ADMIN")) {
				cooperativePersonList = cooperativePersonServiceApi.findAll();
			} else if (roles.contains("COOPERATIVE_PERSON")) {
				cooperativePersonList = cooperativePersonServiceApi.getByPropertyWithCondtion("userId",
						Long.parseLong(profile.getId()), "=", 0, 0, "unionCode desc");
			} else {
				return Collections.emptyList();
			}

			if (cooperativePersonList.isEmpty()) {
				return Collections.emptyList();
			}

			List<Long> userCooperativeCodeList = cooperativePersonList.stream().map(CooperativePerson::getCoCode)
					.collect(Collectors.toList());

			List<Long> commonCooperativeCodes = userCooperativeCodeList.stream().filter(cooperativeCodeList::contains)
					.collect(Collectors.toList());

			if (commonCooperativeCodes.isEmpty()) {
				return Collections.emptyList();
			}

			return collectionCenterEntityService.getByMultipleValuesWithCondition("cooperativeCode",
					commonCooperativeCodes, 0, 0);

		} catch (Exception e) {
			logger.error("Error retrieving collection center data for profile ID {}: {}", profile.getId(),
					e.getMessage(), e);
			return Collections.emptyList();
		}
	}

	public static boolean containsRole(java.util.Collection<Role> list, String roleName) {
		if (list == null) {
			return false;
		}
		for (Role role : list) {
			if (role.getAuthority() != null && role.getAuthority().equals(roleName)) {
				return true;
			}
		}
		return false;
	}

	private void setAdminPersonData(Map<String, Object> userData, Long userId) {
		List<UnionPerson> unionPersonList = unionPersonServiceApi.findAll();
		if (unionPersonList != null && !unionPersonList.isEmpty()) {
			userData.put(UNION_CODE, unionPersonList.get(0).getUnionCode());
		}
	}

	private void setUnionPersonData(Map<String, Object> userData, Long userId) {
		UnionPerson unionPerson = unionPersonServiceApi.findByUserId(userId);
		if (unionPerson != null) {
			userData.put(UNION_CODE, unionPerson.getUnionCode());
		}
	}

	private void setInspectorData(Map<String, Object> userData, Long userId) {
		Inspector inspector = inspectorSerciveApi.findByUserId(userId);
		if (inspector != null) {
			userData.put(UNION_CODE, inspector.getUnionCode());
		}
	}

	private void setICSManagerData(Map<String, Object> userData, Long userId) {
		ICSManager icsManager = icsManagerServiceApi.findByUserId(userId);
		if (icsManager != null) {
			userData.put(UNION_CODE, icsManager.getUnionCode());
		}
	}

	private void setCooperativePersonData(Map<String, Object> userData, Long userId) {
		CooperativePerson coPerson = cooperativePersonServiceApi.findByUserId(userId);
		if (coPerson != null) {
			Long coCode = coPerson.getCoCode();
			userData.put("coCode", coCode);
			CooperativeEntity cooperative = cooperativeEntityService.findByCode(coCode);
			if (cooperative != null) {
				userData.put(UNION_CODE, cooperative.getUnionCode());
			}
		}
	}

	private void setCollectionCenterPersonData(Map<String, Object> userData, Long userId) {
		CollectionCenterPerson ccPerson = collectionCenterPersonApi.findByUserId(userId);
		if (ccPerson != null) {
			Long ccCode = ccPerson.getCcCode();
			userData.put("ccCode", ccCode);
			CollectionCenterEntity collectionCenter = collectionCenterEntityService.findByCode(ccCode);
			if (collectionCenter != null) {
				Long coCode = collectionCenter.getCooperativeCode();
				userData.put("coCode", coCode);
				CooperativeEntity cooperative = cooperativeEntityService.findByCode(coCode);
				if (cooperative != null) {
					userData.put(UNION_CODE, cooperative.getUnionCode());
				}
			}
		}
	}

	public String addImage(InputStream inputStream, FormDataContentDisposition fileDetails,
			HttpServletRequest request) {
		String rawFileName = fileDetails.getFileName();
		String fileName = new File(rawFileName).getName();

		UUID uuid = UUID.randomUUID();
		String dirPath = ROOTPATH + File.separator + uuid.toString();
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdirs();
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
			inputStream.transferTo(out);
			return true;
		} catch (IOException e) {
			logger.error("Error writing uploaded file: {}", e.getMessage(), e);
		}
		return false;
	}

}
