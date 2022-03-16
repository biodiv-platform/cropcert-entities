package cropcert.entities.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;

@Entity
@Table (name = "factory")
@XmlRootElement
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue(value= "factory")
@ApiModel("Factory")
public class Factory extends CropcertEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2129075276095668097L;

	@Column (name = "union_code")
	private Long unionCode;
	
	public Long getUnionCode() {
		return unionCode;
	}
	public void setUnionCode(Long unionCode) {
		this.unionCode = unionCode;
	}
}
