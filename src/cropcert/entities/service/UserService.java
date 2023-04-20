package cropcert.entities.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
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
import com.strandls.user.ApiException;
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
import cropcert.entities.util.AppUtil;
import cropcert.entities.util.AppUtil.MODULE;

public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	public final static String rootPath = System.getProperty("user.home") + File.separatorChar + "cropcert-image";

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

		Map<String, Object> myData = new HashMap<>();

		try {
			User user = userServiceApi.getUser(profile.getId());

			if (user == null)
				return null;
			myData.put("user", user);
			// Insert data specific to user
			myData.put("ccCode", -1);
			myData.put("coCode", -1);
			myData.put("unionCode", -1);

			for (Role role : user.getRoles()) {

				MODULE roleType = AppUtil.getModule(role.getAuthority());

				if (roleType != null && roleType.name().contains("UNION_PERSON")) {

					UnionPerson unionPerson = unionPersonServiceApi.findByUserId(user.getId());

					myData.put("unionCode", unionPerson.getUnionCode());
				} else if (roleType != null && roleType.name().contains("INSPECTOR")) {

					Inspector inspector = inspectorSerciveApi.findByUserId(user.getId());

					myData.put("unionCode", inspector.getUnionCode());
				} else if (roleType != null && roleType.name().contains("ICS_MANAGER")) {

					ICSManager icsManager = icsManagerServiceApi.findByUserId(user.getId());

					myData.put("unionCode", icsManager.getUnionCode());
				} else if (roleType != null && roleType.name().contains("COOPERATIVE_PERSON")) {

					CooperativePerson coPerson = cooperativePersonServiceApi.findByUserId(user.getId());

					Long coCode = coPerson.getCoCode();
					myData.put("coCode", coPerson.getCoCode());

					Response coResponse = cooperativeEntitiesApi.findByCode(request, (long) coCode);
					if (coResponse.getEntity() != null) {
						Cooperative cooperative = (Cooperative) coResponse.getEntity();
						myData.put("unionCode", cooperative.getUnionCode());
					}
				} else if (roleType != null && roleType.name().contains("COLLECTION_CENTER_PERSON")) {
					CollectionCenterPerson ccPerson = collectionCenterPersonApi.findByUserId(user.getId());

					Long ccCode = ccPerson.getCcCode();
					myData.put("ccCode", ccCode);

					Response ccResponse = collectionCenterEntitiesApi.findByCode(request, (long) ccCode);
					if (ccResponse.getEntity() != null) {
						CollectionCenter collectionCenter = (CollectionCenter) ccResponse.getEntity();
						Long coCode = collectionCenter.getCoCode();
						myData.put("coCode", coCode);

						Response coResponse = cooperativeEntitiesApi.findByCode(request, (long) coCode);
						if (coResponse.getEntity() != null) {
							Cooperative cooperative = (Cooperative) coResponse.getEntity();
							myData.put("unionCode", cooperative.getUnionCode());
						}
					}
				}
			}
		} catch (ApiException e) {
			logger.error(e.getMessage());
		}

		return myData;
	}

//	public User uploadSignature(HttpServletRequest request, InputStream inputStream,
//			FormDataContentDisposition fileDetails) throws IOException {
//		CommonProfile profile = AuthUtility.getCommonProfile(request);
//		Long id = Long.parseLong(profile.getId());
//		User user = findById(id);
//		String sign = addImage(inputStream, fileDetails, request);
//		user.setSign(sign);
//		update(user);
//		return user;
//	}

	public String addImage(InputStream inputStream, FormDataContentDisposition fileDetails,
			HttpServletRequest request) {
		String fileName = fileDetails.getFileName();

		UUID uuid = UUID.randomUUID();
		String dirPath = rootPath + File.separator + uuid.toString();
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
		try {
			OutputStream out = new FileOutputStream(new File(fileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(fileLocation));
			while ((read = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();

			return true;
		} catch (IOException e) {
			e.printStackTrace();

		}
		return false;
	}

}
