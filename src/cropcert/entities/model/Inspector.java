package cropcert.entities.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "inspector")
@IdClass(EntitiesCompositeKey.class)
@Schema(description = "Inspector")
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
