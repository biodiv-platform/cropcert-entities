package cropcert.entities.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.xml.bind.ValidationException;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.pac4j.core.profile.CommonProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.strandls.authentication_utility.util.AuthUtil;
import com.strandls.user.controller.UserServiceApi;
import com.strandls.user.pojo.User;

import cropcert.entities.dao.FarmerDao;
import cropcert.entities.filter.Permissions;
import cropcert.entities.model.CollectionCenterEntity;
import cropcert.entities.model.CooperativeEntity;
import cropcert.entities.model.Farmer;
import cropcert.entities.model.UnionEntities;
import cropcert.entities.model.UserFarmerDetail;
import cropcert.entities.model.request.FarmerFileMetaData;
import net.minidev.json.JSONArray;

public class FarmerService extends AbstractService<Farmer> {
	private static final Logger logger = LoggerFactory.getLogger(FarmerService.class);

	@Inject
	ObjectMapper objectMapper;

	@Inject
	private CollectionCenterEntityService collectionCenterEntityService;

	@Inject
	private CooperativeEntityService cooperativeEntityService;

	@Inject
	private UnionEntityService unionEntityService;

	@Inject
	private UserServiceApi userServiceApi;

	private static Set<String> defaultPermissions;
	static {
		defaultPermissions = new HashSet<>();
		defaultPermissions.add(Permissions.FARMER);
	}

	@Inject
	public FarmerService(FarmerDao farmerDao) {
		super(farmerDao);
	}

	public Farmer save(String jsonString) throws IOException, ValidationException {
		Farmer farmer = objectMapper.readValue(jsonString, Farmer.class);

		String membershipId = farmer.getMembershipId();
		Long ccCode = farmer.getCcCode();
		if (ccCode == null)
			throw new ValidationException("Collection center code is compulsory");
		CollectionCenterEntity collectionCenter = collectionCenterEntityService.findByPropertyWithCondition("code",
				ccCode, "=");
		CooperativeEntity cooperative = cooperativeEntityService.findByPropertyWithCondition("code",
				collectionCenter.getCooperativeCode(), "=");

		if (membershipId == null) {
			membershipId = "";
			Long union = cooperative.getUnionCode();

			Long seqNumber = cooperative.getFarSeqNumber();
			Long numFarmer = cooperative.getNumFarmer();

			membershipId += union;
			membershipId += "-" + cooperative.getCode();
			membershipId += "-" + collectionCenter.getCode();
			membershipId += "-" + seqNumber;

			farmer.setFieldCoOrdinator(seqNumber);
			farmer.setMembershipId(membershipId);

			cooperative.setFarSeqNumber(seqNumber + 1);
			cooperative.setNumFarmer(numFarmer + 1);

			cooperativeEntityService.update(cooperative);
		}
		farmer.setCcName(collectionCenter.getName());
		farmer.setCoName(cooperative.getName());
		UnionEntities unionObject = unionEntityService.findById(cooperative.getUnionCode());
		farmer.setUnionName(unionObject.getName());

		return save(farmer);
	}

	public List<UserFarmerDetail> findByUserFarmer(Integer limit, Integer offset) {

		List<Farmer> farmers;
		if (limit == -1 || offset == -1)
			farmers = findAll();
		else
			farmers = findAll(limit, offset);
		return getUserFarmerList(farmers);

	}

	public List<UserFarmerDetail> findByUserId(Long userId) {
		Farmer farmer = findByPropertyWithCondition("userId", userId, "=");
		List<Farmer> farmerList = new ArrayList<>();
		farmerList.add(farmer);
		return getUserFarmerList(farmerList);

	}

	public List<UserFarmerDetail> findByFamerId(Long id) {
		Farmer farmer = ((FarmerDao) dao).findById(id);
		List<Farmer> farmerList = new ArrayList<>();
		farmerList.add(farmer);
		return getUserFarmerList(farmerList);

	}

