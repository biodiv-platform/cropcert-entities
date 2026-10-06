package cropcert.entities.model;

import java.io.Serializable;
import java.util.Objects;

public class UnionUserCompositeKey implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long unionCode;
	private Long userId;

	public UnionUserCompositeKey() {
		super();
	}

	public Long getUnionCode() {
		return unionCode;
	}

	public void setUnionCode(Long unionCode) {
		this.unionCode = unionCode;
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
		if (!(o instanceof UnionUserCompositeKey))
			return false;
		UnionUserCompositeKey that = (UnionUserCompositeKey) o;
		return Objects.equals(unionCode, that.unionCode) && Objects.equals(userId, that.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(unionCode, userId);
	}
}
