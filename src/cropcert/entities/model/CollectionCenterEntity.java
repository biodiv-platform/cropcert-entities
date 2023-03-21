package cropcert.entities.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @author Arun
 *
 */
@Entity
@Table(name = "collection_center_entities")

public class CollectionCenterEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 86089644487109439L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "village")
	private String village;

	@Column(name = "type")
	private String type;

	@Column(name = "sub_country")
	private String subCountry;

	@Column(name = "union_id", nullable = false)
	private Long unionId;

	@Column(name = "cooperative_id", nullable = false)
	private Long CooperativeId;

	@Column(name = "latitude")
	private float latitude;

	@Column(name = "longitude")
	private float longitude;

	@Column(name = "altitude")
	private float altitude;

	@Column(name = "code", nullable = false)
	private Long code;

	public CollectionCenterEntity() {
		super();
	}

	public CollectionCenterEntity(Long id, String name, String village, String type, String subCountry, Long unionId,
			Long CooperativeId, float latitude, float longitude, float altitude, Long code) {
		super();
		this.id = id;
		this.name = name;
		this.village = village;
		this.type = type;
		this.subCountry = subCountry;
		this.unionId = unionId;
		this.CooperativeId = CooperativeId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.code = code;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Long getUnionId() {
		return unionId;
	}

	public void setUnionId(Long unionId) {
		this.unionId = unionId;
	}

	public Long getCooperativeId() {
		return CooperativeId;
	}

	public void setCooperativeId(Long CooperativeId) {
		this.CooperativeId = CooperativeId;
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

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

}
