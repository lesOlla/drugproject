package on.druguser.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
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
@Table(schema = "sign", name = "user")
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6802140481669116233L;
	@Id
	@Column(name = "id")
	@SequenceGenerator(name = "personsIdSeq", sequenceName = "persons_id_seq", allocationSize = 12)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientsIdSeq")
	Long id;

	@Column(name = "username")
	String username;

	@Column(name = "email")
	String email;

	@Column(name = "password")
	String password;

	@Column(name = "active")
	boolean active = true;

	public User(String username, String email, String password) {
		this.username = username.toLowerCase();
		this.email = email.toLowerCase();
		this.password = password;
	}
}
