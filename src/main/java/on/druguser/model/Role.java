package on.druguser.model;

import java.io.Serializable;
import java.util.Set;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(schema = "sign", name = "role")
public class Role  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2649307916159910049L;
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	Long id;
	@Column(name = "name")
	String name;
	
	@ManyToMany/*(fetch = FetchType.LAZY,
		      cascade = {
		              CascadeType.PERSIST,
		              CascadeType.MERGE
		          })*/
	@JoinTable(schema = "sign", name = "role_route",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "route_id")
			) 
	Set<Route> routes;
	
	public Role(String roleName) {
		this.name = roleName;
	}	
}
