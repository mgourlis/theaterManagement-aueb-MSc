package gr.aueb.mscis.theater.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "userCatergories")
public class UserCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

	@Column(name = "category", length = 255, nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType category;
	
    @Column(name = "gender", length = 255, nullable = false)
    private String gender;

    @Column(name = "birthday", length = 255, nullable = false)
    private Date birthday;

    @Column(name = "telephone", length = 255, nullable = false)
    private String telephone;
	
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userCategory")    
    private Set<User> users = new HashSet<User>();

	/**
    * Προκαθορισμένος κατασκευαστής.
    */
    public UserCategory(){

    }

    /**
    * Βοηθητικός κατασκευαστής.
    * @param category Η κατηγορία που ανήκει ο χρήστης
    * @param gender Το φύλο του χρήστη
    * @param birthday Η ημερομηνία γέννησης του χρήστη
    * @param telephone Ο αριθμός τηλεφώνου του χρήστη
    */	 
    public UserCategory(UserType category, String gender, Date birthday, String telephone) {
		this.category = category;
        this.gender = gender;
        this.birthday = birthday;
        this.telephone = telephone;
    }

    /**
     * Επιστρέφει το id του αντικειμένου στη Βάση Δεδομένων.
     * Επιστρέφει Null αν το αντικείμενο δεν έχει ανασυρθεί από τη βάση.
     * @return Integer id
     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
    * Επιστρέφει την κατηγορία που ανήκει ο χρήστης.
    * @return Την κατηγορία που ανήκει ο χρήστης
    */
    public UserType getCategory() {
        return category;
    }

	/**
    * Θέτει την κατηγορία που ανήκει ο χρήστης.
    * @param category Την κατηγορία που ανήκει ο χρήστης
    */
    public void setCategory(UserType category) {
        this.category = category;
    }	

    /**
    * Επιστρέφει το φύλο του χρήστη.
    * @return Το φύλο του χρήστη
    */	
    public String getGender() {
        return gender;
    }

	/**
    * Θέτει το φύλο του χρήστη.
    * @param gender Το φύλο του χρήστη
    */	
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
    * Επιστρέφει την ημερομηνία γέννησης του χρήστη.
    * @return  Την ημερομηνία γέννησης του χρήστη
    */	
    public Date getBirthday() {
        return birthday;
    }

	/**
    * Θέτει την ημερομηνία γέννησης του χρήστη.
    * @param birthday Την ημερομηνία γέννησης του χρήστη
    */	
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
    * Επιστρέφει τον αριθμό τηλεφώνου του χρήστη.
    * @return  Τον αριθμό τηλεφώνου του χρήστη
    */	
    public String getTelephone() {
        return telephone;
    }

	/**
    * Θέτει τον αριθμό τηλεφώνου του χρήστη.
    * @param telephone Τον αριθμό τηλεφώνου του χρήστη
    */	
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}
