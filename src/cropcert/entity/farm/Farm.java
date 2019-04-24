package cropcert.entity.farm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table (name = "farm")
public class Farm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 66407839811131038L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "farm_id_generator")
	@SequenceGenerator(name = "farm_id_generator", sequenceName = "farm_id_seq", allocationSize = 50)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	@Column( name = "farm_code", nullable = false)
	private String farmCode;
	
	@Column( name = "farm_number", nullable = false)
	private int farmNumber;
		
	@Column (name = "num_of_coffee_plots", nullable = false)
	private int numOfPlots;	

	@Column (name = "num_of_coffee_trees", nullable = false)
	private int numOfCoffeeTrees;
	
	@Column (name = "area_under_coffee", nullable = false)
	private double areaUnderCoffee;

	@Column (name = "total_area", nullable = false)
	private double totalArea;
	
	@Column (name = "latitude")
	private double latitude;
	
	@Column (name = "langitude")
	private double langitude;
	
	@Column (name = "membership_id")
	private String membershipId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFarmCode() {
		return farmCode;
	}

	public void setFarmCode(String farmCode) {
		this.farmCode = farmCode;
	}

	public int getFarmNumber() {
		return farmNumber;
	}

	public void setFarmNumber(int farmNumber) {
		this.farmNumber = farmNumber;
	}

	public int getNumOfPlots() {
		return numOfPlots;
	}

	public void setNumOfPlots(int numOfPlots) {
		this.numOfPlots = numOfPlots;
	}

	public int getNumOfCoffeeTrees() {
		return numOfCoffeeTrees;
	}

	public void setNumOfCoffeeTrees(int numOfCoffeeTrees) {
		this.numOfCoffeeTrees = numOfCoffeeTrees;
	}

	public double getAreaUnderCoffee() {
		return areaUnderCoffee;
	}

	public void setAreaUnderCoffee(double areaUnderCoffee) {
		this.areaUnderCoffee = areaUnderCoffee;
	}

	public double getTotalArea() {
		return totalArea;
	}

	public void setTotalArea(double totalArea) {
		this.totalArea = totalArea;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLangitude() {
		return langitude;
	}

	public void setLangitude(double langitude) {
		this.langitude = langitude;
	}

	public String getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}
}
