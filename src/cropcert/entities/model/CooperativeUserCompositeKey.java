package cropcert.entities.model;

import java.io.Serializable;
import java.util.Objects;

public class CooperativeUserCompositeKey implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long coCode;
	private Long userId;

	public CooperativeUserCompositeKey() {
		super();
	}

	public Long getCoCode() {
		return coCode;
	}

	public void setCoCode(Long coCode) {
		this.coCode = coCode;
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
		if (!(o instanceof CooperativeUserCompositeKey))
			return false;
		CooperativeUserCompositeKey that = (CooperativeUserCompositeKey) o;
		return Objects.equals(coCode, that.coCode) && Objects.equals(userId, that.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(coCode, userId);
	}
}
