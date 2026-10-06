package cropcert.entities.model;

import java.io.Serializable;
import java.util.Objects;

public class CollectionCenterUserCompositeKey implements Serializable {

	private static final long serialVersionUID = 1L;

	private String membershipId;
	private Long ccCode;
	private Long userId;

	public CollectionCenterUserCompositeKey() {
		super();
	}

	public String getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}

	public Long getCcCode() {
		return ccCode;
	}

	public void setCcCode(Long ccCode) {
		this.ccCode = ccCode;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof CollectionCenterUserCompositeKey))
			return false;
		CollectionCenterUserCompositeKey that = (CollectionCenterUserCompositeKey) o;
		return Objects.equals(membershipId, that.membershipId) && Objects.equals(ccCode, that.ccCode) && Objects.equals(userId, that.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(membershipId, ccCode, userId);
	}
}
