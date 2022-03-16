package cropcert.entities.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;

@Entity
@Table (name = "union_table")
@XmlRootElement
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue(value= "union")
@ApiModel("Union")
public class Union extends CropcertEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 109808509127731770L;

}
