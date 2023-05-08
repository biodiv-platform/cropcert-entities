package cropcert.entities.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Table;

/**
 * @author Arun
 *
 */
@Entity
@Table(name = "cooperative_entities")

public class CooperativeEntity extends UnionEntities {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3935478797789760195L;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "contact")
	private String contact;

	@Column(name = "manager")
	private String manager;

	@Column(name = "union_code", nullable = false)
	private Long unionCode;

	@Column(name = "num_farmer")
	private Long numFarmer;

	@Column(name = "farmer_seq_number")
	private Long farSeqNumber;

	public CooperativeEntity(String fullName, String contact, String manager, Long unionCode, Long numFarmer,
			Long farSeqNumber) {
		super();

		this.fullName = fullName;
		this.contact = contact;
		this.manager = manager;
		this.unionCode = unionCode;
		this.numFarmer = numFarmer;
		this.farSeqNumber = farSeqNumber;

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
