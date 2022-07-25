package cropcert.entities.model.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cropcert.entities.model.CollectionCenter;
import cropcert.entities.model.Cooperative;
import cropcert.entities.model.Union;
import cropcert.entities.service.CollectionCenterService;
import cropcert.entities.service.CooperativeService;
import cropcert.entities.service.UnionService;
import io.swagger.annotations.ApiModel;

@ApiModel("FarmerFileMetadata")
public class FarmerFileMetaData {

	private String fileType;

	private String ccNameColumnName;
	private String ccCodeColumnName;
	private String farmerCodeColumnName;
	private String farmerIdColumnName;
	private String nameColumnName;
	private String genderColumnName;
	private String birthDateColumnName;

	private String numCoffeePlotsColumnName;
	private String numCoffeeTreesColumnName;
	private String farmAreaColumnName;
	private String coffeeAreaColumnName;

	private String cellNumberColumnName;
	private String emailColumnName;
	private String villageColumnName;
	private String subCountryColumnName;

	@JsonIgnore
	private Integer ccNameColumnIndex;
	@JsonIgnore
	private Integer ccCodeColumnIndex;
	@JsonIgnore
	private Integer farmerIdColumnIndex;
	@JsonIgnore
	private Integer farmerCodeColumnIndex;
	@JsonIgnore
	private Integer nameColumnIndex;
	@JsonIgnore
	private Integer genderColumnIndex;
	@JsonIgnore
	private Integer birthDateColumnIndex;

	@JsonIgnore
	private Integer numCoffeePlotsColumnIndex;
	@JsonIgnore
	private Integer numCoffeeTreesColumnIndex;
	@JsonIgnore
	private Integer farmAreaColumnIndex;
	@JsonIgnore
	private Integer coffeeAreaColumnIndex;

	@JsonIgnore
	private Integer cellNumberColumnIndex;
	@JsonIgnore
	private Integer emailColumnIndex;
	@JsonIgnore
	private Integer villageColumnIndex;
	@JsonIgnore
	private Integer subCountryColumnIndex;

	@JsonIgnore
	private CollectionCenterService collectionCenterService;

	@JsonIgnore
	private CooperativeService cooperativeService;

	@JsonIgnore
	private UnionService unionService;

	@JsonIgnore
	private Map<String, List<CollectionCenter>> collectionCenterMap = new HashMap<>();

	@JsonIgnore
	private Map<Long, Cooperative> coCodeToCooperativeMap = new HashMap<Long, Cooperative>();

	@JsonIgnore
	private Map<Long, Union> unionCodeToUnionCenterMap = new HashMap<Long, Union>();

	@Inject
	public FarmerFileMetaData() {
		super();
	}

	public void setCollectionCenterService(CollectionCenterService collectionCenterService) {
		this.collectionCenterService = collectionCenterService;
	}

	public void setCooperativeService(CooperativeService cooperativeService) {
		this.cooperativeService = cooperativeService;
	}

	public void setUnionService(UnionService unionService) {
		this.unionService = unionService;
	}

