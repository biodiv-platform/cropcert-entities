package cropcert.entities.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "factory_person")
@IdClass(EntitiesCompositeKey.class)
@Schema(description = "FactoryPerson")
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

	@Id
	@Column(name = "factory_code")
	public Long getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(Long factoryCode) {
		this.factoryCode = factoryCode;
	}

	@Id
	@Column(name = "user_id", nullable = false)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
