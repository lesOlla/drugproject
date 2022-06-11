package on.druguser.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = { "id" })
@Entity
@Table(schema = "sign", name = "route")
public class Route implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 804772484530786916L;
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	Long id;
	@Column(name = "name")
	String routeName;
	/*
	 * @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy =
	 * "routes")
	 * 
	 * @JsonIgnore private Set<Role> roles = new HashSet<>();
	 */
	
	public Route(long routeId, String routeName) {
		this.routeName = routeName;
	}
	
	
}

