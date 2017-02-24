package gr.aueb.mscis.theater.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "plays")
public class Play {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "title", length = 255, nullable = false)
	private String title;

	@Column(name = "description", length = 5000, nullable = true)
	private String description;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "play")
	private Set<Role> roles = new HashSet<Role>();

	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "play")
	private Set<Show> shows = new HashSet<Show>();

	/**
	 *Προκαθορισμένος κατασκευαστής.
	 */
	public Play() {

	}

	/**
	 *Κατασκευαστής της κλάσσης Play, δημιουργεί αντικείμενο τύπου Play
	 * @param title ο τίτλος του θεατρικού έργου
	 * @param description η περιγραφή του θεατρικού έργου
	 */
	public Play(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	/**
	 *Επιστρέφει τον τίτλο του θεατρικού έργου
	 * @return ο τίτλος
	 */
	public String getTitle() {
		return title;
	}

	/**
	 *Θέτει τον τίτλο του θεατρικού έργου
	 * @param title ο τίτλος
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Επιστρέφει την περιγραφή του θεατρικού έργου
	 * @return η περιγραφή
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Θέτει την περιγραφή του θεατρικού έργου
	 * @param description η περιγραφή
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Επιστρέφει το σύνολο των ρόλων του θεατρικού έργου
	 * @return σύνολο ρόλων
	 */
	public Set<Role> getRoles() {
		return new HashSet<Role>(roles);
	}

	/**
	 * Επιστρέφει έναν ρόλο του θεατρικού έργου που έχει ως παραμέτρους το όνομά του
	 * και τον τύπο του
	 * @param roleName το όνομα του ρόλου
	 * @param type ο τύπος του ρόλου
	 * @return ο ρόλος
	 * @throws IllegalArgumentException αν δεν βρεθεί ο ρόλος
	 */
	public Role getRole(String roleName, RoleType type) throws IllegalArgumentException {
		for(Role role :roles){
			if(role.getName().equals(roleName) && role.getRoleType().equals(type))
				return role;
		}
		throw new IllegalArgumentException("role not found");
	}

	/**
	 * Εισάγει ρόλο στο θεατρικό έργο με την μέθοδο add του HashSet
	 * @param role ο ρόλος
	 */
	public void addRole(Role role){
		role.setPlay(this);
		this.roles.add(role);
	}

	/**
	 * Αφαιρεί ρόλο από το θεατρικό έργο με την μέθοδο remove του HashSet
	 * @param role ο ρόλος
	 * @return true/false αν έγινε η διαγραφεί ή όχι.
	 */
	public boolean removeRole(Role role){
		Boolean delete = this.roles.remove(role);
		if(delete) {
			if(role.getAgent() != null){
				role.getAgent().removeRole(role);
			}
			role.setPlay(null);
			return true;
		}
		return delete;
	}

	/**
	 * Επιστρέφει το σύνολο των παραστέσεων του θεατρικού έργου.
	 * @return το σύνολο των παραστάσεων
	 */
	public Set<Show> getShows() {
		return shows;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Play play = (Play) o;

		return title.equals(play.title);
	}

	@Override
	public int hashCode() {
		return title.hashCode();
	}
}
