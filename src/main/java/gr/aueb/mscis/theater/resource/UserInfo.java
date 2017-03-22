
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


import gr.aueb.mscis.theater.model.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
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
	@XmlElement(name="category")
	private UserType category;
	@XmlElement(name="gender")
	private String gender;
	@XmlElement(name="birthday")
	private Date birthday;
	@XmlElement(name="telephone")
	private String telephone;
	
	public UserInfo() {		
	}

    public UserInfo(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
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

	public UserType getCategory() {
		return category;
	}

	public void setCategory(UserType category) {
		this.category = category;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public static List<UserInfo> wrap(List<User> Users) {

		List<UserInfo> UserInfoList = new ArrayList<UserInfo>();

		for (User u : Users) {
			UserInfoList.add(new UserInfo(u));
		}

		return UserInfoList;
    }

	public User getNewUser(EntityManager em, UserType type){

		User user = new User();

		user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
		user.setUserCategory(new UserCategory(type));

        if(type == UserType.Customer){
			user.getUserCategory().setBirthday(birthday);
			user.getUserCategory().setGender(gender);
			user.getUserCategory().setTelephone(telephone);
		}

		return user;
    }

	public User getExistingUser(EntityManager em, UserType type){

		User user = null;

		if (id != null) {
			user = em.find(User.class, id);
		}

		if(user != null) {
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setPassword(password);
			user.setUserCategory(new UserCategory(type));

			if (type == UserType.Customer) {
				user.getUserCategory().setBirthday(birthday);
				user.getUserCategory().setGender(gender);
				user.getUserCategory().setTelephone(telephone);
			}
		}

		return user;
	}
}