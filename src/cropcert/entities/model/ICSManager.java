package cropcert.entities.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;

@Entity
@Table(name = "ics_manager")
@ApiModel("ICSManager")
public class ICSManager {

	/**
	 * 
	 */

	private String membershipId;
	private Long unionCode;
	private Long userId;

	public ICSManager() {
		super();
	}

	public ICSManager(String membershipId, Long unionCode, Long userId) {
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

	@Column(name = "union_code")
	public Long getUnionCode() {
		return unionCode;
	}

	public void setUnionCode(Long unionCode) {
		this.unionCode = unionCode;
	}

	@Column(name = "user_id", nullable = false)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
