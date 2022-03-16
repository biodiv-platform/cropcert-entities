package cropcert.entities.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.pac4j.core.profile.CommonProfile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.sun.jersey.core.header.FormDataContentDisposition;

import cropcert.entities.api.CollectionCenterApi;
import cropcert.entities.api.CooperativeApi;
import cropcert.entities.dao.UserDao;
import cropcert.entities.filter.Permissions;
import cropcert.entities.model.CollectionCenter;
import cropcert.entities.model.CollectionCenterPerson;
import cropcert.entities.model.Cooperative;
import cropcert.entities.model.CooperativePerson;
import cropcert.entities.model.ICSManager;
import cropcert.entities.model.Inspector;
import cropcert.entities.model.UnionPerson;
import cropcert.entities.model.User;
import cropcert.entities.util.AuthUtility;
import cropcert.entities.util.MessageDigestPasswordEncoder;

public class UserService extends AbstractService<User> {

	public final static String rootPath = System.getProperty("user.home") + File.separatorChar + "cropcert-image";

	@Inject
	private ObjectMapper objectMappper;

	@Inject
	private MessageDigestPasswordEncoder passwordEncoder;

	@Inject
	private CooperativeApi cooperativeApi;

	@Inject
	private CollectionCenterApi collectionCenterApi;

	private static Set<String> defaultPermissions;
	static {
		defaultPermissions = new HashSet<String>();
		defaultPermissions.add(Permissions.DEFAULT);
	}

	@Inject
	public UserService(UserDao userDao) {
		super(userDao);
	}

	public User save(String jsonString) throws JsonParseException, JsonMappingException, IOException, JSONException {
		User user = objectMappper.readValue(jsonString, User.class);
		JSONObject jsonObject = new JSONObject(jsonString);
		String password = jsonObject.getString("password");
		password = passwordEncoder.encodePassword(password, null);
		user.setPassword(password);
		user.setPermissions(defaultPermissions);
		return save(user);
	}

	public User updatePassword(HttpServletRequest request, String password) {
		
		CommonProfile profile = AuthUtility.getCurrentUser(request);
		User user = findById(Long.parseLong(profile.getId()));
		
		password = passwordEncoder.encodePassword(password, null);
		user.setPassword(password);
		return update(user);
	}
	
	public User getByEmail(String email) {
		return findByPropertyWithCondtion("email", email, "=");
	}

	public User getByUserName(String userName) {
		return findByPropertyWithCondtion("userName", userName, "=");
	}

	public User findByPropertyWithCondtion(String property, String value, String condition) {
		return dao.findByPropertyWithCondition(property, value, condition);
	}

	public Map<String, Object> getMyData(HttpServletRequest request) {
		//CommonProfile profile = AuthUtility.getCurrentUser(request);
		//User user = findById(Long.parseLong(profile.getId()));

		User user = findById(Long.parseLong("1"));

		Map<String, Object> myData = new HashMap<String, Object>();
		myData.put("user", user);

		// Insert data specific to user
		myData.put("ccCode", -1);
		myData.put("coCode", -1);
		myData.put("unionCode", -1);

		if (user instanceof UnionPerson) {
			myData.put("unionCode", ((UnionPerson) user).getUnionCode());
		} else if (user instanceof Inspector) {
			myData.put("unionCode", ((Inspector) user).getUnionCode());
		} else if (user instanceof ICSManager) {
			myData.put("unionCode", ((ICSManager) user).getUnionCode());
		} else if (user instanceof CooperativePerson) {
			CooperativePerson coPerson = (CooperativePerson) user;

			int coCode = coPerson.getCoCode();
			myData.put("coCode", coPerson.getCoCode());

			Response coResponse = cooperativeApi.findByCode(request, (long) coCode);
			if (coResponse.getEntity() != null) {
				Cooperative cooperative = (Cooperative) coResponse.getEntity();
				myData.put("unionCode", cooperative.getUnionCode());
			}
		} else if (user instanceof CollectionCenterPerson) {
			CollectionCenterPerson ccPerson = (CollectionCenterPerson) user;

			int ccCode = ccPerson.getCcCode();
			myData.put("ccCode", ccCode);

			Response ccResponse = collectionCenterApi.findByCode(request, (long) ccCode);
			if (ccResponse.getEntity() != null) {
				CollectionCenter collectionCenter = (CollectionCenter) ccResponse.getEntity();
				Long coCode = collectionCenter.getCoCode();
				myData.put("coCode", coCode);

				Response coResponse = cooperativeApi.findByCode(request, (long) coCode);
				if (coResponse.getEntity() != null) {
					Cooperative cooperative = (Cooperative) coResponse.getEntity();
					myData.put("unionCode", cooperative.getUnionCode());
				}
			}
		}
		return myData;
	}

	public User uploadSignature(HttpServletRequest request, InputStream inputStream,
			FormDataContentDisposition fileDetails) throws IOException {
		CommonProfile profile = AuthUtility.getCommonProfile(request);
		Long id = Long.parseLong(profile.getId());
		User user = findById(id);
		String sign = addImage(inputStream, fileDetails, request);
		user.setSign(sign);
		update(user);
		return user;
	}
	
	public String addImage(InputStream inputStream, FormDataContentDisposition fileDetails,
			HttpServletRequest request) {
		String fileName = fileDetails.getFileName();

		UUID uuid = UUID.randomUUID();
		String dirPath = rootPath + File.separator + uuid.toString();
		File dir = new File(dirPath);
		if(!dir.exists()) {
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
