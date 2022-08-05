package cropcert.entities.model;

import javax.persistence.Entity;

import io.swagger.annotations.ApiModel;

@Entity
@ApiModel("UserFarmerDetail")

public class UserFarmerDetail extends Farmer {

	private String name;

	public UserFarmerDetail() {
		super();
	}

	public UserFarmerDetail(String membershipId, Integer numCoffeePlots, Integer numCoffeeTrees, Float farmArea,
			Float coffeeArea, String farmerCode, Long ccCode, String ccName, String coName, String unionName,
			Long fieldCoOrdinator, Long userId, String name) {

		super(membershipId, numCoffeePlots, numCoffeeTrees, farmArea, coffeeArea, farmerCode, ccCode, ccName, coName,
				unionName, fieldCoOrdinator, userId);

		this.name = name;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}