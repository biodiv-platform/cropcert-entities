package cropcert.entity.cc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table (name = "collection_center")
public class CollectionCenter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 109808509127731770L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cc_id_generator")
	@SequenceGenerator(name = "cc_id_generator", sequenceName = "cc_id_seq", allocationSize = 50)
	@Column(name = "id", updatable = false, nullable = false)
	private Long ccId;
	
	@Column (name = "cc_code", nullable=false)
	private int ccCode;
	
	@Column (name = "cc_name", nullable=false)
	private String ccName;
	
	@Column (name = "type")
	private String type;
	
	@Column (name = "village")
	private String village;
	
	@Column (name = "sub_country")
	private String subCountry;
	
	@Column (name = "latitude")
	private float latitude;
	
	@Column( name = "longitude")
	private float longitude;
	
	@Column( name = "altitude")
	private float altitude;
	
	@Column ( name = "co_operative_id")
	private Long coOperativeId;

	public Long getCcId() {
		return ccId;
	}

	public void setCcId(Long ccId) {
		this.ccId = ccId;
	}

	public int getCcCode() {
		return ccCode;
	}

	public void setCcCode(int ccCode) {
		this.ccCode = ccCode;
	}

	public String getCcName() {
		return ccName;
	}

	public void setCcName(String ccName) {
		this.ccName = ccName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Long getCoOperativeId() {
		return coOperativeId;
	}

	public void setCoOperativeId(Long coOperativeId) {
		this.coOperativeId = coOperativeId;
	}
	
}
