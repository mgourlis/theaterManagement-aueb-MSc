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
import gr.aueb.mscis.theater.model.UserCategory;

import gr.aueb.mscis.theater.model.UserType;

@XmlRootElement
public class UserCategoryInfo {

	@XmlElement(name="id")
    private Integer id;
	@XmlElement(name="category")
    private UserType category;
	@XmlElement(name="gender")
    private String gender;
	@XmlElement(name="birthday")
    private Date birthday;
	@XmlElement(name="telephone")
    private String telephone;
	@XmlElement(name="users")
    private Set<User> users = new HashSet<User>();
    
	public UserCategoryInfo() {		
	}

    public UserCategoryInfo(UserCategory userCategory) {
		
		this.id = userCategory.getId();
		this.category = userCategory.getCategory();
        this.gender = userCategory.getGender();
        this.birthday = userCategory.getBirthday();
        this.telephone = userCategory.getTelephone();
    }

    public Integer getId() {
        return id;
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
	
	
	

}
