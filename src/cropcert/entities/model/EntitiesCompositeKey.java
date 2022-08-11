package cropcert.entities.model;

import java.io.Serializable;

public class EntitiesCompositeKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -676457363837201905L;
	private String membershipId;
	private Long userId;

	public String getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
