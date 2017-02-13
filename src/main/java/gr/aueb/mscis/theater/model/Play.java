package gr.aueb.mscis.theater.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "plays")
public class Play {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "title", length = 255, nullable = false)
	private String title;

	@Column(name = "description", length = 5000, nullable = true)
	private String description;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "play")
	private Set<Role> roles = new HashSet<Role>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "play")
	private Set<Show> shows = new HashSet<Show>();

	/**
	 *
	 */
	public Play() {
	}

	/**
	 *
	 * @param title
	 */
	public Play(String title) {
		this.title = title;
	}

	/**
	 *
	 * @param title
	 * @param description
	 */
	public Play(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 *
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 *
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Role> getRoles() {
		return new HashSet<Role>(roles);
	}

	public void setRoles(Set<Role> roles) {
		this.roles = new HashSet<Role>(roles);
	}
}
