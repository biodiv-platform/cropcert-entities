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
	private String conact;

	@Column(name = "manager")
	private String manager;

	@Column(name = "union_id", nullable = false)
	private Long unionId;

	@Column(name = "code", nullable = false)
	private Long code;

	public CooperativeEntity(Long id, String name, String fullName, String conact, String manager, Long code,
			Long unionId) {
		super();
		this.id = id;
		this.name = name;
		this.fullName = fullName;
		this.conact = conact;
		this.manager = manager;
		this.code = code;
		this.unionId = unionId;
	}
	
    public CooperativeEntity() {
        super();
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getConact() {
		return conact;
	}

	public void setConact(String conact) {
		this.conact = conact;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public Long getUnionId() {
		return unionId;
	}

	public void setUnionId(Long unionId) {
		this.unionId = unionId;
	}

}
