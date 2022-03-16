package cropcert.entities.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;

@Entity
@Table(name="inspector")
@XmlRootElement
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue(value= "inspector")
@ApiModel("Inspector")
public class Inspector extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8991931638221138539L;

	@Column(name = "membership_id", nullable=false)
	private String membershipId;
	@Column (name = "union_code")
	private int unionCode;
	
	public String getMembershipId() {
		return membershipId;
	}
	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}
	public int getUnionCode() {
		return unionCode;
	}
	public void setUnionCode(int unionCode) {
		this.unionCode = unionCode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
