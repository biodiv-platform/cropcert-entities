package cropcert.entities.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Arun
 *
 */
@Entity
@Table(name = "collection_center_entities")

public class CollectionCenterEntity extends UnionEntities {

	/**
	 * 
	 */
	private static final long serialVersionUID = 86089644487109439L;

	@Column(name = "village")
	private String village;

	@Column(name = "type")
	private String type;

	@Column(name = "sub_country")
	private String subCountry;

	@Column(name = "union_code", nullable = false)
	private Long unionCode;

	@Column(name = "cooperative_code", nullable = false)
	private Long cooperativeCode;

	@Column(name = "latitude")
	private float latitude;

	@Column(name = "longitude")
	private float longitude;

	@Column(name = "altitude")
	private float altitude;

	public CollectionCenterEntity() {
		super();
	}

	public CollectionCenterEntity(String village, String type, String subCountry, Long unionCode, Long cooperativeCode,
			float latitude, float longitude, float altitude) {
		super();
		this.village = village;
		this.type = type;
		this.subCountry = subCountry;
		this.unionCode = unionCode;
		this.cooperativeCode = cooperativeCode;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubCountry() {
		return subCountry;
	}

	public void setSubCountry(String subCountry) {
		this.subCountry = subCountry;
	}

	public Long getUnionCode() {
		return unionCode;
	}

	public void setUnionCode(Long unionId) {
		this.unionCode = unionId;
	}

	public Long getCooperativeCode() {
		return cooperativeCode;
	}

	public void setCooperativeCode(Long cooperativeCode) {
		this.cooperativeCode = cooperativeCode;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getAltitude() {
		return altitude;
	}

	public void setAltitude(float altitude) {
		this.altitude = altitude;
	}

}
