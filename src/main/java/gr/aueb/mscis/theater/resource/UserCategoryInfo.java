package gr.aueb.mscis.theater.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


import gr.aueb.mscis.theater.model.Play;
import gr.aueb.mscis.theater.model.Purchase;
import gr.aueb.mscis.theater.model.Sector;
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
	@XmlElementWrapper(name = "UserInfoes")
	@XmlElement(name="UserInfo")
    private List<UserInfo> users;
    
	public UserCategoryInfo() {		
	}

    public UserCategoryInfo(UserCategory userCategory) {		
		this.id = userCategory.getId();
		this.category = userCategory.getCategory();
        this.gender = userCategory.getGender();
        this.birthday = userCategory.getBirthday();
        this.telephone = userCategory.getTelephone();
        this.users = UserInfo.wrap((List<User>) userCategory.getUsers());
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

	public List<UserInfo> getUsers() {
		return users;
	}

	public void setUsers(List<UserInfo> users) {
		this.users = users;
    }
	
    public static List<UserCategoryInfo> wrap(List<UserCategory> UserCategories) {

		List<UserCategoryInfo> UserCategoryInfoList = new ArrayList<UserCategoryInfo>();

		for (UserCategory u : UserCategories) {
			UserCategoryInfoList.add(new UserCategoryInfo(u));
		}

		return UserCategoryInfoList;
    }	
	
	public UserCategory getUserCategory(EntityManager em){

		UserCategory userCategory = null;

		if (id != null) {
			userCategory = em.find(UserCategory.class, id);
		} else {
			userCategory = new UserCategory();
		}
		
		userCategory.setCategory(category);
        userCategory.setGender(gender);
        userCategory.setBirthday(birthday);
        userCategory.setTelephone(telephone);
		List<User> newUsers = new ArrayList<User>();
		for (UserInfo u : users){
			User user = u.getUser(em);
			user.setUserCategory(userCategory);
			newUsers.add(user);
		}
		userCategory.getUsers().clear();
		userCategory.getUsers().addAll(newUsers);

		return userCategory;
    }
	
}
