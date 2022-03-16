package cropcert.entities.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;

@Entity
@Table(name="admin_person")
@XmlRootElement
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue(value= "admin")
@ApiModel("Admin")
public class Admin extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5072383312664930067L;
	@Column(name = "membership_id", nullable=false)
	private String membershipId;

	public String getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}
}