	public boolean validateIndices(String[] headers) {

		if (!fileType.equals("csv") || (ccNameColumnName == null && ccCodeColumnName == null)
				|| (farmerCodeColumnName == null && farmerIdColumnName == null) || nameColumnName == null
				|| genderColumnName == null)
			return false;

		for (int i = 0; i < headers.length; i++) {
			String header = headers[i].trim();

			if (header.equalsIgnoreCase(ccNameColumnName))
				ccNameColumnIndex = i;
			else if (header.equalsIgnoreCase(ccCodeColumnName))
				ccCodeColumnIndex = i;
			else if (header.equalsIgnoreCase(farmerCodeColumnName))
				farmerCodeColumnIndex = i;
			else if (header.equalsIgnoreCase(farmerIdColumnName))
				farmerIdColumnIndex = i;
			else if (header.equalsIgnoreCase(nameColumnName))
				nameColumnIndex = i;
			else if (header.equalsIgnoreCase(genderColumnName))
				genderColumnIndex = i;
			else if (header.equalsIgnoreCase(birthDateColumnName))
				birthDateColumnIndex = i;
			else if (header.equalsIgnoreCase(numCoffeePlotsColumnName))
				numCoffeePlotsColumnIndex = i;
			else if (header.equalsIgnoreCase(numCoffeeTreesColumnName))
				numCoffeeTreesColumnIndex = i;
			else if (header.equalsIgnoreCase(farmAreaColumnName))
				farmAreaColumnIndex = i;
			else if (header.equalsIgnoreCase(coffeeAreaColumnName))
				coffeeAreaColumnIndex = i;
			else if (header.equalsIgnoreCase(cellNumberColumnName))
				cellNumberColumnIndex = i;
			else if (header.equalsIgnoreCase(emailColumnName))
				emailColumnIndex = i;
			else if (header.equalsIgnoreCase(villageColumnName))
				villageColumnIndex = i;
			else if (header.equalsIgnoreCase(subCountryColumnName))
				subCountryColumnIndex = i;
			else {
				return false;
			}
		}
		return true;
	}

//	public Farmer readOneRow(String[] data, boolean validation) throws IOException {
//		if (data[nameColumnIndex] == null && "".equals(data[nameColumnIndex].trim())) {
//			throw new IOException("Name missing");
//		}
//		if ((data[ccNameColumnIndex] == null && "".equals(data[ccNameColumnIndex].trim()))
//				|| (data[ccCodeColumnIndex] == null && "".equals(data[ccCodeColumnIndex].trim()))) {
//			throw new IOException("CC Name and/or code missing");
//		}
//		if ((data[farmerIdColumnIndex] == null && "".equals(data[farmerIdColumnIndex].trim()))
//				|| (data[farmerCodeColumnIndex] == null && "".equals(data[farmerCodeColumnIndex].trim()))) {
//			throw new IOException("Farmer id missing");
//		}
//		if (data[genderColumnIndex] == null && "".equals(data[genderColumnIndex].trim())) {
//			throw new IOException("Gender missing");
//		}
//
//		CollectionCenter cc;
//		try {
//			cc = collectionCenterService.findByName(data[ccNameColumnIndex], data[ccCodeColumnIndex]);
//		} catch (Exception e) {
//			throw new IOException("Invalid collection center : " + data[ccNameColumnIndex]);
//		}
//		if (cc == null)
//			throw new IOException("Invalid collection center : " + data[ccNameColumnIndex]);
//		/*
//		 * try { if (collectionCenterMap.containsKey(ccName)) { cc =
//		 * collectionCenterMap.get(ccName); } else { cc =
//		 * collectionCenterService.findByName(data[ccNameColumnIndex]);//
//		 * findByPropertyWithCondition("name", data[ccNameColumnIndex].trim(), "=");
//		 * collectionCenterMap.put(ccName, cc); } } catch (Exception e) { throw new
//		 * IOException("Invalid collection center : " + data[ccNameColumnIndex]); }
//		 */
//
//		if (validation) {
//			return null;
//		}
//
//		Farmer farmer = new Farmer();
//		farmer.setPassword(RandomStringUtils.randomAlphanumeric(8));
//
//		String[] names = StringUtils.split(data[nameColumnIndex]);
//		if (names.length > 0)
//			farmer.setFirstName(names[0]);
//		else
//			farmer.setFirstName("");
//		if (names.length > 1)
//			farmer.setLastName(names[1]);
//		else
//			farmer.setLastName("");
//
//		if (birthDateColumnIndex != null && data[birthDateColumnIndex] != null) {
//			Timestamp birthDate = null;
//			try {
//				birthDate = new Timestamp(Date.valueOf(data[9]).getTime());
//			} catch (Exception e) {
//			} finally {
//				farmer.setDateOfBirth(birthDate);
//			}
//		}
//		farmer.setGender(data[genderColumnIndex]);
//
//		if (cellNumberColumnIndex != null && !"".equals(data[cellNumberColumnIndex]))
//			farmer.setCellNumber(data[cellNumberColumnIndex]);
//
//		if (emailColumnIndex != null && !"".equals(data[emailColumnIndex]))
//			farmer.setEmail(data[emailColumnIndex]);
//
//		if (villageColumnIndex != null && !"".equals(data[villageColumnIndex]))
//			farmer.setVillage(data[villageColumnIndex]);
//
//		if (subCountryColumnIndex != null && !"".equals(data[subCountryColumnIndex]))
//			farmer.setSubCountry(data[subCountryColumnIndex]);
//
//		// Need to modify the number of plots to Float in futre if possible
//		if (numCoffeePlotsColumnIndex != null && !"".equals(data[numCoffeePlotsColumnIndex].trim()))
//			farmer.setNumCoffeePlots((int) Float.parseFloat(data[numCoffeePlotsColumnIndex].trim()));
//		if (numCoffeeTreesColumnIndex != null && !"".equals(data[numCoffeeTreesColumnIndex].trim()))
//			farmer.setNumCoffeeTrees((int) Float.parseFloat(data[numCoffeeTreesColumnIndex].trim()));
//		if (farmAreaColumnIndex != null && !"".equals(data[farmAreaColumnIndex].trim()))
//			farmer.setFarmArea(Float.parseFloat(data[farmAreaColumnIndex].trim()));
//		if (coffeeAreaColumnIndex != null && !"".equals(data[coffeeAreaColumnIndex].trim()))
//			farmer.setCoffeeArea(Float.parseFloat(data[coffeeAreaColumnIndex].trim()));
//
//		String farmerCode = data[farmerIdColumnIndex] + "_" + data[farmerCodeColumnIndex];
//		farmer.setFarmerCode(farmerCode);
//		farmer.setCcCode(cc.getCode());
//		farmer.setCcName(cc.getName());
//
//		Cooperative cooperative;
//		if (coCodeToCooperativeMap.containsKey(cc.getCoCode())) {
//			cooperative = coCodeToCooperativeMap.get(cc.getCoCode());
//		} else {
//			cooperative = cooperativeService.findByPropertyWithCondition("code", cc.getCoCode(), "=");
//			coCodeToCooperativeMap.put(cc.getCoCode(), cooperative);
//		}
//		farmer.setCoName(cooperative.getName());
//
//		Union union;
//		if (unionCodeToUnionCenterMap.containsKey(cooperative.getUnionCode())) {
//			union = unionCodeToUnionCenterMap.get(cooperative.getUnionCode());
//		} else {
//			union = unionService.findByPropertyWithCondition("code", cooperative.getUnionCode(), "=");
//			unionCodeToUnionCenterMap.put(cooperative.getUnionCode(), union);
//		}
//		farmer.setUnionName(union.getName());
//
//		farmer.setFieldCoOrdinator(1L);
//
//		String userName = farmer.getFirstName().toLowerCase() + "_" + farmer.getLastName().toLowerCase() + "_"
//				+ cooperative.getName().toLowerCase() + "_" + farmer.getFarmerCode() + "_" + farmer.getFarmerCode() + "_" + farmer.getCcCode()
//				+ "@cropcert.org";
//		String membershipId = userName;
//		farmer.setMembershipId(membershipId);
//		farmer.setUserName(userName);
//		return farmer;
//	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getCcNameColumnName() {
		return ccNameColumnName;
	}

	public void setCcNameColumnName(String ccNameColumnName) {
		this.ccNameColumnName = ccNameColumnName;
	}

	public String getCcCodeColumnName() {
		return ccCodeColumnName;
	}

	public void setCcCodeColumnName(String ccCodeColumnName) {
		this.ccCodeColumnName = ccCodeColumnName;
	}

	public String getFarmerCodeColumnName() {
		return farmerCodeColumnName;
	}

	public void setFarmerCodeColumnName(String farmerCodeColumnName) {
		this.farmerCodeColumnName = farmerCodeColumnName;
	}

	public String getFarmerIdColumnName() {
		return farmerIdColumnName;
	}

	public void setFarmerIdColumnName(String farmerIdColumnName) {
		this.farmerIdColumnName = farmerIdColumnName;
	}

	public String getNameColumnName() {
		return nameColumnName;
	}

	public void setNameColumnName(String nameColumnName) {
		this.nameColumnName = nameColumnName;
	}

	public String getGenderColumnName() {
		return genderColumnName;
	}

	public void setGenderColumnName(String genderColumnName) {
		this.genderColumnName = genderColumnName;
	}

	public String getBirthDateColumnName() {
		return birthDateColumnName;
	}

	public void setBirthDateColumnName(String birthDateColumnName) {
		this.birthDateColumnName = birthDateColumnName;
	}

	public String getNumCoffeePlotsColumnName() {
		return numCoffeePlotsColumnName;
	}

	public void setNumCoffeePlotsColumnName(String numCoffeePlotsColumnName) {
		this.numCoffeePlotsColumnName = numCoffeePlotsColumnName;
	}

	public String getNumCoffeeTreesColumnName() {
		return numCoffeeTreesColumnName;
	}

	public void setNumCoffeeTreesColumnName(String numCoffeeTreesColumnName) {
		this.numCoffeeTreesColumnName = numCoffeeTreesColumnName;
	}

	public String getFarmAreaColumnName() {
		return farmAreaColumnName;
	}

	public void setFarmAreaColumnName(String farmAreaColumnName) {
		this.farmAreaColumnName = farmAreaColumnName;
	}

	public String getCoffeeAreaColumnName() {
		return coffeeAreaColumnName;
	}

	public void setCoffeeAreaColumnName(String coffeeAreaColumnName) {
		this.coffeeAreaColumnName = coffeeAreaColumnName;
	}

	public String getCellNumberColumnName() {
		return cellNumberColumnName;
	}

	public void setCellNumberColumnName(String cellNumberColumnName) {
		this.cellNumberColumnName = cellNumberColumnName;
	}

	public String getEmailColumnName() {
		return emailColumnName;
	}

	public void setEmailColumnName(String emailColumnName) {
		this.emailColumnName = emailColumnName;
	}

	public String getVillageColumnName() {
		return villageColumnName;
	}

	public void setVillageColumnName(String villageColumnName) {
		this.villageColumnName = villageColumnName;
	}

	public String getSubCountryColumnName() {
		return subCountryColumnName;
	}

	public void setSubCountryColumnName(String subCountryColumnName) {
		this.subCountryColumnName = subCountryColumnName;
	}
}
