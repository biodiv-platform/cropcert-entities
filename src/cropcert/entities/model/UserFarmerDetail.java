package cropcert.entities.model;

import javax.persistence.Entity;

import io.swagger.annotations.ApiModel;

@Entity
@ApiModel("UserFarmerDetail")

public class UserFarmerDetail {

	private String name;
	private String membershipId;
	private Integer numCoffeePlots;
	private Integer numCoffeeTrees;
	private Float farmArea;
	private Float coffeeArea;
	private String farmerCode;
	private Long ccCode;
	private String ccName;
	private String coName;
	private String unionName;
	private Long fieldCoOrdinator;
	private Long userId;

	public UserFarmerDetail() {
		super();
	}

	public UserFarmerDetail(String name, String membershipId, Integer numCoffeePlots, Integer numCoffeeTrees,
			Float farmArea, Float coffeeArea, String farmerCode, Long ccCode, String ccName, String coName,
			String unionName, Long fieldCoOrdinator, Long userId) {
		super();
		this.name = name;
		this.membershipId = membershipId;
		this.numCoffeePlots = numCoffeePlots;
		this.numCoffeeTrees = numCoffeeTrees;
		this.farmArea = farmArea;
		this.coffeeArea = coffeeArea;
		this.farmerCode = farmerCode;
		this.ccCode = ccCode;
		this.ccName = ccName;
		this.coName = coName;
		this.unionName = unionName;
		this.fieldCoOrdinator = fieldCoOrdinator;
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}

	public Integer getNumCoffeePlots() {
		return numCoffeePlots;
	}

	public void setNumCoffeePlots(Integer numCoffeePlots) {
		this.numCoffeePlots = numCoffeePlots;
	}

	public Integer getNumCoffeeTrees() {
		return numCoffeeTrees;
	}

	public void setNumCoffeeTrees(Integer numCoffeeTrees) {
		this.numCoffeeTrees = numCoffeeTrees;
	}

	public Float getFarmArea() {
		return farmArea;
	}

	public void setFarmArea(Float farmArea) {
		this.farmArea = farmArea;
	}

	public Float getCoffeeArea() {
		return coffeeArea;
	}

	public void setCoffeeArea(Float coffeeArea) {
		this.coffeeArea = coffeeArea;
	}

	public String getFarmerCode() {
		return farmerCode;
	}

	public void setFarmerCode(String farmerCode) {
		this.farmerCode = farmerCode;
	}

	public Long getCcCode() {
		return ccCode;
	}

	public void setCcCode(Long ccCode) {
		this.ccCode = ccCode;
	}

	public String getCcName() {
		return ccName;
	}

	public void setCcName(String ccName) {
		this.ccName = ccName;
	}

	public String getCoName() {
		return coName;
	}

	public void setCoName(String coName) {
		this.coName = coName;
	}

	public String getUnionName() {
		return unionName;
	}

	public void setUnionName(String unionName) {
		this.unionName = unionName;
	}

	public Long getFieldCoOrdinator() {
		return fieldCoOrdinator;
	}

	public void setFieldCoOrdinator(Long fieldCoOrdinator) {
		this.fieldCoOrdinator = fieldCoOrdinator;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}