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

	public Integer getId() {
		return id;
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

	public Role getRole(String roleName, RoleType type) throws IllegalArgumentException {
		for(Role role :roles){
			if(role.getName().equals(roleName) && role.getRoleType().equals(type))
				return role;
		}
		throw new IllegalArgumentException("role not found");
	}

	public void addRole(Role role){
		role.setPlay(this);
		this.roles.add(role);
	}

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
