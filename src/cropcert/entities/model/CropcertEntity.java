package cropcert.entities.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;

@Entity
@Table(name="cropcert_entity")
@XmlRootElement
@JsonIgnoreProperties
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "entityType", discriminatorType = DiscriminatorType.STRING)
@ApiModel( value = "CropcertEntity", subTypes = {CollectionCenter.class, Cooperative.class,
		Factory.class, Farm.class, Union.class})
public class CropcertEntity implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4840834129839951969L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entity_id_generator")
	@SequenceGenerator(name = "entity_id_generator", sequenceName = "entity_id_seq", allocationSize = 50)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Column (name = "code", nullable=false)
	private Long code;
	@Column (name = "name", nullable=false)
	private String name;
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
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
