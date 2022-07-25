package cropcert.entities.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;

@Entity
@Table(name = "cooperative_person")
@IdClass(EntitiesCompositeKey.class)
@ApiModel("CooperativePerson")
public class CooperativePerson {

	private String membershipId;
	private Long coCode;
	private Long userId;

	public CooperativePerson(String membershipId, Long coCode, Long userId) {
		super();
		this.membershipId = membershipId;
		this.coCode = coCode;
		this.userId = userId;
	}

	public CooperativePerson() {
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
	@Column(name = "co_code")
	public Long getCoCode() {
		return coCode;
	}

	public void setCoCode(Long factoryCode) {
		this.coCode = factoryCode;
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
