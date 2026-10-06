package cropcert.entities.model;

import java.io.Serializable;
import java.util.Objects;

public class FactoryUserCompositeKey implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long factoryCode;
	private Long userId;

	public FactoryUserCompositeKey() {
		super();
	}

	public Long getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(Long factoryCode) {
		this.factoryCode = factoryCode;
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
		if (!(o instanceof FactoryUserCompositeKey))
			return false;
		FactoryUserCompositeKey that = (FactoryUserCompositeKey) o;
		return Objects.equals(factoryCode, that.factoryCode) && Objects.equals(userId, that.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(factoryCode, userId);
	}
}
