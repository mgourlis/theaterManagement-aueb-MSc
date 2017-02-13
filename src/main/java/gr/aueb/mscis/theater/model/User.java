package gr.aueb.mscis.theater.model;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User {
	
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="firstName", length=50, nullable = false)
    private String firstName;
 
    @Column(name="lastName", length=50, nullable = false)
    private String lastName;

    @Column(name="email", length=50, nullable = false)
    private String email;

    @Column(name="password", length=50, nullable = false)
    private String password;

    @Column(name="token", length=50, nullable = true)
    private String token;

//ayto telika tha to xrisimopoiisoume?
//    @Enumerated(EnumType.STRING)
//    @Column(name="userType")
//    private UserType userType;

    
    /**
     * Προκαθορισμένος κατασκευαστής.
     */
    public User() { }

    /**
     * Βοηθητικός κατασκευαστής.
     * ....
     * @param
     * @param
     * @param
     * @param
     * @param
     */
    public User(String firstname,
    		    String lastName,
    		    String email,
    		    String password) {

    	this.firstName = firstname;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
//        this.userType = userType;
    }
    
    /**
     * Θέτει το όνομα του χρήστη.
     * @param firstName Το όνομα του χρήστη
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Επιστρέφει το όνομα του χρήστη.
     * @return Το όνομα του χρήστη
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Θέτει το επώνυμο του χρήστη.
     * @param lastName Το επώνυμο του χρήστη
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Επιστρέφει το επώνυμο του χρήστη.
     * @return Το επώνυμο του χρήστη
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Θέτει τον κωδικό σύνδεσης.
     * @param password Ο κωδικός σύνδεσης
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Επιστρέφει τον κωδικό σύνδεσης.
     * @return Ο κωδικός σύνδεσης
     */
    public String getPassword() {
        return password;
    }

    /**
     * Θέτει το e-mail του χρήστη.
     * @param eMail Το e-mail του χρήστη
     */
    public void setEmail(String eMail) {
        this.email = eMail;
    }

    /**
     * Επιστρέφει το e-mail του δανειζομένου.
     * @return Το e-mail του δανειζομένου
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Επιστρέφει το token.
     * @return Το token
     */
    public String getToken() {
        return token;
    }

    /**
     * θέτει το token.
     * @param type Το token.
     */
    protected void setToken(String token) {
        this.token = token;
    }
    
//    /**
//     * Επιστρέφει τον τύπο χρήστη.
//     * @return Ο τύπος του χρήστη
//     */
//    public UserType getUserType() {
//        return userType;
//    }
//
//    /**
//     * θέτει τον τύπο του χρήστη.
//     * @param type Ο τύπος του χρήστη.
//     */
//    private void setUserType(UserType type) {
//        this.userType = type;
//    }

}