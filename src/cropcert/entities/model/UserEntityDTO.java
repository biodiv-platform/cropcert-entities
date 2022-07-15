package cropcert.entities.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.strandls.user.pojo.UserDTO;
import com.strandls.user.pojo.UserRoles;

@XmlRootElement
@JsonInclude(value = Include.NON_NULL)
public class UserEntityDTO {

	private UserDTO user;
	private CollectionCenterPerson ccPerson;
	private CooperativePerson coPerson;
	private FactoryPerson factoryPerson;
	private ICSManager iscManager;
	private Inspector inspector;
	private UnionPerson unionPerson;
	private Farmer farmer;
	private UserRoles userRole;

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public CollectionCenterPerson getCcPerson() {
		return ccPerson;
	}

	public void setCcPerson(CollectionCenterPerson ccPerson) {
		this.ccPerson = ccPerson;
	}

	public CooperativePerson getCoPerson() {
		return coPerson;
	}

	public void setCoPerson(CooperativePerson coPerson) {
		this.coPerson = coPerson;
	}

	public FactoryPerson getFactoryPerson() {
		return factoryPerson;
	}

	public void setFactoryPerson(FactoryPerson factoryPerson) {
		this.factoryPerson = factoryPerson;
	}

	public ICSManager getIscManager() {
		return iscManager;
	}

	public void setIscManager(ICSManager iscManager) {
		this.iscManager = iscManager;
	}

	public Inspector getInspector() {
		return inspector;
	}

	public void setInspector(Inspector inspector) {
		this.inspector = inspector;
	}

	public UnionPerson getUnionPerson() {
		return unionPerson;
	}

	public void setUnionPerson(UnionPerson unionPerson) {
		this.unionPerson = unionPerson;
	}

	public Farmer getFarmer() {
		return farmer;
	}

	public void setFarmer(Farmer farmer) {
		this.farmer = farmer;
	}

	public UserRoles getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRoles userRole) {
		this.userRole = userRole;
	}

}
