package cropcert.entities.model.response;

import cropcert.entities.model.CollectionCenterEntity;

public class CollectionCenterShow extends CollectionCenterEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2394474635739038584L;
	private String type;
	private Long coCode;
	private String coName;
	private String unionName;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getCoCode() {
		return coCode;
	}

	public void setCoCode(Long coCode) {
		this.coCode = coCode;
	}

	public String getCoName() {
		return coName;
	}

	public void setCoName(String coName) {
		this.coName = coName;
	}

	public String getUnionName() {
		return unionName;
	}

	public void setUnionName(String unionName) {
		this.unionName = unionName;
	}

}
