package gr.aueb.mscis.theater.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import gr.aueb.mscis.theater.model.Play;
import gr.aueb.mscis.theater.model.Purchase;
import gr.aueb.mscis.theater.model.Ticket;
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
    @XmlElement(name="purchases")
    private Set<Purchase> purchases = new HashSet<Purchase>();    
//  @XmlElement(name="userCategory")
//  private UserCategoryInfo userCategory;
    
	public UserInfo() {		
	}

    public UserInfo(User user) {

        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.token = user.getToken();
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

    public void setPurchase(Purchase purchase) {
    	purchases.add(purchase);
    }

    public Set<Purchase> getPurchases() {
        return purchases;
    }
    
	/*
	public User getUserCategory() {
		return userCategory;
	}

	public void setUserCategory(UserCategory userCategory) {
		this.userCategory = userCategory;
	}
    */
}