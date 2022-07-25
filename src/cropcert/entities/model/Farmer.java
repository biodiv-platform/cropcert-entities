package cropcert.entities.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;

@Entity
@Table(name = "farmer")
@IdClass(EntitiesCompositeKey.class)
@ApiModel("Farmer")
public class Farmer {

	/**
	 * 
	 */

	@Id
	@Column(name = "membership_id", nullable = false)
	private String membershipId;
	@Column(name = "num_coffee_plots")
	private Integer numCoffeePlots;
	@Column(name = "num_coffee_Trees")
	private Integer numCoffeeTrees;
	@Column(name = "farm_area")
	private Float farmArea;
	@Column(name = "coffee_area")
	private Float coffeeArea;
	@Column(name = "farmer_code")
	private String farmerCode;
	@Column(name = "cc_code", nullable = false)
	private Long ccCode;
	@Column(name = "cc_name")
	private String ccName;
	@Column(name = "co_name")
	private String coName;
	@Column(name = "union_name")
	private String unionName;
	@Column(name = "field_coordinator")
	private Long fieldCoOrdinator;
	@Id
	@Column(name = "user_id", nullable = false)
	private Long userId;

	public Farmer() {
		super();
	}

	public Farmer(String membershipId, Integer numCoffeePlots, Integer numCoffeeTrees, Float farmArea, Float coffeeArea,
			String farmerCode, Long ccCode, String ccName, String coName, String unionName, Long fieldCoOrdinator,
			Long userId) {
		super();
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
