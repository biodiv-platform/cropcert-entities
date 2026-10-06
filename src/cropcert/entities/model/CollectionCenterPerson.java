package cropcert.entities.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "collection_center_person")
@IdClass(CollectionCenterUserCompositeKey.class)
@Schema(description = "CollectionCenterPerson")
public class CollectionCenterPerson {

	/**
	 * 
	 */

	private String membershipId;
	private Long ccCode;
	private Long userId;

	public CollectionCenterPerson() {
		super();
	}

	public CollectionCenterPerson(String membershipId, Long ccCode, Long userId) {
		super();
		this.membershipId = membershipId;
		this.ccCode = ccCode;
		this.userId = userId;
	}

	@Id
	@Column(name = "membership_id", nullable = false)
	public String getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}

	@Id
	@Column(name = "cc_code")
	public Long getCcCode() {
		return ccCode;
	}

	public void setCcCode(Long ccCode) {
		this.ccCode = ccCode;
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
