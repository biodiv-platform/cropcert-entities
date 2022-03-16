package cropcert.entities.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;

@Entity
@Table(name="factory_person")
@XmlRootElement
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue(value= "factory")
@ApiModel("FactoryPerson")
public class FactoryPerson extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5072383312664930067L;
	
	private String membershipId;
	private int factoryCode;
	

	@Column(name = "membership_id", nullable=false)
	public String getMembershipId() {
		return membershipId;
	}
	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}
	
	@Column (name = "factory_code")
	public int getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(int factoryCode) {
		this.factoryCode = factoryCode;
	}

}
