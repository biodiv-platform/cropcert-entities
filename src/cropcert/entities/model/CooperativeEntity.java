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
@Table(name = "cooperative_entities")

public class CooperativeEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3935478797789760195L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "contact")
	private String contact;

	@Column(name = "manager")
	private String manager;

	@Column(name = "union_code", nullable = false)
	private Long unionCode;

	@Column(name = "code", nullable = false)
	private Long code;

	@Column(name = "num_farmer")
	private Long numFarmer;

	@Column(name = "farmer_seq_number")
	private Long farSeqNumber;

	public CooperativeEntity(Long id, String name, String fullName, String contact, String manager, Long unionCode,
			Long numFarmer, Long farSeqNumber, Long code) {
		super();
		this.id = id;
		this.name = name;
		this.fullName = fullName;
		this.contact = contact;
		this.manager = manager;
		this.unionCode = unionCode;
		this.numFarmer = numFarmer;
		this.farSeqNumber = farSeqNumber;
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

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public CooperativeEntity() {
		super();
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String conact) {
		this.contact = conact;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public Long getUnionCode() {
		return unionCode;
	}

	public void setUnionCode(Long unionCode) {
		this.unionCode = unionCode;
	}

	public Long getNumFarmer() {
		return numFarmer;
	}

	public void setNumFarmer(Long numFarmer) {
		this.numFarmer = numFarmer;
	}

	public Long getFarSeqNumber() {
		return farSeqNumber;
	}

	public void setFarSeqNumber(Long farSeqNumber) {
		this.farSeqNumber = farSeqNumber;
	}

}
