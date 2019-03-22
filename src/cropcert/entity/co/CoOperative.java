package cropcert.entity.co;

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
@Table (name = "co_operative")
public class CoOperative implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1216212900838354949L;

	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "co_id_generator")
	@SequenceGenerator(name = "co_id_generator", sequenceName = "co_id_seq", allocationSize = 50)
	@Column(name = "id", updatable = false, nullable = false)
	private Long coId;
	
	@Column (name = "co_code", nullable=false)
	private int coCode;
	
	@Column (name = "co_name", nullable=false)
	private String coName;
	
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

	public Long getCoId() {
		return coId;
	}

	public void setCoId(Long coId) {
		this.coId = coId;
	}

	public int getCoCode() {
		return coCode;
	}

	public void setCoCode(int coCode) {
		this.coCode = coCode;
	}

	public String getCoName() {
		return coName;
	}

	public void setCoName(String coName) {
		this.coName = coName;
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
