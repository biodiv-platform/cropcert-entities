package cropcert.entities.model.response;

import cropcert.entities.model.CollectionCenter;

public class CollectionCenterShow {

	private Long id;
	private String type;
	private Long coCode;
	private Long code;
	private String name;
	private String coName;
	private String unionName;
	private String village;
	private String subCountry;
	private float latitude;
	private float longitude;
	private float altitude;

	public CollectionCenterShow() {
		super();
	}
	
	public CollectionCenterShow(CollectionCenter collectionCenter, String coName, String unionName) {
		this.id = collectionCenter.getId();
		this.type = collectionCenter.getType();
		this.coCode = collectionCenter.getCoCode();
		this.code = collectionCenter.getCode();
		this.name = collectionCenter.getName();
		this.coName = coName;
		this.unionName = unionName;
		this.village = collectionCenter.getVillage();
		this.subCountry = collectionCenter.getSubCountry();
		this.latitude = collectionCenter.getLatitude();
		this.longitude = collectionCenter.getLongitude();
		this.altitude = collectionCenter.getAltitude();
	}

	public CollectionCenterShow(Long id, String type, Long coCode, Long code, String name, String coName,
			String unionName, String village, String subCountry, float latitude, float longitude, float altitude) {
		super();
		this.id = id;
		this.type = type;
		this.coCode = coCode;
		this.code = code;
		this.name = name;
		this.coName = coName;
		this.unionName = unionName;
		this.village = village;
		this.subCountry = subCountry;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getSubCountry() {
		return subCountry;
	}

	public void setSubCountry(String subCountry) {
		this.subCountry = subCountry;
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
