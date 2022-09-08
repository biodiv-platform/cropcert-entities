package cropcert.entities.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;


import io.swagger.annotations.ApiModel;

@Entity
@Table(name = "collection_center_person")
@IdClass(EntitiesCompositeKey.class)
@ApiModel("CollectionCenterPerson")
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
