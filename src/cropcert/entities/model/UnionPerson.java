package cropcert.entities.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;

@Entity
@Table(name="union_person")
@XmlRootElement
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue(value= "union")
@ApiModel("UnionPerson")
public class UnionPerson extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5072383312664930067L;
	
	@Column(name = "membership_id", nullable=false)
	private String membershipId;
	@Column (name = "union_code")
	private Long unionCode;
	@Column (name = "union_name")
	private String unionName;

	public String getMembershipId() {
		return membershipId;
	}
	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}

	public Long getUnionCode() {
		return unionCode;
	}
	public void setUnionCode(Long unionCode) {
		this.unionCode = unionCode;
	}
	
	public String getUnionName() {
		return unionName;
	}
	public void setUnionName(String unionName) {
		this.unionName = unionName;
	}
	
}
