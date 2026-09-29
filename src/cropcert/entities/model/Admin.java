package cropcert.entities.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "admin_person")
@XmlRootElement
@PrimaryKeyJoinColumn(name = "id")
@DiscriminatorValue(value = "admin")
@Schema(description = "Admin")
public class Admin extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5072383312664930067L;
	@Column(name = "membership_id", nullable = false)
	private String membershipId;

	public String getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}
}
