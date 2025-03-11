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
@Table(name = "union_entities")

public class UnionEntities implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5265495045053410905L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "code", nullable = false)
	private Long code;

	@Column(name = "has_batch", nullable = false, columnDefinition = "boolean default true")
	private Boolean hasBatch;

	@Column(name = "has_lot", nullable = false, columnDefinition = "boolean default true")
	private Boolean hasLot;

	@Column(name = "has_container", nullable = false, columnDefinition = "boolean default false")
	private Boolean hasContainer;

	public UnionEntities(Long id, String name, Long code, Boolean hasBatch, Boolean hasLot, Boolean hasContainer) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.hasBatch = hasBatch;
		this.hasLot = hasLot;
		this.hasContainer = hasContainer;
	}

	public UnionEntities() {
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

	public Boolean getHasBatch() {
		return hasBatch;
	}

	public void setHasBatch(Boolean hasBatch) {
		this.hasBatch = hasBatch;
	}

	public Boolean getHasLot() {
		return hasLot;
	}

	public void setHasLot(Boolean hasLot) {
		this.hasLot = hasLot;
	}

	public Boolean getHasContainer() {
		return hasContainer;
	}

	public void setHasContainer(Boolean hasContainer) {
		this.hasContainer = hasContainer;
	}

}
