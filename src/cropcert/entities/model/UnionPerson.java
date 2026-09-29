package cropcert.entities.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "union_person")
@IdClass(EntitiesCompositeKey.class)
@Schema(description = "UnionPerson")
public class UnionPerson {

	private String membershipId;
	private Long unionCode;
	private String unionName;
	private Long userId;

	public UnionPerson(String membershipId, Long unionCode, String unionName, Long userId) {
		super();
		this.membershipId = membershipId;
		this.unionCode = unionCode;
		this.unionName = unionName;
		this.userId = userId;
	}

	public UnionPerson() {
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
	@Column(name = "union_code")
	public Long getUnionCode() {
		return unionCode;
	}

	public void setUnionCode(Long unionCode) {
		this.unionCode = unionCode;
	}

	@Column(name = "union_name")
	public String getUnionName() {
		return unionName;
	}

	public void setUnionName(String unionName) {
		this.unionName = unionName;
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
