package cropcert.entities.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;

@Entity
@Table(name = "factory_person")
@ApiModel("FactoryPerson")
public class FactoryPerson {

	private String membershipId;
	private Long factoryCode;
	private Long userId;

	public FactoryPerson(String membershipId, Long factoryCode, Long userId) {
		super();
		this.membershipId = membershipId;
		this.factoryCode = factoryCode;
		this.userId = userId;
	}

	public FactoryPerson() {
		super();
	}

	@Column(name = "membership_id", nullable = false)
	public String getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}

	@Column(name = "factory_code")
	public Long getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(Long factoryCode) {
		this.factoryCode = factoryCode;
	}

	@Column(name = "user_id", nullable = false)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	

}
