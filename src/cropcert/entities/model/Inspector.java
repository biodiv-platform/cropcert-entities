package cropcert.entities.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;

@Entity
@Table(name = "inspector")
@IdClass(EntitiesCompositeKey.class)
@ApiModel("Inspector")
public class Inspector {

	/**
	 * 
	 */

	private String membershipId;
	private Long unionCode;
	private Long userId;

	public Inspector() {
		super();
	}

	public Inspector(String membershipId, Long unionCode, Long userId) {
		super();
		this.membershipId = membershipId;
		this.unionCode = unionCode;
		this.userId = userId;
	}

	@Column(name = "membership_id", nullable = false)
	public String getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}

	@Id
	@Column(name = "union_code")
	public Long getUnionCode() {
		return unionCode;
	}

	public void setUnionCode(Long unionCode) {
		this.unionCode = unionCode;
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
