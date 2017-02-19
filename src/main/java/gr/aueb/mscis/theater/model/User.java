package gr.aueb.mscis.theater.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Purchase> purchases = new HashSet<Purchase>();

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="userCategory_id", nullable = true)
    private UserCategory userCategory;
    
    /**
     * Προκαθορισμένος κατασκευαστής.
     */
    public User() { }

    /**
     * Βοηθητικός κατασκευαστής.
     * @param firstname Το όνομα χρήστη
     * @param lastName Το επώνυμο του χρήστη
     * @param email Το e-mail του χρήστη
     * @param password Ο κωδικός πρόσβασης του χρήστη
     */
    public User(String firstname,
    		    String lastName,
    		    String email,
    		    String password) {

    	this.firstName = firstname;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
    
    public Integer getId() {
        return id;
    }

    /**
     * Επιστρέφει το id του αντικειμένου στη Βάση Δεδομένων.
     * Επιστρέφει Null αν το αντικείμενο δεν έχει ανασυρθεί από τη βάση.
     * @return Integer id
     */
    public Integer getId() {
        return id;
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
     * @param token Το token.
     */
    protected void setToken(String token) {
        this.token = token;
    }

    public boolean isPasswordValid() {
    	
    	if (email.length() < 8) return false;

    	Pattern p = Pattern.compile("([0-9])");
    	Matcher m = p.matcher(email);
    	boolean b = m.find();
    	if (b == false) return false;
    	
    	return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return email.equals(user.email);
    }
}