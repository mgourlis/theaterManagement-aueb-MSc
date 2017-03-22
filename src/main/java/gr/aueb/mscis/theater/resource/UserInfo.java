
package gr.aueb.mscis.theater.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.xml.bind.annotation.*;
import javax.persistence.EntityManager;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


import gr.aueb.mscis.theater.model.Purchase;
import gr.aueb.mscis.theater.model.Sector;
import gr.aueb.mscis.theater.model.User;

@XmlRootElement
public class UserInfo {

    @XmlElement(name="id")
    private Integer id;
    @XmlElement(name="firstName")
    private String firstName;
    @XmlElement(name="lastName")
    private String lastName;
    @XmlElement(name="email")
    private String email;
    @XmlElement(name="password")
    private String password;
    @XmlElement(name="token")
    private String token;
	@XmlElementWrapper(name = "PurchaseInfoes")
	@XmlElement(name = "PurchaseInfo")
	private List<PurchaseInfo> purchases;
	
	public UserInfo() {		
	}

    public UserInfo(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.token = user.getToken();
		//this.purchases = PurchaseInfo.wrap(user.getPurchases());      ==>  Perimenw to "PurchaseInfo wrap(Purchase purchase)" sto PurchaseInfo 
    }
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}	

	public List<PurchaseInfo> getPurchases() {
		return purchases;
	}

	public void setPurchases(List<PurchaseInfo> purchases) {
		this.purchases = purchases;
    }
	
    public static List<UserInfo> wrap(List<User> Users) {

		List<UserInfo> UserInfoList = new ArrayList<UserInfo>();

		for (User u : Users) {
			UserInfoList.add(new UserInfo(u));
		}

		return UserInfoList;
    }

	public User getUser(EntityManager em){

		User user = null;

		if (id != null) {
			user = em.find(User.class, id);
		} else {
			user = new User();
		}

		user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setToken(token);
		List<Purchase> newPurchases = new ArrayList<Purchase>();
		for (PurchaseInfo p : purchases){
			//Purchase purchase = p.getPurchase(em);     ==>  Perimenw to "Purchase getPurchase(EntityManager em)" sto PurchaseInfo
			//purchase.setUser(user);
			//newPurchases.add(purchase);
		}
		user.getPurchases().clear();
		user.getPurchases().addAll(newPurchases);

		return user;
    }
}