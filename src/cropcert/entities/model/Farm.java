package cropcert.entities.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;

@Entity
@Table (name = "farm")
@XmlRootElement
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue(value= "farm")
@ApiModel("Farm")
public class Farm extends CropcertEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3329981401545405313L;
	
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
	
}