	public List<UserFarmerDetail> getFarmerForMultipleCollectionCenter(String ccCodes, String firstName, Integer limit,
			Integer offset) {
		List<Long> ccCodesLong = new ArrayList<>();

		String[] ccCodesString = ccCodes.split(",");
		for (String ccCodeString : ccCodesString) {
			Long ccCode = Long.parseLong(ccCodeString);
			ccCodesLong.add(ccCode);
		}

		List<Farmer> farmerList = ((FarmerDao) dao).getFarmerForMultipleCollectionCenter(ccCodesLong, firstName, limit,
				offset);

		return getUserFarmerList(farmerList);
	}

	private List<UserFarmerDetail> getUserFarmerList(List<Farmer> farmerList) {
		List<UserFarmerDetail> result = new ArrayList<>();

		try {
			List<Long> userIds = farmerList.stream().map(Farmer::getUserId).collect(Collectors.toList());
			List<User> users = userServiceApi.getUserBulk(userIds);
			int index = 0;
			for (Farmer farmer : farmerList) {
				result.add(new UserFarmerDetail(users.get(index).getName(), users.get(index).getUserName(),
						users.get(index).getEmail(), farmer.getMembershipId(), farmer.getNumCoffeePlots(),
						farmer.getNumCoffeeTrees(), farmer.getFarmArea(), farmer.getCoffeeArea(),
						farmer.getFarmerCode(), farmer.getCcCode(), farmer.getCcName(), farmer.getCoName(),
						farmer.getUnionName(), farmer.getFieldCoOrdinator(), farmer.getUserId(),
						users.get(index).getSexType(), users.get(index).getMobileNumber()));

				index++;
			}

			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return result;
	}

	public List<UserFarmerDetail> getFarmerForCollectionCenter(Long ccCode, Integer limit, Integer offset) {
		List<Farmer> farmerList = getByPropertyWithCondtion("ccCode", ccCode, "=", limit, offset, "userId");

		return getUserFarmerList(farmerList);

	}

	public Map<String, Object> bulkFarmerSave(HttpServletRequest request, FormDataMultiPart multiPart)
			throws IOException {

		CommonProfile profile = AuthUtil.getProfileFromRequest(request);
		JSONArray roles = (JSONArray) profile.getAttribute("roles");

		if (roles.contains("ROLE_ADMIN")) {

			FormDataBodyPart formdata = multiPart.getField("metadata");
			if (formdata == null) {
				throw new WebApplicationException(
						Response.status(Response.Status.BAD_REQUEST).entity("Metadata file not present").build());
			}
			InputStream metaDataInputStream = formdata.getValueAs(InputStream.class);
			InputStreamReader inputStreamReader = new InputStreamReader(metaDataInputStream, StandardCharsets.UTF_8);

			FarmerFileMetaData fileMetaData = objectMapper.readValue(inputStreamReader, FarmerFileMetaData.class);
			fileMetaData.setCollectionCenterService(collectionCenterEntityService);
			fileMetaData.setCooperativeService(cooperativeEntityService);
			fileMetaData.setUnionService(unionEntityService);

			if (fileMetaData.getFileType().equalsIgnoreCase("csv")) {

				formdata = multiPart.getField("csv");
				InputStream csvDataInputStream = formdata.getValueAs(InputStream.class);
				inputStreamReader = new InputStreamReader(csvDataInputStream, StandardCharsets.UTF_8);
				CSVReader reader = new CSVReader(inputStreamReader);

				Map<String, Object> result = validateSheet(reader, fileMetaData);
				if (result.size() > 0)
					return result;

				csvDataInputStream = formdata.getValueAs(InputStream.class);
				inputStreamReader = new InputStreamReader(csvDataInputStream, StandardCharsets.UTF_8);
				reader = new CSVReader(inputStreamReader);
				Iterator<String[]> it = reader.iterator();

				@SuppressWarnings("unused")
				String[] headers = it.next();

				reader.close();
				return result;
			}
			throw new IOException("CSV File as input is compulsory");
		}
		return Collections.emptyMap();
	}

	private Map<String, Object> validateSheet(CSVReader reader, FarmerFileMetaData fileMetaData) {

		Map<String, Object> validationResult = new HashMap<>();

		Iterator<String[]> it = reader.iterator();

		String[] headers = it.next();
		if (!fileMetaData.validateIndices(headers)) {
			validationResult.put("Failed", "Compulsory column indices in the file are required for metadata");
			return validationResult;
		}

		return validationResult;
	}
}
