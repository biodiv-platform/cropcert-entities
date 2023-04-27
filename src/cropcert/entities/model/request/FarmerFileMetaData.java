package cropcert.entities.model.request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cropcert.entities.model.CollectionCenter;
import cropcert.entities.model.Cooperative;
import cropcert.entities.model.Union;
import cropcert.entities.service.CollectionCenterEntityService;
import cropcert.entities.service.CooperativeEntityService;
import cropcert.entities.service.UnionEntityService;
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
	private CollectionCenterEntityService collectionCenterEntityService;

	@JsonIgnore
	private CooperativeEntityService cooperativeEntityService;

	@JsonIgnore
	private UnionEntityService unionEntityService;

	@JsonIgnore
	private Map<String, List<CollectionCenter>> collectionCenterMap = new HashMap<>();

	@JsonIgnore
	private Map<Long, Cooperative> coCodeToCooperativeMap = new HashMap<>();

	@JsonIgnore
	private Map<Long, Union> unionCodeToUnionCenterMap = new HashMap<>();

	@Inject
	public FarmerFileMetaData() {
		super();
	}

	public void setCollectionCenterService(CollectionCenterEntityService collectionCenterEntityService) {
		this.collectionCenterEntityService = collectionCenterEntityService;
	}

	public void setCooperativeService(CooperativeEntityService cooperativeEntityService) {
		this.cooperativeEntityService = cooperativeEntityService;
	}

	public void setUnionService(UnionEntityService unionEntityService) {
		this.unionEntityService = unionEntityService;
	}

	public boolean validateIndices(String[] headers) {
		if (!fileType.equals("csv") || (ccNameColumnName == null && ccCodeColumnName == null)
				|| (farmerCodeColumnName == null && farmerIdColumnName == null) || nameColumnName == null
				|| genderColumnName == null) {
			return false;
		}

		Map<String, Integer> expectedHeaders = new HashMap<>();
		expectedHeaders.put(ccNameColumnName, ccNameColumnIndex);
		expectedHeaders.put(ccCodeColumnName, ccCodeColumnIndex);
		expectedHeaders.put(farmerCodeColumnName, farmerCodeColumnIndex);
		expectedHeaders.put(farmerIdColumnName, farmerIdColumnIndex);
		expectedHeaders.put(nameColumnName, nameColumnIndex);
		expectedHeaders.put(genderColumnName, genderColumnIndex);
		expectedHeaders.put(birthDateColumnName, birthDateColumnIndex);
		expectedHeaders.put(numCoffeePlotsColumnName, numCoffeePlotsColumnIndex);
		expectedHeaders.put(numCoffeeTreesColumnName, numCoffeeTreesColumnIndex);
		expectedHeaders.put(farmAreaColumnName, farmAreaColumnIndex);
		expectedHeaders.put(coffeeAreaColumnName, coffeeAreaColumnIndex);
		expectedHeaders.put(cellNumberColumnName, cellNumberColumnIndex);
		expectedHeaders.put(emailColumnName, emailColumnIndex);
		expectedHeaders.put(villageColumnName, villageColumnIndex);
		expectedHeaders.put(subCountryColumnName, subCountryColumnIndex);

		// Check if all expected headers are present and assign their indices
		return Arrays.stream(headers).map(String::trim).map(String::toLowerCase).map(expectedHeaders::get)
				.filter(Objects::nonNull).reduce((a, b) -> a).orElse(-1) >= 0;
	}

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
